package com.yan.coderhelper

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.yan.coderhelper.callback.ApiSwitchCallBack
import com.yan.coderhelper.callback.AppApiSaveCallBack
import com.yan.coderhelper.callback.TokenSaveCallBack
import com.yan.coderhelper.floatview.FloatingMagnetView
import com.yan.coderhelper.floatview.FloatingView
import com.yan.coderhelper.floatview.MagnetViewListener
import com.yan.coderhelper.helper.CoderHelper
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 示例代码
 */
class MainActivity : AppCompatActivity() {

    companion object {
        var api: Int = 1//模拟当前环境
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initHelper()
        tv_api.text = "当前Api$api"
        tv_sp.text = SPUtils.getInstance().getString("SP")
        FloatingView.get().add()
        FloatingView.get().listener(object : MagnetViewListener {
            override fun onRemove(magnetView: FloatingMagnetView) {
            }

            override fun onClick(magnetView: FloatingMagnetView) {
                FunctionActivity.startActivity(this@MainActivity)
            }
        })
    }

    private fun initHelper() {
        /**
         *  将第一次启动的api切换为之前设置的环境变量，若之前没设置则按当前的环境运行
         *  若默认按照当前环境，无论之前有没有设置过其他环境，删除该行即可
         */
        api = if (CoderHelper.get().mCurrentApi == -1) api else CoderHelper.get().mCurrentApi
        CoderHelper.Builder().CurrentApi(api).ApiCallBack(object : ApiSwitchCallBack {
            override fun ApiSwitchListener(
                apiPosition: Int,
                appApiSaveLIstener: AppApiSaveCallBack
            ) {
                when (apiPosition) {
                    0 -> {
                        // todo dev
                        api = 1
                    }
                    1 -> {
                        // todo test
                        api = 2
                    }
                    2 -> {
                        // todo test2
                        api = 3
                    }
                    3 -> {
                        // todo test3
                        api = 4
                    }
                    4 -> {
                        // todo pre
                        api = 5
                    }
                    5 -> {
                        // todo online
                        api = 6
                    }
                }
                appApiSaveLIstener.AppApiSaveListener(api)
            }

        })
            .TokenSaveCallBack(object : TokenSaveCallBack{
                override fun SaveToken(token: String, appApiSaveCallBack: AppApiSaveCallBack) {
                    ToastUtils.showShort(token)
                    appApiSaveCallBack.AppApiSaveListener(6)
                }

            })

    }

    override fun onStart() {
        super.onStart()
        FloatingView.get().attach(this)
    }


    override fun onStop() {
        super.onStop()
        FloatingView.get().detach(this)
    }
}
