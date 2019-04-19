package aleksey.projects.github_users.app


import aleksey.projects.github_users.di.apiModule
import aleksey.projects.github_users.di.appPrefsModule
import aleksey.projects.github_users.di.repositoryModule
import aleksey.projects.github_users.di.viewModelsModule
import android.app.Application
import com.facebook.stetho.Stetho
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import org.koin.android.ext.android.startKoin
import timber.log.Timber


class GithubUsersApp : Application() {

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)

        startKoin(
            this, listOf(
                appPrefsModule,
                viewModelsModule,
                apiModule,
                repositoryModule
            )
        )

        Stetho.initializeWithDefaults(this)
    }
}