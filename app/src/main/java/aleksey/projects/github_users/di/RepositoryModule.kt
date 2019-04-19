package aleksey.projects.github_users.di

import aleksey.projects.github_users.data.repository.UserRepository
import aleksey.projects.github_users.data.repository.UserRepositoryImpl
import org.koin.dsl.module.module

val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

}