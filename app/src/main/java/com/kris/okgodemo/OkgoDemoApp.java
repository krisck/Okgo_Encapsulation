package com.kris.okgodemo;

import android.app.Activity;
import android.app.Application;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;

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
    }
}
