package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BaseView
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.ext.bindView
import aleksey.projects.github_users.screens.github_users_list.adapter.GithubUsersAdapter
import aleksey.projects.github_users.screens.github_users_list.listener.GithubUserClickListener
import aleksey.projects.github_users.screens.github_users_list.models.GithubUser
import aleksey.projects.github_users.screens.sign_in.startSignInActivity
import android.animation.LayoutTransition
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.TransitionManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
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
    private val drawerLayout: DrawerLayout by bindView(R.id.drawer_layout)
    private val navigationView: NavigationView by bindView(R.id.nvView)
    private var drawerToggle: ActionBarDrawerToggle? = null
    private lateinit var githubUsersAdapter: GithubUsersAdapter
    private val githubUsersList: RecyclerView by bindView(R.id.github_user_recycler_view)

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
        searchPlate.setBackgroundColor(
            ContextCompat.getColor(
                this@GithubUsersListActivityView,
                android.R.color.transparent
            )
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.findGithubUsers(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
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
        spannableTitle.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorAccent)),
            dotPosition + 1,
            spannableTitle.length,
            Spannable.SPAN_COMPOSING
        )
        toolbar.title = spannableTitle

        setSupportActionBar(toolbar)

        toolbar.navigationIcon = ContextCompat.getDrawable(this@GithubUsersListActivityView, R.drawable.ic_menu)
        drawerToggle = ActionBarDrawerToggle(
            this@GithubUsersListActivityView,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerToggle?.let {
            drawerLayout.addDrawerListener(it)
        }
    }

    override fun initViews() {
        githubUsersAdapter = GithubUsersAdapter(this@GithubUsersListActivityView)
        githubUsersList.layoutManager = LinearLayoutManager(this@GithubUsersListActivityView)
        githubUsersList.adapter = githubUsersAdapter
        githubUsersAdapter.itemClickListener = object : GithubUserClickListener {
            override fun onClick(data: GithubUser) {
                val uri = Uri.parse(data.htmlUrl)
                val intent = Intent (Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

        }
    }

    override fun initListeners() {
        navigationView.setNavigationItemSelectedListener(
            object : NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    when (menuItem.itemId) {
                        R.id.action_logout -> {
                            viewModel.clearToken()
                            startSignInActivity(this@GithubUsersListActivityView)
                            finish()
                        }
                        else -> {
                        }
                    }
                    drawerLayout.closeDrawers()
                    return true
                }
            })
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

        viewModel.getGithubUsers().observe(this, Observer {
            githubUsersAdapter.setItems(it as MutableList<GithubUser>)
        })

        viewModel.getUserData().observe(this, Observer {
            val userName = findViewById<TextView>(R.id.user_name)
            userName.text = it.first
            val userImage = findViewById<ImageView>(R.id.user_image)
            Picasso.get()
                .load(it.second)
                .placeholder(R.drawable.ic_face)
                .error(R.drawable.ic_face)
                .transform(CropCircleTransformation())
                .into(userImage)
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