package com.silence.account.application;

import android.app.Application;

import com.silence.account.model.User;

import cn.bmob.v3.Bmob;




/**
 * Created by Silence on 2016/3/28 0028.
 */
public class AccountApplication extends Application {
//    private RefWatcher refWatcher;
    private static AccountApplication sAccountApplication;
    public static User sUser;

//    public static RefWatcher getRefWatcher(Context context) {
//        AccountApplication application = ( AccountApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    public static AccountApplication getApplication() {
        return sAccountApplication;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //Bmob.initialize(getApplicationContext(), "7930be502d4400a41d4f656fbd9c65cf");
        Bmob.initialize(getApplicationContext(), "15e0b29e8e1c7ec4200aa9a59b725645");
        sAccountApplication = this;
//        refWatcher = LeakCanary.install(this);

    }
}
