package com.example.mrdr.logout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mrdr.R;
import com.example.mrdr.activities.MainActivity;
import com.example.mrdr.activities.User_Activity;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.databinding.ActivityHomeBinding;
import com.example.mrdr.databinding.ActivityMainBinding;

public class LogoutActivity extends AppCompatActivity {
    private ActivityHomeBinding homeBinding;
    private AppConfig appConfig;
    private Button logout,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_logout);
        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_logout);
        cancel = findViewById(R.id.cancel);
        logout = findViewById(R.id.logout);
        statusbarcolor();
        appConfig = new AppConfig(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoutActivity.this, User_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appConfig.updateUserLoginStatus(false);
                startActivity(new Intent(LogoutActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }
}