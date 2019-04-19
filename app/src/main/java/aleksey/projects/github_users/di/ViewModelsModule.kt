package aleksey.projects.github_users.di

import aleksey.projects.github_users.screens.github_users_list.GithubUsersListActivityViewModel
import aleksey.projects.github_users.screens.sign_in.SignInActivityViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val viewModelsModule = module {

    viewModel { SignInActivityViewModel(get()) }

    viewModel { GithubUsersListActivityViewModel(get(), get()) }

}