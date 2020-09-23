package com.taobao.IPCServiceDemo.lib;

import android.os.Parcel;
import android.os.Parcelable;

public class Config implements Parcelable {
    String current;

    public Config(String current) {
        this.current = current;
    }

    protected Config(Parcel in) {
        current = in.readString();
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel in) {
            return new Config(in);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(current);
    }
}
