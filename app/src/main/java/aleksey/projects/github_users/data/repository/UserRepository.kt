package aleksey.projects.github_users.data.repository

import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.api.GithubApi
import aleksey.projects.github_users.data.api.VkApi
import aleksey.projects.github_users.data.api.responces.SearchGithubUsersResponse
import aleksey.projects.github_users.data.api.responces.VkProfileResponse
import aleksey.projects.github_users.data.api.responces.VkUserInfoResponse
import io.reactivex.Observable

interface UserRepository {
    fun getVkProfileInfo(): Observable<VkProfileResponse>
    fun getUserInfo(): Observable<VkUserInfoResponse>
    fun searchGithubUsers(searchQuery: String): Observable<SearchGithubUsersResponse>
}

class UserRepositoryImpl(
    private val vkApi: VkApi,
    private val appPrefs: AppPrefs,
    private val githubApi: GithubApi
) : UserRepository {

    override fun getVkProfileInfo(): Observable<VkProfileResponse> {
        return vkApi.getVkProfileInfo(appPrefs.getVkAccessToken(), appPrefs.getVkVersionApi())
    }

    override fun getUserInfo(): Observable<VkUserInfoResponse> {
        return vkApi.getExtendedUsersInfo(
            appPrefs.getVkUserId(),
            "photo_100",
            appPrefs.getVkAccessToken(),
            appPrefs.getVkVersionApi()
        )
    }

    override fun searchGithubUsers(searchQuery: String): Observable<SearchGithubUsersResponse> {
        return githubApi.searchGithubUsers(searchQuery)
    }

}
