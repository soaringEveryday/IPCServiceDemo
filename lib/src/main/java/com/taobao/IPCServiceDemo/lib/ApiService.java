package com.taobao.IPCServiceDemo.lib;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ApiService extends Service {

    private static final String TAG = "ApiService";
    private IMyAidlInterface.Stub binder = null;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        if (binder == null) {
            binder = new IMyAidlStubImpl(this);
        }
        return binder;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }
}
