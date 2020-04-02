package com.yan.coderhelper

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.*
import com.yan.coderhelper.adapter.FunctionListAdapter
import com.yan.coderhelper.adapter.InfoListAdapter
import com.yan.coderhelper.bean.FunctionBean
import com.yan.coderhelper.bean.InfoBean
import com.yan.coderhelper.callback.AppApiSaveCallBack
import com.yan.coderhelper.dialog.LogcatDialog
import com.yan.coderhelper.helper.CoderHelper
import com.yan.coderhelper.helper.PermissionHelper
import kotlinx.android.synthetic.main.activity_function.*


/**
 * @author Yan
 * @date 2019/10/23.
 * description：功能清单
 */
class FunctionActivity : AppCompatActivity() {
    //------------------------------功能清单----------------------------------
    private lateinit var mFunctionName: Array<String>
    private lateinit var mFunctionRes: TypedArray
    private var mFunctionList = mutableListOf<FunctionBean>()
    private lateinit var mFunctionAdapter: FunctionListAdapter
    //-------------------------------App信息--------------------------------------------
    private var mAppInfoBeanList = mutableListOf<InfoBean>()
    private var mAppInfoBottomDialog: BottomSheetDialog? = null
    //--------------------------------设备信息-----------------------------------------------
    private var mDeviceInfoBeanList = mutableListOf<InfoBean>()
    private var mDeviceInfoBottomDialog: BottomSheetDialog? = null
    //------------------------------环境切换----------------------------------
    private lateinit var mApiName: Array<String>
    private lateinit var mApiRes: TypedArray
    private var mApiList = mutableListOf<FunctionBean>()
    private lateinit var mApiAdapter: FunctionListAdapter
    private var mApiBottomDialog: BottomSheetDialog? = null
    //------------------------------线上问题查验----------------------------------
    private var mTokenBottomDialog: BottomSheetDialog? = null

