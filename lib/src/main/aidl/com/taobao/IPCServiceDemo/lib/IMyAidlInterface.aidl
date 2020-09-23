// IMyAidlInterface.aidl
package com.taobao.IPCServiceDemo.lib;

import com.taobao.IPCServiceDemo.lib.Config;

// Declare any non-default types here with import statements

interface IMyAidlInterface {
    void init(in Config config);
    String getCurrentTime(String from);
}
