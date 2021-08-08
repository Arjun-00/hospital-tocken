package com.example.mrdr.hospitalweb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.mrdr.R;
import com.example.mrdr.databinding.ActivityRegisterBinding;

public class HospitalwebActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;

    private WebView mywebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_hospitalweb);

        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_hospitalweb);
        statusbarcolor();
        mywebview = (WebView) findViewById(R.id.webView);
        mywebview.loadUrl("https://kasargod.nic.in/public-utility-category/hospitals/");
    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }
    }

}