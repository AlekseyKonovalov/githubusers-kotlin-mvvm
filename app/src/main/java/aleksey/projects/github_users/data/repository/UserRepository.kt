package aleksey.projects.github_users.data.repository

import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.api.VkApi
import aleksey.projects.github_users.data.api.responces.VkProfileResponse
import aleksey.projects.github_users.data.api.responces.VkUserInfoResponse
import io.reactivex.Observable

interface UserRepository {
    fun getVkProfileInfo(): Observable<VkProfileResponse>
    fun getUserInfo(): Observable<VkUserInfoResponse>
}

class UserRepositoryImpl(
    private val vkApi: VkApi,
    private val appPrefs: AppPrefs
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

}
