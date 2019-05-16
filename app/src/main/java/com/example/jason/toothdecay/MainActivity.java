package com.example.jason.toothdecay;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private Button next;
private Button exit;
private ImageView pagepicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        next = (Button)findViewById(R.id.button);
        exit = (Button) findViewById(R.id.button2);
        pagepicture = (ImageView)findViewById(R.id.imageView2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Nextpage = new Intent();
                Nextpage.setClass(MainActivity.this, MainActivity1.class);
                startActivity(Nextpage);

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             System.exit(0);
            }
        });

    }
}
