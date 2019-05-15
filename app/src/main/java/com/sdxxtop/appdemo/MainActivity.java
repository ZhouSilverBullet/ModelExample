package com.sdxxtop.appdemo;


import com.sdxxtop.app.App;
import com.sdxxtop.appdemo.di.DaggerMyActivityComponent;
import com.sdxxtop.appdemo.di.MyActivityComponent;
import com.sdxxtop.appdemo.presenter.CopyPresenter;
import com.sdxxtop.appdemo.presenter.constract.CopyContract;
import com.sdxxtop.base.BaseMvpActivity;
import com.sdxxtop.di.component.ActivityComponent;
import com.sdxxtop.di.component.DaggerActivityComponent;
import com.sdxxtop.di.module.ActivityModule;

public class MainActivity extends BaseMvpActivity<CopyPresenter> implements CopyContract.IView {

    @Override
    protected int getLayout() {
        return R.layout.activity_base_home;
    }

    @Override
    protected void initInject() {
        getMyActivityComponent().inject(this);
    }

    protected MyActivityComponent getMyActivityComponent() {
        return DaggerMyActivityComponent
                .builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadData();
    }

    @Override
    public void showError(String error) {
        showToast(error);
    }
}
