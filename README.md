# CoderHelper
开发者工具
[![](https://jitpack.io/v/yan-code/CoderHelper.svg)](https://jitpack.io/#yan-code/CoderHelper)


# 集成方法
Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.yan-code:CoderHelper:1.2.3'
	}

# 使用方法
在Aplication页面添加初始化代码

        /**
         *  将第一次启动的api切换为之前设置的环境变量，若之前没设置则按当前的环境运行
         */
        HttpConstant.val = CoderHelper.Companion.get().getMCurrentApi() == -1? HttpConstant.val : CoderHelper.Companion.get().getMCurrentApi();
        CoderHelper.Companion.get().setMCurrentApi(HttpConstant.val);
        CoderHelper.Companion.get().setMApiCallBack(new ApiSwitchCallBack() {
            @Override
            public void ApiSwitchListener(int position, @NotNull AppApiSaveCallBack appApiSaveCallBack) {
                //环境切换逻辑
                switch (position){
                    case 0:
                        // dev
                        HttpConstant.val=HttpConstant.Envir.dev;
                        break;
                    case 1:
                        // test
                        HttpConstant.val=HttpConstant.Envir.test;
                        break;
                    case 2:
                        // test2
                        HttpConstant.val=HttpConstant.Envir.test_2;
                        break;
                    case 3:
                        // test3
                        HttpConstant.val = HttpConstant.Envir.test_3;
                        break;
                    case 4:
                        // pre
                        HttpConstant.val=HttpConstant.Envir.pre;
                        break;
                    case 5:
                        // online
                        HttpConstant.val=HttpConstant.Envir.online;
                        break;
                }
                //保存切换的环境变量
                appApiSaveCallBack.AppApiSaveListener(HttpConstant.val);
            }
        });
	 CoderHelper.get().mTokenCallBack = object : TokenSaveCallBack {

            override fun SaveToken(token: String, appApiSaveCallBack: AppApiSaveCallBack) {
	    	//替换本地token为需要验证的线上token
                UserUtil.init().token = token
                HttpConstant.`val` = HttpConstant.Envir.online
                appApiSaveCallBack.AppApiSaveListener(HttpConstant.`val`)
            }
        }
        if (HttpConstant.`val` == HttpConstant.Envir.online) {
            return
        }
        FloatingView.get().add()
        FloatingView.get().listener(object : MagnetViewListener {

            override fun onRemove(magnetView: FloatingMagnetView) {

            }

            override fun onClick(magnetView: FloatingMagnetView) {
                //跳转功能页面
                FunctionActivity.startActivity(context)
            }
        })

在BaseActivity添加
	
	@Override
    protected void onResume() {
        super.onResume();
        FloatingView.get().attach(this);
    }
	
    @Override
    protected void onPause() {
        super.onPause();
        FloatingView.get().detach(this);
    }
        
# 提示
需在root project下的build.gradle中支持kotlin

	buildscript {
    	ext.kotlin_version = '1.3.50'
    	...
    	dependencies{
     		classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
     	}
    }
