package com.taobao.IPCServiceDemo.lib;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

public class Internal {

    private Context context;
    private String process = "";

    static class InternalHolder {
        static Internal INSTANCE = new Internal();
    }

    public static Internal getInstance() {
        return InternalHolder.INSTANCE;
    }

    public Internal() {
    }

    public void init(Context context, Config config) {
        this.context = context;
    }

    public String getCurrentTime(String from) {
        String time = String.valueOf(System.nanoTime() / 1000);
        return getProcessName(context) + "getCurrentTime " + time + ", from " + from;
    }

    private String getProcessName(Context context) {
        if (!TextUtils.isEmpty(process)) {
            return process;
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
            try {
                if (info.pid == pid) {
                    process = info.processName;
                }
            } catch (Throwable ignored) {
            }
        }
        return process;
    }

}
