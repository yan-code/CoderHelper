package com.yan.coderhelper.helper

import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils

/**
 * @author Yan
 * @date 2019/10/24.
 * description：
 */
class PermissionHelper {

    companion object{
        private var instance: PermissionHelper? = null
            get() {
                if (field == null) {
                    field = PermissionHelper()
                }
                return field
            }
        fun get(): PermissionHelper{
            return instance!!
        }
    }

    fun requestCamera(listener: OnPermissionGrantedListener,
                      deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.CAMERA)
    }

    fun requestStorage(listener: OnPermissionGrantedListener,
                       deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.STORAGE)
    }

    fun requestPhone(listener: OnPermissionGrantedListener,
                     deniedListener: OnPermissionDeniedListener) {
        request(listener, deniedListener, PermissionConstants.PHONE)
    }

    private fun request(grantedListener: OnPermissionGrantedListener,
                        deniedListener: OnPermissionDeniedListener,
                        @PermissionConstants.Permission vararg permissions: String) {
        PermissionUtils.permission(*permissions)
            .rationale { shouldRequest -> DialogHelper.get().showRationaleDialog(shouldRequest) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                    grantedListener.onPermissionGranted()
                }

                override fun onDenied(permissionsDeniedForever: List<String>, permissionsDenied: List<String>) {
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    if (!permissionsDeniedForever.isEmpty()) {
                        DialogHelper.get().showOpenAppSettingDialog()
                        return
                    }
                    deniedListener.onPermissionDenied()
                }
            })
            .request()
    }

    interface OnPermissionGrantedListener {
        fun onPermissionGranted()
    }

    interface OnPermissionDeniedListener {
        fun onPermissionDenied()
    }
}