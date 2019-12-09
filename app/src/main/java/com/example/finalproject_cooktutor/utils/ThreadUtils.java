package com.example.finalproject_cooktutor.utils;

import android.os.Handler;

public class ThreadUtils {

	public static void runInThread(Runnable task) {
		new Thread(task).start();
	}


	public static Handler mHandler = new Handler();


	public static void runInUIThread(Runnable task) {
		mHandler.post(task);
	}
}