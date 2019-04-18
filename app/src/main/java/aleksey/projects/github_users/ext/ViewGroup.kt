package aleksey.projects.github_users.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resId: Int, attach: Boolean = false): View {
    return LayoutInflater.from(context).inflate(resId, this, attach)
}