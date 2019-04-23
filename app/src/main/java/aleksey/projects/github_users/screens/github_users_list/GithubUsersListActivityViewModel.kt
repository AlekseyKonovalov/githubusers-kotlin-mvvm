package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.base.BaseViewModel
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.repository.UserRepository
import aleksey.projects.github_users.screens.github_users_list.models.GithubUser
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class GithubUsersListActivityViewModel(
    val appPrefs: AppPrefs,
    val userRepository: UserRepository
) : ViewModel(), BaseViewModel, LifecycleObserver {

    private var disposables = CompositeDisposable()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()
    private var userData: MutableLiveData<Pair<String, String>> = MutableLiveData()
    private var githubUsers: MutableLiveData<List<GithubUser?>> = MutableLiveData()

    fun getGithubUsers(): MutableLiveData<List<GithubUser?>> {
        return githubUsers
    }

    override fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

    fun getUserData(): MutableLiveData<Pair<String, String>> {
        return userData
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getUserInfo() {
        if (!(appPrefs.getVkAccessToken().isNullOrEmpty())) {
            getVkUserInfo()
        }
    }

    private fun getVkUserInfo() {
        //account.getProfileInfoВозвращает информацию о текущем профиле.
        disposables += userRepository.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { progressState.postValue(ProgressState.Loading()) }
            .subscribe(
                {
                    Timber.d(it.toString())
                    userData.postValue(
                        Pair(
                            "${it.response?.first()?.firstName} ${it.response?.first()?.lastName}",
                            it.response?.first()?.photo100 ?: ""
                        )
                    )
                    progressState.postValue(ProgressState.Done())
                },
                {
                    Timber.e(it.toString())
                    progressState.postValue(ProgressState.Error())
                }
            )
    }

    fun clearToken() {
        appPrefs.cleanVkAccessToken()
        appPrefs.cleanVkUserId()
    }

    fun findGithubUsers(searchQuery: String){
        disposables += userRepository.searchGithubUsers(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(250, TimeUnit.MILLISECONDS)
            .subscribe(
                {
                    it.items?.let {
                        githubUsers.postValue(it)
                    }

                },
                {
                    Timber.e(it.toString())
                }
            )
    }




}