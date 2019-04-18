package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BaseView
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.ext.bindView
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel

fun startGithubUsersListActivity(context: Context) {
    context.startActivity(getGithubUsersListActivityIntent(context))
}

fun getGithubUsersListActivityIntent(context: Context): Intent {
    val intent = Intent(context, GithubUsersListActivityView::class.java)
    return intent
}

class GithubUsersListActivityView : AppCompatActivity(), BaseView {

    private val viewModel: GithubUsersListActivityViewModel by viewModel()

    private val rootView: CoordinatorLayout by bindView(R.id.root)
    private val toolbar: Toolbar by bindView(R.id.toolbar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_users_list)
        lifecycle.addObserver(viewModel)

        initToolbar()
        initViews()
        initListeners()
        initViewModelObserving()
    }

    override fun initToolbar() {
        val spannableTitle = SpannableString(getString(R.string.github_users_list_toolbar_title))
        val dotPosition = spannableTitle.indexOf(".")
        spannableTitle.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), dotPosition + 1, spannableTitle.length, Spannable.SPAN_COMPOSING)
        toolbar.title = spannableTitle
    }

    override fun initViews() {

    }

    override fun initListeners() {

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