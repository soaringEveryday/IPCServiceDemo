package com.taobao.IPCServiceDemo.lib;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiImpl extends Api{

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Context context = null;

    private static final String TAG = "ApiImpl";
    static ApiImpl instance = new ApiImpl();

    public ApiImpl() {
    }

    @Override
    public void init(final Context context, final Config config) {
        this.context = context;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                checkAndBindRemoteService(context, true);
                if (null != getRemoteService()) {
                    try {
                        getRemoteService().init(config);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public String getCurrentTime(String from) {
        String result = "";
        checkAndBindRemoteService(context, false);
        if (null != getRemoteService()) {
            try {
                result = getRemoteService().getCurrentTime(from);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "remote service died");
        }
        return result;
    }
}
