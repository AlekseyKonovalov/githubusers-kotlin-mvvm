package aleksey.projects.github_users.data.api.responces

import aleksey.projects.github_users.screens.github_users_list.models.GithubUser
import com.google.gson.annotations.SerializedName

data class SearchGithubUsersResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<GithubUser?>?,
    @SerializedName("total_count")
    val totalCount: Int?
)