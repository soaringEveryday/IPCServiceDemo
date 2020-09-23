package com.taobao.ipcservicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taobao.IPCServiceDemo.lib.Api;
import com.taobao.IPCServiceDemo.lib.Config;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        String result = Api.getInstance().getCurrentTime("main");
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    }

    public void init(View view) {
        Api.getInstance().init(getApplicationContext(), new Config(String.valueOf(System.nanoTime() / 1000)));
    }
}