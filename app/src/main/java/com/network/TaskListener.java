package com.network;

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