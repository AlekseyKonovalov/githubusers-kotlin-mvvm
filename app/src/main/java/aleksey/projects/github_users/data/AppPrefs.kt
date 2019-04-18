package aleksey.projects.github_users.data

import android.content.Context

private const val PREFS_APP = "search_github_users_app_prefs"

class AppPrefs(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE)

    fun clearPrefs() {
        sharedPreferences.edit().clear().apply()
    }


/*    fun setResourcesSince(since: Long) {
        sharedPreferences.edit().putLong(PREFS_KEY_RESOURCES_SINCE, since).apply()
    }

    fun getResourcesSince(): Long {
        return sharedPreferences.getLong(PREFS_KEY_RESOURCES_SINCE, 0)
    }

    fun cleanResourcesSince() {
        sharedPreferences.edit().remove(PREFS_KEY_RESOURCES_SINCE).apply()
    }*/


}