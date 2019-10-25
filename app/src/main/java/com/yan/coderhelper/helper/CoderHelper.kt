package com.yan.coderhelper.helper

import com.blankj.utilcode.util.SPUtils
import com.yan.coderhelper.HelperConstan
import com.yan.coderhelper.callback.ApiSwitchCallBack

/**
 * @author Yan
 * @date 2019/10/24.
 * description：
 */
class CoderHelper {

    companion object{
        private var instance: CoderHelper? = null
            get() {
                if (field == null) {
                    field = CoderHelper()
                }
                return field
            }
        fun get(): CoderHelper{
            return instance!!
        }
    }

    var mApiCallBack : ApiSwitchCallBack? = null
    var mCurrentApi : Int = SPUtils.getInstance().getInt(HelperConstan.SP_KEY.CURRENT_API,-1)

    class Builder{

        fun ApiCallBack(apiCallBack:ApiSwitchCallBack):Builder{
            build().mApiCallBack = apiCallBack
            return this
        }

        fun CurrentApi(api:Int):Builder{
            build().mCurrentApi = api
            SPUtils.getInstance().put(HelperConstan.SP_KEY.CURRENT_API, api)
            return this
        }

        /**
         * 创建
         *
         * @return
         */
        fun build(): CoderHelper {
            return CoderHelper.get()
        }

    }
}