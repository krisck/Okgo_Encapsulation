package com.network;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by kris.
 * 按自己使用习惯封装一下okgo请求
 * FYI
 */

public class OkgoTask {

    /**
     * 是否使用测试服务器
     */
    public static final boolean UseTestServer = false;

    public static final String ServerUrl = UseTestServer? "http://xxxx:8081/api/" : "http://192.168.1.90:8081/api/";

    public static final String UTF8_BOM = "\uFEFF";

    /**
     * volatile是一个类型修饰符（type specifier）.volatile的作用是作为指令关键字，确保本条指令不会因编译器的优化而省略，且要求每次直接读值。
     * volatile的变量是说这变量可能会被意想不到地改变，这样，编译器就不会去假设这个变量的值了。
     */
    public static volatile OkgoTask mOkgoTask;

    private TaskType mTaskType;
    private TaskListener mTaskListener;

    /**
     * 获取task单例
     * @return
     */
    public static OkgoTask getInstance(){
        if(mOkgoTask == null){
            synchronized (OkgoTask.class){
                if(mOkgoTask == null){
                    mOkgoTask = new OkgoTask();
                }
            }
        }
        return mOkgoTask;
    }

    /**
     * 启动任务，连接服务器
     * @param taskType
     * @param taskListener
     * @param params
     */
    public void startTask(final TaskType taskType, final TaskListener taskListener, HashMap<String, Object> params){
        mTaskType = taskType;
        mTaskListener = taskListener;
        if(params == null){
            //TODO:
            if(mTaskListener != null){
                mTaskListener.taskFinished(mTaskType, new Error("参数异常"), false);
                //mTaskListener = null;
            }
            return;
        }
        switch ((String)params.get("RequestType")){
            case RequestType.GET:
                getMode(params);
                break;
            case RequestType.POST:
                postMode(params);
                break;
            case RequestType.POSTJSON:
                postJsonMode(params);
                break;
            case RequestType.PUT:
                //TODO:
                break;
        }
    }

    /**
     * GET类型请求
     */
    private void getMode(HashMap<String, Object> params){
        String url = ServerUrl + params.get("TaskMethod") + getParamsStr(params);
        System.out.println("==**==url==== " + url);
        OkGo.<String>get(url)
            .execute(mCallBack);
    }

    /**
     * POST
     */
    private void postMode(HashMap<String, Object> params){
        String url = ServerUrl + params.get("TaskMethod");
        System.out.println("==**==url==== " + url);

        // 添加header
//        PostRequest rq = OkGo.post(url);
//        rq.params(getHttpParams(params));
//        //rq.headers("","");
//        rq.execute(mCallBack);

        OkGo.<String>post(url)
            .params(getHttpParams(params))
            .execute(mCallBack);
    }

    /**
     * post json
     * @param params
     */
    public void postJsonMode(HashMap<String, Object> params){
        String url = ServerUrl + params.get("TaskMethod");
        System.out.println("==**==url==== " + url);
        OkGo.<String>post(url)
            .upJson(params.get("json").toString())
            .execute(mCallBack);
    }

    /**
     *  请求结果回调
     */
    StringCallback mCallBack = new StringCallback() {
        @Override
        public void onSuccess(Response<String> response) {
            System.out.println("==response.body= " + response.body());
            if(mTaskListener != null){
                mTaskListener.taskFinished(mTaskType, getSuccessReulst(response.body()), false);
                //mTaskListener = null;
            }
        }

        @Override
        public void onError(Response<String> response) {
            super.onError(response);
            System.out.println("==response.onError= " + response.getException().getMessage());
            /**
             * 解析json， 判断errorCode或者Msg, 如果token invalid登录失效，封装成Exception返回
             *
             * 其他异常封装成Error返回
             */
            if(mTaskListener != null){
                //mTaskListener.taskFinished(mTaskType, new Exception("token invalid"), false);
            }
            if(mTaskListener != null){
                //mTaskListener.taskFinished(mTaskType, new Error(response.body()), false);
            }

            if(mTaskListener != null){
                mTaskListener.taskFinished(mTaskType, new Error(response.getException().getMessage()), false);
            }
            //mTaskListener = null;
        }
    };

    /**
     * 添加请求头
     */
    public void addHeader(){

    }

    /**
     * post 的参数需要封装成HttpParams
     * @param params
     * @return
     */
    public HttpParams getHttpParams(HashMap<String, Object> params){
        HttpParams httpParams = new HttpParams();
        for (String key : params.keySet()) {
            if(key.equalsIgnoreCase("TaskType")
                    || key.equalsIgnoreCase("TaskMethod")
                    || key.equalsIgnoreCase("RequestType")){
                continue;
            }
            httpParams.put(key, params.get(key).toString());
        }
        return httpParams;
    }

    /**
     * 在这里将返回数据转成JSONObject或者JSONArray对象
     * @param body
     * @return
     */
    private Object getSuccessReulst(String body){
        Object obj = null;
        try {
            if (body.startsWith(UTF8_BOM)) {
                body = body.substring(1);
            }
            if (body.startsWith("[")) {
                obj = new JSONArray(body);
            }
            else if (body.startsWith("{")) {
                obj = new JSONObject(body);
            }
            else if (body.startsWith("<")) {
                obj = body;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            obj = new Error(e.getMessage());
        }
        return obj;
    }

    /**
     * 参数拼接
     * @param params
     * @return
     */
    private String getParamsStr(HashMap<String, Object> params){
        String paramStr = "";
        for (String key : params.keySet()) {
            if(key.equalsIgnoreCase("TaskType")
                    || key.equalsIgnoreCase("TaskMethod")
                    || key.equalsIgnoreCase("RequestType")){
                continue;
            }
            Object objectValue = params.get(key);

            if (objectValue instanceof String) {
                String paramValue = (String) objectValue;
                try {
                    paramValue = URLEncoder.encode(paramValue, "utf-8");
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                paramStr += String.format("%s=%s&", key, paramValue);
            }
            else {
                paramStr += String.format("%s=%s&", key, params.get(key));
            }
        }
        System.out.println("==**==ParamsStr== " + paramStr);
        return paramStr;
    }
}
