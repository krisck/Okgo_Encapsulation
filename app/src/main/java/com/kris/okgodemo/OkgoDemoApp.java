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

    private List<Activity> activitys = new LinkedList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

        OkGo.getInstance().init(this);
    }

    public List<Activity> getActivityList(){
        return activitys;
    }

    /**
     * 退出指定名字的activity
     */
    public void removeActivityByName(String name) {
        if(activitys == null || activitys.size() == 0){
            return;
        }
        for(int i = 0; i < activitys.size(); i ++){
            Activity activity = activitys.get(i);
            if (null == activity) {
                break;
            }
            String activityName = activity.getComponentName().getClassName().toString();
            if (TextUtils.equals(name, activityName)) {
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    public void addActivity(Activity activity) {
        if(activitys == null){
            activitys = new LinkedList<Activity>();
        }
        if(activitys != null && activitys.size() > 0){
            if(!activitys.contains(activity)){
                activitys.add(activity);
            }
        }
        else{
            activitys.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if(activitys != null && activitys.size() > 0){
            if(activitys.contains(activity)){
                activitys.remove(activity);
            }
        }
    }

    public void finishActivityList() {
        if(activitys != null && activitys.size() > 0){
            for(Activity activity : activitys) {
                activity.finish();
            }
        }
    }

    public void exit() {
        if(activitys != null && activitys.size() > 0){
            for(Activity activity : activitys) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}
