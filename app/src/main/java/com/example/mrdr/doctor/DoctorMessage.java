package com.example.mrdr.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrdr.R;
import com.example.mrdr.databinding.ActivityRegisterBinding;

public class DoctorMessage extends AppCompatActivity {
    private ActivityRegisterBinding registerBinding;
    Button send;
    EditText phone,message;
    private String message1;
    String phonenumber;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_doctor_message);
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_message);
        statusbarcolor();
        send = findViewById(R.id.send);
        phone = findViewById(R.id.pho);
        message = findViewById(R.id.msg);
        smsManager=SmsManager.getDefault();
        message1 = message.getText().toString();

        Intent intent1 = getIntent();
        phonenumber = intent1.getStringExtra("phoneno");
        phone.setText(phonenumber);
        String phone = "+91".concat(phonenumber);
        System.out.println("the number is"+phone);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsManager.sendTextMessage(phone, null, message1, null, null);
                Toast.makeText(DoctorMessage.this,"Send Successfully...!",Toast.LENGTH_SHORT).show();
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