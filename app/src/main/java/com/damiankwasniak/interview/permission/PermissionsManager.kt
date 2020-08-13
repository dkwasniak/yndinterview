package com.damiankwasniak.interview.permission

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface PermissionsManager {

    fun askForPermission(ctx: Context, permission: Permission, rationale: String = "")
    fun askForPermission(fragment: AppCompatActivity, permission: Permission, rationale: String = "")
    fun askForPermission(fragment: Fragment, permission: Permission, rationale: String = "")
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out Permission>, grantResults: IntArray): PermissionResult
    fun checkPermission(ctx: Context, permission: Permission): PermissionState


    companion object {
        const val LOCATION_PERMISSION: Permission = Manifest.permission.ACCESS_FINE_LOCATION
        const val CAMERA_PERMISSION: Permission = Manifest.permission.CAMERA
    }
}

sealed class PermissionState(val permission: Permission) {
    data class Granted(private val _permission: Permission): PermissionState(_permission)
    class PreviouslyDenied(permission: Permission): PermissionState(permission)
    class PermanentlyDenied(permission: Permission): PermissionState(permission)
    class NeedPermission(permission: Permission): PermissionState(permission)
}

sealed class PermissionResult(val permission: Permission) {
    class Granted(permission: Permission): PermissionResult(permission)
    class Denied(permission: Permission): PermissionResult(permission)
    object Unknown: PermissionResult("")
}

typealias Permission = String
