package com.kris.okgodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.network.TaskListener;
import com.network.TaskType;

/**
 * Created by kris.
 *
 * 基类实现TaskListener
 *
 */

public class BaseActivity extends Activity implements TaskListener{

    /**
     * 父容器， 带自定义顶部bar或者toolbar
     */
    protected LinearLayout mMainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLayout = (LinearLayout) View.inflate(this, R.layout.activity_base, null);
        setContentView(mMainLayout);
    }

    @Override
    public void setContentView(int id){
        if(mMainLayout != null){
            mMainLayout.addView(View.inflate(this, id, null), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    @Override
    public void taskFinished(TaskType type, Object result, boolean isHistory) {
        /**
         * 返回Exception暂定义为token invalid，需重新登录,
         * 其他异常定义为Error返回
         * error或者正常返回不截获，通过taskFinishedCustom方法响应
         */
        if(result != null && result instanceof Exception){
            //TODO:
            // kill all activity
            // go to login page
            return;
        }

        taskFinishedCustom(type, result, isHistory);
    }

    protected void taskFinishedCustom(TaskType type, Object result, boolean isHistory){

    }
}
