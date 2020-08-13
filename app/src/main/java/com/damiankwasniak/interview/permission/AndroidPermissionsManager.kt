package com.damiankwasniak.interview.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.damiankwasniak.data.AppPrefs
import java.util.*
import kotlin.math.abs

class AndroidPermissionsManager(private val prefs: AppPrefs) :
    PermissionsManager {

    override fun askForPermission(ctx: Context, permission: Permission, rationale: String) {
        val code = createRandomRequestCode()
        requestPermissionFromActivity(ctx, permission, code, rationale)
    }

    override fun askForPermission(activity: AppCompatActivity, permission: Permission, rationale: String) {
        val code = createRandomRequestCode()
        requestPermissionFromActivity(activity, permission, code, rationale)
    }

    override fun askForPermission(fragment: Fragment, permission: Permission, rationale: String) {
        val code = createRandomRequestCode()
        requestPermissionFromFragment(fragment, permission, code, rationale)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray): PermissionResult {
        if (grantResults.isEmpty()) return PermissionResult.Unknown
        val permission = permissions[0]
        return if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            PermissionResult.Granted(permission)
        } else {
            PermissionResult.Denied(permission)
        }
    }

    override fun checkPermission(ctx: Context, permission: Permission): PermissionState = when (hasPermission(ctx, permission)) {
        true -> PermissionState.Granted(permission)
        false -> when (ActivityCompat.shouldShowRequestPermissionRationale(ctx as AppCompatActivity, permission)) {
            true -> PermissionState.PreviouslyDenied(permission)
            false -> if (prefs.isFirstAsking(permission)) {
                prefs.setFirstAsking(permission, false)
                PermissionState.NeedPermission(permission)
            } else {
                PermissionState.PermanentlyDenied(permission)
            }
        }
    }

    private fun requestPermissionFromActivity(ctx: Context, permission: Permission, code: Int, rationale: String) {
        val activity = (ctx as? AppCompatActivity) ?: return
        ActivityCompat.requestPermissions(activity, arrayOf(permission), code)
    }

    private fun requestPermissionFromFragment(ctx: Fragment, permission: Permission, code: Int, rationale: String) {
        val fragment = (ctx as? Fragment) ?: return
        fragment.requestPermissions(arrayOf(permission), code)
    }

    private fun createRandomRequestCode(): Int = abs(Random().nextInt() % 10000)

    private fun hasPermission(ctx: Context, permission: Permission): Boolean =
        ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED

}