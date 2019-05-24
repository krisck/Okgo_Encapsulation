# NetFrameWork_Okgo
**记录一下自己习惯使用的网络请求模式，用okgo封装了一下**

**主要使用类**
```
OkgoTask ：服务器地址，请求任务
TaskParamsManager ： 请求参数封装
TaskType ： 请求接口名枚举
```

**项目使用Okgo作为网络请求框架， 定义一个TaskListener作为请求结果回调接口**
```
/**
 * 网络访问结果回调接口
 * data callback
 * 在基类中使用，实现此接口
 */
public interface TaskListener {

	//void taskStarted(TaskType type);
	void taskFinished(TaskType type, Object result, boolean isHistory);
	//void taskIsCanceled(TaskType type);
}

//OkgoTask中使用回调
mTaskListener.taskFinished(mTaskType, result, false);

//子activity中
@Override
    public void taskFinishedCustom(TaskType type, Object result, boolean isHistory) {
        super.taskFinishedCustom(type, result, isHistory);
        if(result == null){
            return;
        }
        if(result instanceof Error){
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
    }
```

**直接在基类中实现该接口**
```
public class BaseActivity extends Activity implements TaskListener{

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
```

**在需要访问服务器的地方启动任务**
```
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
```
**添加接口和参数**
需要改动两个类 
1、com.network.TaskType 新增一个接口名定义
```
public enum TaskType {
    TaskType_Version,
    TaskType_Homepage,
    TaskType_Desc,
}
```
2、com.network.TaskParamsManager 新增
```
public HashMap<String, Object> getVersionParams(){
        HashMap<String, Object> params = new HashMap<>();
        params.put("TaskType", TaskType.TaskType_Version);
       // params.put("TaskMethod", "app/version");
        params.put("TaskMethod", "getVersion");
        params.put("RequestType", RequestType.GET);

        return params;
    }
```
