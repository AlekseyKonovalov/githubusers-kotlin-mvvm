package aleksey.projects.github_users.screens.github_users_list.adapter

import aleksey.projects.github_users.R
import aleksey.projects.github_users.base.BindedViewHolder
import aleksey.projects.github_users.ext.bindView
import aleksey.projects.github_users.ext.inflate
import aleksey.projects.github_users.screens.github_users_list.listener.GithubUserClickListener
import aleksey.projects.github_users.screens.github_users_list.models.GithubUser
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class GithubUsersAdapter(private val context: Context) : RecyclerView.Adapter<GithubUsersAdapter.ResourcesViewHolder>() {

    private var items: MutableList<GithubUser> = mutableListOf()
    var itemClickListener: GithubUserClickListener? = null

    fun addItems(newItems: List<GithubUser>) {
        val startPosition = items.size
        items.addAll(newItems)
        notifyItemRangeChanged(startPosition, newItems.size)
    }

    fun setItems(items: MutableList<GithubUser>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourcesViewHolder {
        val view = parent.inflate(R.layout.item_github_user)
        view.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ResourcesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResourcesViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setItemClickListener(items[position])
    }

    inner class ResourcesViewHolder(val view: View) : BindedViewHolder<GithubUser>(view) {

        private val resourceImage: ImageView by bindView(R.id.github_user_avatar)
        private val resourceLogin: TextView by bindView(R.id.github_user_login)
        private val resourceUrl: TextView by bindView(R.id.github_user_repo_url)

        override fun bind(data: GithubUser) {
            resourceLogin.text = data.login
            resourceUrl.text = data.htmlUrl
            Picasso.get()
                .load(data.avatarUrl)
                .placeholder(R.drawable.ic_face)
                .error(R.drawable.ic_face)
                .transform(CropCircleTransformation())
                .into(resourceImage)
        }

        fun setItemClickListener(data: GithubUser) {
            view.setOnClickListener {
                itemClickListener?.onClick(data)
            }
        }
    }
}