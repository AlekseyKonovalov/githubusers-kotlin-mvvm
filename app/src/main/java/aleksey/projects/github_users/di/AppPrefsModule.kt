package aleksey.projects.github_users.di

import aleksey.projects.github_users.data.AppPrefs
import org.koin.dsl.module.module

val appPrefsModule = module {

    single { AppPrefs(get()) }

}