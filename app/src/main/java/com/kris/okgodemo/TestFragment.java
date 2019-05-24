package com.kris.okgodemo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.network.TaskType;

/**
 * Created by kris.
 */

public class TestFragment extends BaseFragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void taskFinishedCustom(TaskType type, Object result, boolean isHistory) {
        super.taskFinishedCustom(type, result, isHistory);
    }
}
