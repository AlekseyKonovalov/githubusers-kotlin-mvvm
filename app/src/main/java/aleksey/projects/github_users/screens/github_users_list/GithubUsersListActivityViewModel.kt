package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.base.BaseViewModel
import aleksey.projects.github_users.base.ProgressState
import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.repository.UserRepository
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class GithubUsersListActivityViewModel(val appPrefs: AppPrefs,
                                       val userRepository: UserRepository) : ViewModel(), BaseViewModel, LifecycleObserver {

    private var disposables = CompositeDisposable()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()

    override fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        disposables.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun getUserInfo() {
        if (!(appPrefs.getVkAccessToken().isNullOrEmpty())){
            getVkUserInfo()
        }
    }

    private fun getVkUserInfo(){
        //account.getProfileInfoВозвращает информацию о текущем профиле.
        disposables += userRepository.getVkProfileInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Timber.d(it.toString())
                },
                {
                    Timber.e(it.toString())
                }
            )
    }


}