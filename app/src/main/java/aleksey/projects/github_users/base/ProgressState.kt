package aleksey.projects.github_users.base


sealed class ProgressState {
    class Initial: ProgressState()
    class Done: ProgressState()
    class Loading: ProgressState()
    class Error(var errorMessage: String? = null): ProgressState()
    class Finish: ProgressState()
}