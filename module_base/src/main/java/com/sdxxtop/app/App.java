package com.sdxxtop.app;


import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.sdxxtop.app.base.BaseApp;
import com.sdxxtop.base.R;
import com.sdxxtop.di.component.AppComponent;
import com.sdxxtop.di.component.DaggerAppComponent;
import com.sdxxtop.di.module.AppModule;


public class App extends BaseApp {

    private static App instance;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLogger();
        AppSession.getInstance().init(this);
        initBaiduFace();
        CrashHandler.getInstance().init(this);
    }

    private void initBaiduFace() {
//        FaceSDKManager.getInstance().initialize(this, "luozhuang-face-android", "idl-license.face-android");
    }

    //初始化logger
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
//                .logStrategy(new LogcatLogStrategy())  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("sdxxtop-TAG")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static App getInstance() {
        return instance;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().appModule(new AppModule(instance)).build();
        }
        return appComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppSession.getInstance().clear();
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_F4F4F4, R.color.color_303030);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter classicsFooter = new ClassicsFooter(context);
                classicsFooter.setBackgroundColor(context.getResources().getColor(R.color.color_F4F4F4));
                return classicsFooter.setDrawableSize(20);
            }
        });
    }
}
