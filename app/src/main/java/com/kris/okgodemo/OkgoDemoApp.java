package com.kris.okgodemo;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kris.
 */

public class OkgoDemoApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        OkGo.getInstance().init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            OkGo.getInstance().setCacheMode(CacheMode.NO_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
