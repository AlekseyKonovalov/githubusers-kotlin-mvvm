package aleksey.projects.github_users.data.api.responces

import com.google.gson.annotations.SerializedName

data class VkUserInfoResponse(
    @SerializedName("response")
    val response: List<Response?>?
) {
    data class Response(
        @SerializedName("can_access_closed")
        val canAccessClosed: Boolean?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("is_closed")
        val isClosed: Boolean?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("photo_100")
        val photo100: String?
    )
}