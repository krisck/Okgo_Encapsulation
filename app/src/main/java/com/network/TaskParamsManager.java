package com.network;

/**
 * Created by kris.
 */

import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 请求参数拼接
 * 组成map
 */
public class TaskParamsManager {

    //分页，每一页最大数20条
    public static final int PageSize = 20;

    /**
     * volatile 防止指令重排
     * 通过“内存屏障”来防止指令被重排序
     */
    private static volatile TaskParamsManager mTaskLinkManager;

    public static TaskParamsManager getInstance(){
        if(mTaskLinkManager == null){
            synchronized (TaskParamsManager.class){
                //synchronized代码块内部虽然会重排序，但不会在代码块的范围内导致线程安全问题
                if(mTaskLinkManager == null){
                    mTaskLinkManager = new TaskParamsManager();
                }
            }
        }
        return mTaskLinkManager;
    }

    /**
     * Get
     * 读取版本信息
     * @return
     */
    public HashMap<String, Object> getVersionParams(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("TaskType", TaskType.TaskType_Version);
       // params.put("TaskMethod", "app/version");
        params.put("TaskMethod", "getVersion");
        params.put("RequestType", RequestType.GET);

        return params;
    }

    /**
     * Post
     * 读取首页列表数据
     * @param PageNo
     * @return
     */
    public HashMap<String, Object> getHomepageParams(int PageNo){
        HashMap<String, Object> params = new HashMap<>();
        params.put("TaskType", TaskType.TaskType_Homepage);
        params.put("TaskMethod", "homepage");
        params.put("RequestType", RequestType.POST);
        params.put("PageNo", PageNo);
        params.put("PageSize", PageSize);
        return params;
    }

    /**
     * Post Json
     * @param PageNo
     * @return
     */
    public HashMap<String, Object> getDescParams(int PageNo){
        HashMap<String, Object> params = new HashMap<>();
        params.put("TaskType", TaskType.TaskType_Desc);
        params.put("TaskMethod", "desc");
        params.put("RequestType", RequestType.POSTJSON);
        JSONObject obj = new JSONObject();
        try {
            obj.put("PageNo", PageNo);
            obj.put("PageSize", PageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //params.put("PageNo", PageNo);
       // params.put("PageSize", PageSize);
        params.put("json", obj.toString());
        return params;
    }

    /**
     * Put
     * @return
     */
    public HashMap<String, Object> getXXXParams(){
        HashMap<String, Object> params = new HashMap<>();
       // params.put("TaskType", TaskType.TaskType_Homepage);
        params.put("TaskMethod", "app/xxx");
        params.put("RequestType", RequestType.PUT);
        return params;
    }

    /**
     * 封装成HttpParams
     * @param i
     * @param j
     * @param b
     * @return
     */
    public HttpParams getYYYParams(int i, String j, boolean b){
        //HashMap<String, Object> params = new HashMap<>();
        HttpParams p = new HttpParams();
        p.put("TaskMethod", "app/xxx");
        p.put("RequestType", RequestType.PUT);
        p.put("i", i);
        p.put("j", j);
        p.put("b", b);
        return p;
    }
}
