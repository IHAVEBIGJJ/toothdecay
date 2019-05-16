package com.example.jason.toothdecay;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity1 extends AppCompatActivity {
    private Button back;
    private Button next;
    private Button connection;
    private Button disable;
    private TextView wificonnect;
    private WifiManager wifi;
    private WifiInfo Info;




    int size = 0;
    private SimpleAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        back = (Button) findViewById(R.id.button12);
        next = (Button) findViewById(R.id.button11);
        connection = (Button) findViewById(R.id.button13);
        disable = (Button) findViewById(R.id.button14);
        wifi = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


        //探測網路是否開啟
        if (wifi.isWifiEnabled() == false) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("注意!");
            dialog.setMessage("你的Wifi還沒開啟,是否要開起呢?");
            dialog.setCancelable(false);
            dialog.setPositiveButton("開啟WIFI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    wifi.setWifiEnabled(true);
                    Toast.makeText(getApplicationContext(), "WIFI是關閉的..現在正在開啟中...", Toast.LENGTH_LONG).show();
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        }

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent camera = new Intent();
                camera.setComponent(new ComponentName("com.example.jason.wificamera","com.example.jason.wificamera.MainActivity"));
                startActivity(camera);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back1 = new Intent();
                back1.setClass(MainActivity1.this, MainActivity.class);
                startActivity(back1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gonext = new Intent();
                gonext.setClass(MainActivity1.this, MainActivity2.class);
                startActivity(gonext);
            }
        });
        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifi.isWifiEnabled()) {
                    wifi.setWifiEnabled(false);
                }
            }
        });
    }
}


