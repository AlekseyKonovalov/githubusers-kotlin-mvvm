package aleksey.projects.github_users.data.repository

import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.api.VkApi
import aleksey.projects.github_users.data.api.responces.VkProfileResponse
import io.reactivex.Observable

interface UserRepository {
    fun getVkProfileInfo(): Observable<VkProfileResponse>
}

class UserRepositoryImpl(
    private val vkApi: VkApi,
    private val appPrefs: AppPrefs): UserRepository{

    override fun getVkProfileInfo(): Observable<VkProfileResponse> {
        return vkApi.getVkProfileInfo(appPrefs.getVkAccessToken(), appPrefs.getVkVersionApi())
    }

}
