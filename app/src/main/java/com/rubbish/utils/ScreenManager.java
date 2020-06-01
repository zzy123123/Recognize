package com.rubbish.utils;

import android.app.Activity;

import java.util.Stack;

public class ScreenManager {
	private static Stack<Activity> activityStack;
	private static ScreenManager instance;

	private ScreenManager() {
	}

	public static ScreenManager getScreenManager() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}

	// 弹出最后一个Activity
	public void popActivity() {
		Activity activity = activityStack.lastElement();
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 弹出指定Activity
	public void popActivity(Activity activity) {
		try {
			if (activity != null) {
				activity.finish();
				activityStack.remove(activity);
				activity = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 弹出指定Activity
	public void popActivity(Class cls) {
		Activity activity = currentActivity();
		if (activity != null && activity.getClass().equals(cls)) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 获取最后一个Activity
	public Activity currentActivity() {
		Activity activity = null;
		if(activityStack.size()>0){
			activity = activityStack.lastElement();
		}
		return activity;
	}

	// 往Stack添加Activity
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 除了制定Activity以外，弹出所有activity
	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				activityStack.pop();
				continue;
			}
			popActivity(activity);
		}
	}

	public void popAllActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}
	
	// activity堆栈是否含有指定activity
	public boolean containsActivity(Class cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				return true;
			}
		}
		return false;
	}
}