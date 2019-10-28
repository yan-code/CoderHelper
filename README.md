# CoderHelper
开发者工具
[![](https://jitpack.io/v/yan-code/CoderHelper.svg)](https://jitpack.io/#yan-code/CoderHelper)
# 使用方法
 FloatingView.get().add();
        FloatingView.get().listener(new MagnetViewListener() {

            @Override
            public void onRemove(FloatingMagnetView magnetView) {

            }

            @Override
            public void onClick(FloatingMagnetView magnetView) {
                //跳转功能页面
                FunctionActivity.Companion.startActivity(SealManageApplication.this);
            }
        });
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
                        // pre
                        HttpConstant.val=HttpConstant.Envir.pre;
                        break;
                    case 4:
                        // online
                        HttpConstant.val=HttpConstant.Envir.online;
                        break;
                }
                //保存切换的环境变量
                appApiSaveCallBack.AppApiSaveListener(HttpConstant.val);
            }
        });
