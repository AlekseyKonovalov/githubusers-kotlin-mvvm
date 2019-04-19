package aleksey.projects.github_users.data.api.responces

import com.google.gson.annotations.SerializedName

data class VkProfileResponse(
    @SerializedName("response")
    val response: Response?
) {
    data class Response(
        @SerializedName("bdate")
        val bdate: String?,
        @SerializedName("bdate_visibility")
        val bdateVisibility: Int?,
        @SerializedName("city")
        val city: City?,
        @SerializedName("country")
        val country: Country?,
        @SerializedName("first_name")
        val firstName: String?,
        @SerializedName("home_town")
        val homeTown: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("relation")
        val relation: Int?,
        @SerializedName("screen_name")
        val screenName: String?,
        @SerializedName("sex")
        val sex: Int?,
        @SerializedName("status")
        val status: String?
    ) {
        data class Country(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?
        )

        data class City(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?
        )
    }
}