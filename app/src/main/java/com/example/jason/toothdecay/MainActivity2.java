package com.example.jason.toothdecay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.Text;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.COLOR_RGB2HSV;
import static org.opencv.imgproc.Imgproc.COLOR_RGBA2YUV_I420;
import static org.opencv.imgproc.Imgproc.CV_BLUR;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.THRESH_OTSU;
import static org.opencv.imgproc.Imgproc.resize;


public class MainActivity2 extends AppCompatActivity {
//宣告物件
private Button back;
private Button detect;
private Button detect2;
private Button next2;
private Button clean;
private Button Instructions;
private Bitmap orgbmp;
private Bitmap binarybmp;
private Bitmap cannybmp;
private Bitmap hsvbmp;
private Bitmap bm;
private TextView colorview;
private TextView colorcode;
private TextView colorshow;
private ImageView imv;

Uri imgUri;
Mat imageMat;

    private static final String TAG = "OCVSample::Activity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //物件導向設定
        back = (Button)findViewById(R.id.button3);
        detect = (Button)findViewById(R.id.button4);
        detect2 = (Button)findViewById(R.id.button9);
        next2 = (Button)findViewById(R.id.button5);
        clean = (Button)findViewById(R.id.button8);
        Instructions = (Button)findViewById(R.id.button19);
        imv = (ImageView)findViewById(R.id.imageView);
        colorview = (TextView)findViewById(R.id.textView4);
        colorcode = (TextView)findViewById(R.id.textView5);
        colorshow = (TextView)findViewById(R.id.textView7);
        final AlertDialog.Builder dialog =new AlertDialog.Builder(this);
        dialog.setTitle("使用者提示:");
        dialog.setMessage("當開始啟用口腔辨識系統時，請注意以下幾點:\n"+"1.請將攝影機鏡頭LED亮度調至最亮的程度\n"+"2. 開始拍照時，請離被拍攝的目標5到7公分在進行拍攝\n" +
                "3. 拍攝牙齒的相片時盡量拍攝咬合面或者是側面與正面，這能使系統的辨識更加準確\n");
        dialog.setNegativeButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
        //設定按鍵監聽器
        //分別是返回,偵測,下一步
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backpage = new Intent();
                backpage.setClass(MainActivity2.this,MainActivity1.class);
                startActivity(backpage);

            }
        });
        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick = new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                startActivityForResult(pick, 1);
            }
        });
        detect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick2 = new Intent(Intent.ACTION_GET_CONTENT);
                pick2.setType("image/*");
                startActivityForResult(pick2, 2);
            }
        });
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Next2 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("有發現蛀牙呢,需要幫你叫醫生嗎?",colorshow.getText().toString());
                Next2.putExtras(bundle);
                Next2.setClass(MainActivity2.this,MainActivity3.class);
                startActivity(Next2);

            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorshow.setText("無異狀");
            }
        });

        Instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });



//點擊顯示顏色編號
        imv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent  ) {
                float eventX = motionEvent.getX();
                float eventY = motionEvent.getY();
                float[] eventXY = new float[] {eventX, eventY};
                Matrix invertMatrix = new Matrix();
                ((ImageView)view).getImageMatrix().invert(invertMatrix);
                invertMatrix.mapPoints(eventXY);
                int x = Integer.valueOf((int)eventXY[0]);
                int y = Integer.valueOf((int)eventXY[1]);
                //點擊座標
                colorview.setText(
                        "touched position: "
                                + String.valueOf(eventX) + " / "
                                + String.valueOf(eventY));
                colorview.setText(
                        "touched position: "
                                + String.valueOf(x) + " / "
                                + String.valueOf(y));
                if(x < 0){
                    x = 0;
                }else if(x > binarybmp.getWidth()-1){
                    x = binarybmp.getWidth()-1;
                }
                if(y < 0){
                    y = 0;
                }else if(y > binarybmp.getHeight()-1){
                    y = binarybmp.getHeight()-1;
                }
                //定義點擊區域X,Y軸
                int touchedRGB = binarybmp.getPixel(x, y);

                if(touchedRGB == Color.rgb(0,0,0))
                {
                    colorshow.setText("齲齒");
                }else if(touchedRGB == Color.rgb(255,255,255))
                {
                    colorshow.setText("牙齒健康");
                }

                    colorcode.setText("touched color: " + "#" + Integer.toHexString(touchedRGB));

                    colorcode.setTextColor(touchedRGB);

                return true;

            }
        });

    }


