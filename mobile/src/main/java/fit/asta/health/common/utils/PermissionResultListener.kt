package fit.asta.health.common.utils

interface PermissionResultListener {
    fun onGranted(perm: String)
    fun onDenied(perm: String)
}