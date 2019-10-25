package com.yan.coderhelper.helper

import android.app.AlertDialog
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.yan.coderhelper.R

/**
 * @author Yan
 * @date 2019/10/24.
 * description：
 */
class DialogHelper {
    companion object{
        private var instance: DialogHelper? = null
            get() {
                if (field == null) {
                    field = DialogHelper()
                }
                return field
            }
        fun get(): DialogHelper{
            return instance!!
        }
    }

    fun showRationaleDialog(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
        val topActivity = ActivityUtils.getTopActivity()
        AlertDialog.Builder(topActivity)
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage(R.string.permission_rationale_message)
            .setPositiveButton(android.R.string.ok) { dialog, which -> shouldRequest.again(true) }
            .setNegativeButton(android.R.string.cancel) { dialog, which -> shouldRequest.again(false) }
            .setCancelable(false)
            .create()
            .show()
    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity()
        AlertDialog.Builder(topActivity)
            .setTitle(android.R.string.dialog_alert_title)
            .setMessage(R.string.permission_denied_forever_message)
            .setPositiveButton(android.R.string.ok) { dialog, which -> PermissionUtils.launchAppDetailsSettings() }
            .setNegativeButton(android.R.string.cancel) { dialog, which -> }
            .setCancelable(false)
            .create()
            .show()
    }
}