//處理圖像與影像辨識區塊
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
    super.onActivityResult(requestCode,resultCode,data);
    if(resultCode == RESULT_OK && requestCode == 1)//偵測牙齦發炎
    {
        switch(requestCode)
        {
            case 1:
                imgUri = data.getData();
                try
                {
                    Mat rgb_mat = new Mat();
                    Mat HSV = new Mat();
                    Mat mDilatedMat = new Mat();
                    Mat hierarchy = new Mat();
                    Mat mask_mat1 = new Mat();
                    Mat mask_mat2 = new Mat();
                    Mat mask_mat3 = new Mat();
                    Mat remixmask = new Mat();
                    Mat remixmask2 = new Mat();
                    Scalar linecolor = new Scalar(0,255,0);//框線的顏色
                    int iLineThickness = 1;//框線的粗細
                    List<MatOfPoint> contoursList=new ArrayList<MatOfPoint>();
                    //牙齦發炎檢測區塊
                    orgbmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));//從資料庫叫出原圖
                    Utils.bitmapToMat(orgbmp,rgb_mat);
                    //                    在Ｕ8中，最大值只有255，因此OpenCV有做了調整
                    //                    Ｈ：0~180   H/2
                    //                    Ｓ：0~255   S*255
                    //                    Ｖ：0~255   V*255
                    Imgproc.cvtColor(rgb_mat,HSV,COLOR_RGB2HSV);

                    Scalar R_lowerThreshold = new Scalar(174, 170, 146);//發炎顏色(最低)
                    Scalar R_upperThreshold = new Scalar(177, 208, 180);//發炎顏色(最高)

                    Scalar T_lowerThreshold = new Scalar(177, 142, 148);//發炎顏色(最低)
                    Scalar T_upperThreshold = new Scalar(3, 217, 200);//發炎顏色(最高)

                    Scalar A_lowerThreshold = new Scalar(175,185,114);
                    Scalar A_upperThreshold = new Scalar(2,229,160);

                        Core.inRange(HSV, R_lowerThreshold, R_upperThreshold, mask_mat1);
                        Core.inRange(HSV, T_lowerThreshold,T_upperThreshold, mask_mat2);
                        Core.inRange(HSV,A_lowerThreshold,A_upperThreshold,mask_mat3);
                        Core.bitwise_or(mask_mat1,mask_mat2,remixmask);
                        Core.bitwise_or(remixmask,mask_mat3,remixmask2);
                            Imgproc.dilate(remixmask2, mDilatedMat, new Mat());

                            Imgproc.findContours(remixmask2, contoursList, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

                            if (contoursList.size() == 0)  // Minimum size allowed for consideration
                            {
                            colorshow.setText("健康牙齦");
                            }
                            else{
                                for (int contourIdx = 0; contourIdx < contoursList.size(); contourIdx++) {

                                    {
                                        Imgproc.drawContours(rgb_mat, contoursList, contourIdx, linecolor, iLineThickness);
                                        colorshow.setText("牙齦發炎");
                                    }

                                }
                            }

                    //土法煉鋼提取HSV比較
                    int width = orgbmp.getWidth();
                    int height = orgbmp.getHeight();
                    int[] inpixel=new int[width*height];
                    int[] outpixel=new int[width*height];
                    orgbmp.getPixels(inpixel, 0, width, 0, 0, width, height);
                    float[] pixelHSV = new float[3];
                    int good = 0;
                    int index = 0;
                    for(int y = 0;y<height;++y)
                    {
                        for(int x = 0;x<width;++x)
                        {
                            Color.colorToHSV(inpixel[index], pixelHSV);
                            if((pixelHSV[0] < 171.0f)&(pixelHSV[0]>151.0f)&(pixelHSV[1] >66)&(pixelHSV[1]<117)&(pixelHSV[2]>121)&(pixelHSV[2]<178))
                            {
                                outpixel[index] = Color.HSVToColor(pixelHSV);
                                index++;
                                good++;
                            }
                            if(good == 1)
                        {
                            colorshow.setText("健康牙齦");
                        }
                        }
                    }


                    bm = Bitmap.createBitmap(HSV.cols(),HSV.rows(),Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(rgb_mat, bm);
                    imv.setImageBitmap(bm);

                } catch (FileNotFoundException e) {
                    Toast.makeText(this,"沒抓到到片",Toast.LENGTH_LONG).show();
                }
        }
    }
    if(resultCode == RESULT_OK && requestCode == 2)//偵測牙齒蛀牙
    {
        switch (requestCode)
        {
            case 2:
                imgUri = data.getData();
                try {
                    Mat rgb_mat = new Mat();
                    Mat thr2 = new Mat();
                    Mat gray_mat2 = new Mat();
                    Mat erodeMat = new Mat();

                    orgbmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri));
                    Bitmap graybmp = Bitmap.createBitmap(orgbmp.getWidth(), orgbmp.getHeight(), Bitmap.Config.RGB_565);
                    Utils.bitmapToMat(orgbmp, rgb_mat);
                    Imgproc.cvtColor(rgb_mat, gray_mat2, Imgproc.COLOR_RGB2GRAY);//灰階
                    Utils.matToBitmap(gray_mat2, graybmp);//Mat轉Bitmap(灰階)
                    Imgproc.erode(gray_mat2,erodeMat,new Mat());//銳值化

                    binarybmp = Bitmap.createBitmap(orgbmp.getWidth(), orgbmp.getHeight(), Bitmap.Config.RGB_565);//二值化
                    Imgproc.threshold(erodeMat, thr2, 125,255,THRESH_OTSU);//大金二值化
                    Utils.matToBitmap(thr2, binarybmp);//Mat轉Bitmap(二值化)
                    imv.setImageBitmap(orgbmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}



//OpenCV載入顯示成功與失敗
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");
                    imageMat = new Mat();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
//載入OpenCV3_0_0
    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(  "OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
}
