package aleksey.projects.github_users.screens.sign_in

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BaseView
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.ext.bindView
import aleksey.projects.github_users.screens.github_users_list.startGithubUsersListActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivityView : AppCompatActivity(), BaseView {

    private val viewModel: SignInActivityViewModel by viewModel()

    private val rootView: CoordinatorLayout by bindView(R.id.root)
    private val toolbar: Toolbar by bindView(R.id.toolbar)

    private val signInWithVk: ImageView by bindView(R.id.sign_in_vk)
    private val signInWithGoogle: ImageView by bindView(R.id.sign_in_google)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        lifecycle.addObserver(viewModel)

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
                }
                is ProgressState.Done -> {
                }
                is ProgressState.Error -> {
                }
                is ProgressState.Finish -> {
                }
            }
        })
    }

}
