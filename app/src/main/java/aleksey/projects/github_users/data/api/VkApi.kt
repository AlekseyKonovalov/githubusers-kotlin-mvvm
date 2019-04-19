package aleksey.projects.github_users.data.api

import aleksey.projects.github_users.data.api.responces.VkProfileResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VkApi {
    @GET("method/account.getProfileInfo")
    fun getVkProfileInfo(
        @Query("access_token") accessToken: String,
        @Query("v") versionVkAPI: String
    ): Observable<VkProfileResponse>
}