package com.yan.coderhelper.helper

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.*
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