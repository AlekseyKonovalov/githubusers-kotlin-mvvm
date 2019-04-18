package aleksey.projects.github_users.base

sealed class ViewNotification {
    class Error(msg: String): ViewNotification()
    class Success(msg: String): ViewNotification()
}