package aleksey.projects.github_users.data.api

import aleksey.projects.github_users.data.api.responces.SearchGithubUsersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/users")
    fun searchGithubUsers(
        @Query("q") searchQuery: String
    ): Observable<SearchGithubUsersResponse>
}