    companion object {
        fun startActivity(context: Context) {
            var intent = Intent(context, FunctionActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)
        SPUtils.getInstance().put("SP", "ABC")
        init()
    }

    private fun init() {
        ll_function.setOnClickListener {
            finish()
        }
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        mFunctionName = resources.getStringArray(R.array.function)
        mFunctionRes = resources.obtainTypedArray(R.array.functionRes)
        for ((index, s) in mFunctionName.withIndex()) {
            var function = FunctionBean(s, mFunctionRes.getDrawable(index))
            mFunctionList.add(function)
        }
        mFunctionAdapter =
            FunctionListAdapter(mFunctionList, object : FunctionListAdapter.onItemClickListener {
                override fun onItemClickListener(view: View, position: Int) {
                    when (position) {
                        0 -> showAppInfo(mFunctionList[position].name)
                        1 -> {
                            PermissionHelper.get().requestPhone(object :
                                PermissionHelper.OnPermissionGrantedListener {
                                override fun onPermissionGranted() {
                                    showDeviceInfo(mFunctionList[position].name)
                                }

                            }, object : PermissionHelper.OnPermissionDeniedListener {
                                override fun onPermissionDenied() {
                                }
                            })
                        }
                        2 -> ApiSwitch(mFunctionList[position].name)
                        3 -> {
                            CleanUtils.cleanInternalSp()
                            CleanUtils.cleanInternalCache()
                            CleanUtils.cleanInternalDbs()
                            CleanUtils.cleanExternalCache()
                            ToastUtils.showShort("清除缓存成功！")
                            Handler().postDelayed({
                                AppUtils.relaunchApp(true)
                            }, 500)
                        }
                        4 -> {
                            ToastUtils.showShort(mFunctionList[position].name)
                        }
                        5 -> LogcatDialog(this@FunctionActivity).show()
                        6-> showEtToken(mFunctionList[position].name)
                    }
                }

            })
        recyclerView.adapter = mFunctionAdapter

        //api数组初始化
        mApiName = resources.getStringArray(R.array.api)
        mApiRes = resources.obtainTypedArray(R.array.apiRes)
        for ((index, s) in mApiName.withIndex()) {
            var api = FunctionBean(s, mApiRes.getDrawable(index))
            mApiList.add(api)
        }

    }

    private fun showAppInfo(title: String) {
        if (mAppInfoBottomDialog == null) {
            var mArrayAppInfo = resources.getStringArray(R.array.app_info)
            for ((index, s) in mArrayAppInfo.withIndex()) {
                var infoBean = InfoBean(s)
                when (index) {
                    0 -> infoBean.infoValue = AppUtils.getAppPackageName()
                    1 -> infoBean.infoValue = AppUtils.getAppVersionName()
                    2 -> infoBean.infoValue = AppUtils.getAppVersionCode().toString()
                    3 -> {
                        when (CoderHelper.get().mCurrentApi) {
                            1 -> infoBean.infoValue = mApiList[0].name
                            2 -> infoBean.infoValue = mApiList[1].name
                            3 -> infoBean.infoValue = mApiList[2].name
                            4 -> infoBean.infoValue = mApiList[3].name
                            5 -> infoBean.infoValue = mApiList[4].name
                            6 -> infoBean.infoValue = mApiList[5].name
                        }
                    }
                    4 -> {
                        when (NetworkUtils.getNetworkType().name) {
                            NetworkUtils.NetworkType.NETWORK_WIFI.name -> infoBean.infoValue =
                                "WIFI"
                            NetworkUtils.NetworkType.NETWORK_4G.name -> infoBean.infoValue = "4G"
                            NetworkUtils.NetworkType.NETWORK_3G.name -> infoBean.infoValue = "3G"
                            NetworkUtils.NetworkType.NETWORK_2G.name -> infoBean.infoValue = "2G"
                            NetworkUtils.NetworkType.NETWORK_ETHERNET.name -> infoBean.infoValue =
                                "以太网"
                            NetworkUtils.NetworkType.NETWORK_NO.name -> infoBean.infoValue = "无网络"
                            NetworkUtils.NetworkType.NETWORK_UNKNOWN.name -> infoBean.infoValue =
                                "UNKNOWN"
                        }

                    }
                }
                mAppInfoBeanList.add(infoBean)
            }
            mAppInfoBottomDialog = BottomSheetDialog(this, R.style.dialog)
            mAppInfoBottomDialog!!.setContentView(
                createInfoView(
                    title,
                    mAppInfoBeanList,
                    View.OnClickListener { mAppInfoBottomDialog?.dismiss() })
            )
        }
        mAppInfoBottomDialog?.show()

    }

    private fun createInfoView(
        title: String,
        list: MutableList<InfoBean>,
        onClickListener: View.OnClickListener
    ): View {
        var mInfoView = View.inflate(this, R.layout.dialog_info_view, null)
        var mTvTitle = mInfoView.findViewById<TextView>(R.id.tv_title)
        mTvTitle.text = title
        var mIvClose = mInfoView.findViewById<ImageView>(R.id.iv_close)
        mIvClose.setOnClickListener(onClickListener)
        var mRvInfoView = mInfoView.findViewById<RecyclerView>(R.id.rv_infoView)
        mRvInfoView.layoutManager = LinearLayoutManager(this)
        var mInfoAdapter = InfoListAdapter(list)
        mRvInfoView.adapter = mInfoAdapter
        return mInfoView
    }

    private fun showEtToken(title: String) {
        if (mTokenBottomDialog == null) {
            mTokenBottomDialog = BottomSheetDialog(this, R.style.dialog)
            mTokenBottomDialog!!.setContentView(
                createEditTextView(
                    title,
                    View.OnClickListener { mTokenBottomDialog?.dismiss() })
            )
        }
        mTokenBottomDialog?.show()

    }

    private fun createEditTextView(
        title: String,
        onClickListener: View.OnClickListener
    ): View {
        var mEtView = View.inflate(this, R.layout.dialog_editext_view, null)
        var mTvTitle = mEtView.findViewById<TextView>(R.id.tv_title)
        mTvTitle.text = title
        var mIvClose = mEtView.findViewById<ImageView>(R.id.iv_close)
        mIvClose.setOnClickListener(onClickListener)
        var mEtToken = mEtView.findViewById<EditText>(R.id.et_token)
        var mBtnSave = mEtView.findViewById<Button>(R.id.btn_save)
        mBtnSave.setOnClickListener {
            var token = mEtToken.text.toString().trim()
            if (TextUtils.isEmpty(token)){
                ToastUtils.showShort("请输入token")
            }else{
                CoderHelper.get().mTokenCallBack?.SaveToken(token, object : AppApiSaveCallBack {
                    override fun AppApiSaveListener(api: Int) {
                        CoderHelper.Builder().CurrentApi(api)
                    }
                })
                mAppInfoBottomDialog?.dismiss()
                Handler().postDelayed({
                    AppUtils.relaunchApp(true)
                }, 500)
            }
        }
        return mEtView
    }

    private fun showDeviceInfo(title: String) {
        if (mDeviceInfoBottomDialog == null) {
            var mArrayDeviceInfo = resources.getStringArray(R.array.device_info)
            for ((index, s) in mArrayDeviceInfo.withIndex()) {
                var infoBean = InfoBean(s)
                when (index) {
                    0 -> infoBean.infoValue = DeviceUtils.isDeviceRooted().toString()
                    1 -> infoBean.infoValue = DeviceUtils.getSDKVersionName()
                    2 -> infoBean.infoValue = DeviceUtils.getSDKVersionCode().toString()
                    3 -> infoBean.infoValue = DeviceUtils.getAndroidID()
                    4 -> infoBean.infoValue = DeviceUtils.getMacAddress()
                    5 -> infoBean.infoValue = DeviceUtils.getManufacturer()
                    6 -> infoBean.infoValue = DeviceUtils.getModel()
                    7 -> infoBean.infoValue = PhoneUtils.getIMEI()

                }
                mDeviceInfoBeanList.add(infoBean)
            }
            mDeviceInfoBottomDialog = BottomSheetDialog(this, R.style.dialog)
            mDeviceInfoBottomDialog!!.setContentView(
                createInfoView(
                    title,
                    mDeviceInfoBeanList,
                    View.OnClickListener { mDeviceInfoBottomDialog?.dismiss() })
            )
        }
        mDeviceInfoBottomDialog?.show()
    }


    private fun ApiSwitch(title: String) {
        var mApiView = View.inflate(this, R.layout.dialog_info_view, null)
        var mTvTitle = mApiView.findViewById<TextView>(R.id.tv_title)
        mTvTitle.text = title
        var mIvClose = mApiView.findViewById<ImageView>(R.id.iv_close)
        mIvClose.setOnClickListener {
            mApiBottomDialog?.dismiss()
        }
        var mRvApiView = mApiView.findViewById<RecyclerView>(R.id.rv_infoView)
        mRvApiView.layoutManager = GridLayoutManager(this, 4)
        mApiAdapter =
            FunctionListAdapter(mApiList, object : FunctionListAdapter.onItemClickListener {
                override fun onItemClickListener(view: View, position: Int) {
                    ToastUtils.showShort(mApiList[position].name)
                    CoderHelper.get().mApiCallBack?.ApiSwitchListener(position,
                        object : AppApiSaveCallBack {
                            override fun AppApiSaveListener(api: Int) {
                                CoderHelper.Builder().CurrentApi(api)
                            }
                        })
                    mApiBottomDialog?.dismiss()
                    Handler().postDelayed({
                        AppUtils.relaunchApp(true)
                    }, 500)
                }
            })
        mRvApiView.adapter = mApiAdapter
        mApiBottomDialog = BottomSheetDialog(this, R.style.dialog)
        mApiBottomDialog!!.setContentView(mApiView)
        mApiBottomDialog?.show()
    }

}