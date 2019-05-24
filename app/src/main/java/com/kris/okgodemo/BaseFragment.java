package com.kris.okgodemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.network.TaskListener;
import com.network.TaskType;

/**
 * Created by kris.
 */

public class BaseFragment extends Fragment implements TaskListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void taskFinished(TaskType type, Object result, boolean isHistory) {
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
