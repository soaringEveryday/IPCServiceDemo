package com.taobao.IPCServiceDemo.lib;

import android.content.Context;
import android.os.RemoteException;

public class IMyAidlStubImpl extends IMyAidlInterface.Stub{

    private Context context;

    public IMyAidlStubImpl(Context context) {
        this.context = context;
    }

    @Override
    public void init(Config config) throws RemoteException {
        Internal.getInstance().init(context, config);
    }

    @Override
    public String getCurrentTime(String from) throws RemoteException {
        return Internal.getInstance().getCurrentTime(from);
    }
}
