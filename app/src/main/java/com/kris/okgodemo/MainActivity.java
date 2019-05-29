package com.kris.okgodemo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.network.OkgoTask;
import com.network.TaskParamsManager;
import com.network.TaskType;

import org.json.JSONObject;

public class MainActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //get
    public void onGetMethod(View v){
        OkgoTask.getInstance().startTask(TaskType.TaskType_Version, MainActivity.this,
                TaskParamsManager.getInstance().getVersionParams());
    }

    //post
    public void onPostMethod(View v){
        OkgoTask.getInstance().startTask(TaskType.TaskType_Homepage, MainActivity.this,
                TaskParamsManager.getInstance().getHomepageParams(0));
    }

    //post json
    public void onPostJsonMethod(View v){
        OkgoTask.getInstance().startTask(TaskType.TaskType_Desc, MainActivity.this,
                TaskParamsManager.getInstance().getDescParams(0));
    }

    @Override
    public void taskFinishedCustom(TaskType type, Object result, boolean isHistory) {
        super.taskFinishedCustom(type, result, isHistory);
        if(result == null){
            return;
        }
        if(result instanceof Error){
            ((TextView) findViewById(R.id.tv_result)).setText(((Error)result).getMessage());
            //TODO:
            return;
        }
        switch (type){
            case TaskType_Version:
                break;
            case TaskType_Homepage:
                break;
            case TaskType_Desc:
                break;
        }
        ((TextView) findViewById(R.id.tv_result)).setText(((JSONObject)result).toString());
    }
}
