package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BaseView
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.ext.bindView
import android.animation.LayoutTransition
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.TransitionManager
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
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
    private val progressOverlay: RelativeLayout by bindView(R.id.progressOverlay)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        val searchBar = searchView.findViewById(R.id.search_bar) as LinearLayout

        searchBar.layoutTransition = LayoutTransition()
        searchView.maxWidth = Integer.MAX_VALUE
        val searchPlate = searchView.findViewById(R.id.search_plate) as View
        searchPlate.setBackgroundColor(ContextCompat.getColor(this@GithubUsersListActivityView, android.R.color.transparent))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //viewModel.findUsers(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //viewModel.findUsers(newText)
                return true
            }
        })

        searchView.setOnCloseListener {
            false
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun initToolbar() {
        val spannableTitle = SpannableString(getString(R.string.github_users_list_toolbar_title))
        val dotPosition = spannableTitle.indexOf(".")
        spannableTitle.setSpan(ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)), dotPosition + 1, spannableTitle.length, Spannable.SPAN_COMPOSING)
        toolbar.title = spannableTitle

        setSupportActionBar(toolbar)
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
                }
            }
        })
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