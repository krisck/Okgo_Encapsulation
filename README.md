# NetFrameWork_Okgo
记录一下自己习惯使用的网络请求模式，用okgo封装了一下

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
```

**直接在基类中实现该接口**
```
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

**为方便测试，本地新建一个服务**
```
@RestController
public class DataController {

    //test okgo demo
    @RequestMapping(value = "api/getVersion")
    public ResultEntity getMethod() {
        ResultEntity result = new ResultEntity();
        result.setStatusCode(200);
        result.setMessage("get == 版本获取成功");
        return result;
    }

    @RequestMapping(value = "api/homepage")
    public ResultEntity postMethod(int PageNo, int PageSize) {
        ResultEntity result = new ResultEntity();
        result.setStatusCode(200);
        result.setMessage("post homepage == 获取成功");
        return result;
    }

    @RequestMapping(value = "api/desc")
    public ResultEntity postJsonMethod(@RequestBody TestOkgoEntity entity) {
        ResultEntity result = new ResultEntity();
        if (entity == null) {
            result.setStatusCode(10001);
            result.setMessage("参数错误");
            return result;
        }
        result.setStatusCode(200);
        result.setMessage("postJson desc == 获取成功");
        return result;
    }

}
```
