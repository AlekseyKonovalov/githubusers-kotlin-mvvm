package aleksey.projects.github_users.base


import androidx.lifecycle.MutableLiveData

interface BaseViewModel {

    fun getProgressState(): MutableLiveData<ProgressState>

    fun dispose()

}