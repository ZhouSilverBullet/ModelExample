package com.sdxxtop.appdemo.di;

import android.app.Activity;

import com.sdxxtop.appdemo.MainActivity;
import com.sdxxtop.di.component.AppComponent;
import com.sdxxtop.di.module.ActivityModule;
import com.sdxxtop.di.qualifier.ActivityScope;

import dagger.Component;

/**
 * @Author: zhousaito
 * @Date: 2019-04-29 08:25
 * @Version 1.0
 * @UserWhat what
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface MyActivityComponent {
    Activity getActivity();

    void inject(MainActivity mainActivity);
}
