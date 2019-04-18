package aleksey.projects.github_users.app


import aleksey.projects.github_users.di.appPrefsModule
import aleksey.projects.github_users.di.viewModelsModule
import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class GithubUsersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin(
            this, listOf(
                appPrefsModule,
                viewModelsModule
            )
        )

        Stetho.initializeWithDefaults(this)
    }
}