package aleksey.projects.github_users.data

import android.content.Context

private const val PREFS_APP = "search_github_users_app_prefs"

private const val VK_ACCESS_TOKKEN = "vk_access_token"
private const val VK_USER_ID = "vk_user_id"
private const val VK_VERSION_API = "vk_version_api"

class AppPrefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE)

    fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }


    fun getVkVersionApi(): String {
        return sharedPreferences.getString(VK_VERSION_API, "5.95")
    }

    fun setVkVersionApi(version: String) {
        sharedPreferences.edit().putString(VK_VERSION_API, version).apply()
    }

    fun cleanVkVersionApi() {
        sharedPreferences.edit().remove(VK_VERSION_API).apply()
    }


    fun setVkAccessToken(token: String) {
        sharedPreferences.edit().putString(VK_ACCESS_TOKKEN, token).apply()
    }

    fun getVkAccessToken(): String {
        return sharedPreferences.getString(VK_ACCESS_TOKKEN, "")
    }

    fun cleanVkAccessToken() {
        sharedPreferences.edit().remove(VK_ACCESS_TOKKEN).apply()
    }

    fun setVkUserId(id: String) {
        sharedPreferences.edit().putString(VK_USER_ID, id).apply()
    }

    fun getVkUserId(): String {
        return sharedPreferences.getString(VK_USER_ID, "")
    }

    fun cleanVkUserId() {
        sharedPreferences.edit().remove(VK_USER_ID).apply()
    }

}