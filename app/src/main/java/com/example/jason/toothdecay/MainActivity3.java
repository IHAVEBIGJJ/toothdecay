package com.example.jason.toothdecay;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    private Button close;
    private Button callLin;
    private Button googlemap;
    private Button problem;
    private Button aboutme;
    private TextView result;
    private TextView solution;
    private Runnable runnable;
    private static final int msgKey1 = 1;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        close = (Button) findViewById(R.id.button6);
        callLin = (Button) findViewById(R.id.button7);
        googlemap = (Button) findViewById(R.id.button10);
        problem = (Button)findViewById(R.id.button16);
        aboutme = (Button)findViewById(R.id.button15);
        result = (TextView) findViewById(R.id.textView3);
        solution = (TextView) findViewById(R.id.textView6);
        solution.setMovementMethod(ScrollingMovementMethod.getInstance());

        googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google = new Intent();
                google.setClass(MainActivity3.this, MapsActivity.class);
                startActivity(google);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        });
        callLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Phone = new Intent();
                Phone.setClass(MainActivity3.this,Phone_book.class);
                startActivity(Phone);
                //dialPhoneNumber("0988358357");
            }
        });
        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moreproblem = new Intent();
                moreproblem.setClass(MainActivity3.this,toothproblem.class);
                startActivity(moreproblem);
            }
        });
        aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent me = new Intent();
                me.setClass(MainActivity3.this,about_me.class);
                startActivity(me);
            }
        });


        Bundle bundle = getIntent().getExtras();
        final String msg = bundle.getString("有發現蛀牙呢,需要幫你叫醫生嗎?");
        result.setText("所偵測到的病症:" + msg);

        if(msg.toString().matches("無異狀"))
        {
            solution.setText(" 無異狀: 沒有發現神麼大問題呢!還請你要多多保持口腔衛生喔!~~");
        }

        if(msg.toString().matches("齲齒"))
        {
            solution.setText(
                    "系統建議:應立即與主治牙醫師商量並進行治療\n\n\n" +
                    "當齲齒發生疼痛時能應急方法:\n"+
                    "1、冰塊：\n" +
                    "冰塊可以緩解牙痛，但不能將冰塊直接觸到牙齦。可把冰袋放在牙痛那一邊的臉上，會快速緩解疼痛。\n" +
                    "2、香草精（Vanilla Extract）\n" +
                    "香草含有一點酒精，可以麻痺牙齦腫痛。眾所周知，香草的氣味對嬰幼兒和成人來說還可以產生鎮靜作用。\n" + "3、牛奶\n" +
                    "顯然，喝冷牛奶是一種治療口腔疼痛的一種方式。許多人利用一杯冷牛奶減輕齲齒造成的痛苦，可以撐到去看牙醫的時候。\n" + "\n" +"\n"
            );
        }

        if(msg.toString().matches("牙齦發炎"))
        {
            solution.setText(
                    "系統建議:即早向牙醫師提出發炎狀況並持續追蹤\n\n\n" +"牙齦發炎的狀況通常是牙周病的前兆,如有發顯有牙齦出血,紅腫疼痛等等症狀立即與認識的牙醫師尋求就診\n" +
                    "牙齦炎的自療方法:\n" +
                       "1、蜜汁含漱法：\n可用10%的蜜汁含漱，能消炎、止痛、促進細胞再生。\n" +
                       "2、蜂蜜療法：\n將口腔洗漱乾淨，再用消毒棉簽將蜂蜜塗於潰瘍面上，塗擦後暫不要飲食。15分鐘左右，可用蜂蜜連口水一起咽下，再繼續塗擦，一天可重複塗擦數遍。\n" +
                       "3、木耳療法：\n" +
                       "取白木耳、黑木耳、山楂各10克，水煎，喝湯吃木耳，每日1-2次，可治口腔潰瘍。\n" +
                        "4、可可療法：將可可粉和蜂蜜調成糊狀，頻頻含咽，每日數次可治口腔發炎及潰瘍。\n" +
                       "\n" +
                        "5、白菜根療法：取白菜根60克，蒜苗15克，大棗10個，水煎服，每日12次，可治口腔潰瘍。\n" +
                       "\n" +
                        "6、菜籽療法：取白蘿蔔籽30克、芥菜籽30克、蔥白15克，放在一起搗爛，貼於足心，每日1次，可治口腔潰瘍。\n" +
                       "\n" +
                        "7、蘋果療法：取1個蘋果(梨也可以)削成片放至容器內，加入冷水(沒過要煮的蘋果或梨)加熱至沸，待其稍涼後同酒一起含在口中片刻再食用，連用幾天即可治癒。\n");
        }
        if (msg.toString().matches("牙齒健康"))
        {
            solution.setText("恭喜!你有一口令人稱羨的潔白牙齒呢!記得定期幫牙齒做健康檢查喔!");
        }
        if(msg.toString().matches("健康牙齦"))
        {
            solution.setText("只需三個簡單步驟，就可維持牙齦和牙齒清潔，並幫助預防牙周問題。\n\n\n"+"第 1 步 - 每天使用含重碳酸鈉低研磨配方的牙膏刷牙兩次\n\n\n"
                    +"每天使用小刷頭且具有軟質圓頭刷毛的手動或電動牙刷刷牙兩次，每次至少兩分鐘。使用含重碳酸鈉低研磨配方的牙膏，可實際移除沿著牙齦線堆積的牙菌斑，有助於牙齦和牙齒之間保持密合，配合正確刷牙習慣，有助於減少牙齦流血之發生率。\n\n\n"
                    +"第 2 步 - 使用牙線去除牙縫的牙菌斑\n\n\n"+"使用牙線或牙間刷，去除牙縫難以觸及部位的牙菌斑。將牙線用於牙縫時動作要輕柔，否則會傷害牙齦。\n\n\n"
                    +"第 3 步 - 定期由牙醫師檢查，去除堆積的牙菌斑並幫助預防牙周問題\n\n\n"+"由牙醫師定期檢查，因為專業人士可在您發生任何症狀前，協助找出牙齦問題。牙醫師也可為您清潔、洗牙及拋光，去除頑固的牙菌斑堆積 (牙結石) 並協幫助預防牙周問題。");
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
        }
    }
}



