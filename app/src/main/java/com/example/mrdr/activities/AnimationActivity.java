package com.example.mrdr.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.mrdr.R;
import com.example.mrdr.databinding.ActivityMainBinding;

public class AnimationActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    Thread time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_animation);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_animation);
        statusbarcolor();
        time = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this){
                        wait(5000);
                    }
                } catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(AnimationActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        time.start();
    }
    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.white,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
    }
}