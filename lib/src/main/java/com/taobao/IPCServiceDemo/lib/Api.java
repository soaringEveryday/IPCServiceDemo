package com.taobao.IPCServiceDemo.lib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Api {

    private static final String TAG = "Api";

    private volatile IMyAidlInterface remoteService = null;
    private volatile AtomicBoolean binding = new AtomicBoolean(false);
    private volatile CountDownLatch waitLock;


    public static Api getInstance() {
        return ApiImpl.instance;
    }

    public abstract void init(Context context, Config config);

    public abstract String getCurrentTime(String from);

    void checkAndBindRemoteService(Context context, boolean wait) {
        if (remoteService != null) {
            return;
        }
        bindRemoteService(context);
        if (wait) {
            if (waitLock == null) {
                waitLock = new CountDownLatch(1);
            }
            if (remoteService != null) {
                return;
            }
            try {
                waitLock.await(5, TimeUnit.SECONDS);
            } catch (Throwable throwable) {
                Log.e(TAG, "error" + throwable.getMessage());
            }
            if (remoteService == null && context != null) {
                // refill remote service by local instance
                remoteService = new IMyAidlStubImpl(context);
                Log.e(TAG, "wait timeout and refill");
            }
        }
    }

    private void bindRemoteService(Context context) {
        if (context != null && remoteService == null) {
            if (binding.compareAndSet(false, true)) {
                try {
                    Intent intent = new Intent(context, ApiService.class);
                    intent.setAction(ApiService.class.getName());
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    boolean result = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    if (result) {
                        Log.d(TAG, "bind service success");
                    }
                } catch (Throwable throwable) {
                    Log.d(TAG, "bind service failed");
                }
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            remoteService = IMyAidlInterface.Stub.asInterface(iBinder);
            if (waitLock != null) {
                waitLock.countDown();
            }
            binding.set(false);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            remoteService = null;
            if (waitLock != null) {
                waitLock.countDown();
            }
            binding.set(false);
        }
    };

    IMyAidlInterface getRemoteService() {
        return remoteService;
    }
}
