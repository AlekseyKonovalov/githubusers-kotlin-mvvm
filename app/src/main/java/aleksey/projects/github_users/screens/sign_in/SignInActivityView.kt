package aleksey.projects.github_users.screens.sign_in

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BaseView
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.ext.bindView
import aleksey.projects.github_users.screens.github_users_list.startGithubUsersListActivity
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.util.VKUtil
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class SignInActivityView : AppCompatActivity(), BaseView {

    private val viewModel: SignInActivityViewModel by viewModel()

    private val rootView: CoordinatorLayout by bindView(R.id.root)
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val progressOverlay: RelativeLayout by bindView(R.id.progressOverlay)
    private val signInWithVk: ImageView by bindView(R.id.sign_in_vk)
    private val signInWithGoogle: ImageView by bindView(R.id.sign_in_google)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        lifecycle.addObserver(viewModel)

        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)



        initToolbar()
        initViews()
        initListeners()
        initViewModelObserving()
    }

    override fun initToolbar() {
        val spannableTitle = SpannableString(getString(R.string.sighin_toolbar_title))
        val dotPosition = spannableTitle.indexOf(".")
        spannableTitle.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)),
            dotPosition + 1,
            spannableTitle.length,
            Spannable.SPAN_COMPOSING
        )
        toolbar.title = spannableTitle
    }

    override fun initViews() {

    }

    override fun initListeners() {
        signInWithVk.setOnClickListener {
            viewModel.signInWithVk()
            VKSdk.login(this@SignInActivityView)
        }
        signInWithGoogle.setOnClickListener {
            //viewModel.signInWithGoogle()
            startGithubUsersListActivity(this@SignInActivityView)
            finish()
        }
    }

    override fun initViewModelObserving() {
        viewModel.getProgressState().observe(this, Observer { state ->
            when (state) {
                is ProgressState.Initial -> {
                }
                is ProgressState.Loading -> {
                    showProgressBar()
                }
                is ProgressState.Done -> {
                    hideProgressBar()
                }
                is ProgressState.Error -> {
                    showSnackbar(getString(R.string.error_try_later))
                    hideProgressBar()
                }
                is ProgressState.Finish -> {
                    hideProgressBar()
                    startGithubUsersListActivity(this@SignInActivityView)
                    finish()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!// Пользователь успешно авторизовался
            // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken) {
                    Timber.d(res.toString())
                    viewModel.setVkData(res.accessToken, res.userId)
                    startGithubUsersListActivity(this@SignInActivityView)
                    finish()
                }

                override fun onError(error: VKError) {
                    Timber.e(error.toString())
                    showSnackbar(getString(R.string.error_try_later))
                }
            })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun showSnackbar(msg: String) {
        val snack = Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun showProgressBar() {
        TransitionManager.beginDelayedTransition(rootView)
        progressOverlay.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        TransitionManager.beginDelayedTransition(rootView)
        progressOverlay.visibility = View.GONE
    }

}
