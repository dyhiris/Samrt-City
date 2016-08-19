package com.example.aaronhu.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private DrawCG mDrawCG;
    private ImageView imgView;
    private int car=1;
    private int you=9;
    Firebase mApply;


    public void init(){
        // 获取手机窗口的大小
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();


        imgView = (ImageView) findViewById(R.id.imgView);
        mDrawCG = new DrawCG(MainActivity.this, screenWidth,
                screenHeight);
        HandleAllBlocks h = new HandleAllBlocks();

        h.setCar(car-1);
        h.setYou(you - 1);

        TextView carT = (TextView)findViewById(R.id.textView);
        carT.setText("Car: 0" + car);
        TextView youT = (TextView)findViewById(R.id.textView2);
        youT.setText("You: 0" + you);

        imgView.setImageBitmap(mDrawCG.drawRect(h));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApply = new Firebase("https://smart-city-3041c.firebaseio.com/applys");
        init();
    }

    // 按钮事件
    public void doCreateLine(View view) {
        imgView.setImageBitmap(mDrawCG.drawLine(car, you));
    }

    public void doCreateRect(View view) {

        Intent intent = new Intent(MainActivity.this, WifiActivity.class);
        startActivity(intent);

    }

    public void doUseLocker(View view){
        Intent intent = new Intent(MainActivity.this, ApplyLockerActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onStart() {
        super.onStart();
        mApply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);
                //Toast.makeText(MainActivity.this, s[1], Toast.LENGTH_SHORT).show();
                car = Integer.parseInt(s[1]);
                init();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }
}
