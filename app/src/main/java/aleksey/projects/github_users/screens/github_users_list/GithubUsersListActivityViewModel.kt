package aleksey.projects.github_users.screens.github_users_list

import aleksey.projects.github_users.base.BaseViewModel
import aleksey.projects.github_users.base.ProgressState
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable

class GithubUsersListActivityViewModel : ViewModel(), BaseViewModel, LifecycleObserver {

    private var disposables = CompositeDisposable()
    private var progressState: MutableLiveData<ProgressState> = MutableLiveData()

    override fun getProgressState(): MutableLiveData<ProgressState> {
        return progressState
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dispose() {
        disposables.dispose()
    }

}