package com.example.mrdr.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.databinding.ActivityMainBinding;
import com.example.mrdr.databinding.ActivityMainBindingImpl;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private boolean isRememberUserLogin = false;
    private AppConfig appConfig;

    private Context mContext;
    private Activity mActivity;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        statusbarcolor();

        mContext = getApplicationContext();
        mActivity = MainActivity.this;

        setSupportActionBar(mainBinding.myToolbar);
        getSupportActionBar().setTitle("Mr.DR");
        appConfig = new AppConfig(this);
        if(appConfig.isUserLogin())
        {
            String user = mainBinding.txtUserName.getText().toString();
            String pass = mainBinding.txtUserPassword.getText().toString();
            String name = appConfig.getNameofUser();
            String age = appConfig.getAgeofUser();
            String place = appConfig.getPlaceofUser();

            SharedPreferences sp = getApplicationContext().getSharedPreferences("appData2",0);
            SharedPreferences.Editor editor;
            editor = sp.edit();
            editor.putString("user",user);
            editor.commit();

            Intent intent = new Intent(MainActivity.this,User_Activity.class);
            intent.putExtra("name",name);
            intent.putExtra("user",user);
            intent.putExtra("pass",pass);
            intent.putExtra("age",age);
            intent.putExtra("place",place);
            startActivity(intent);
            finish();
        }

        mainBinding.bnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        mainBinding.bnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mainBinding.txtUserName.getText().toString().matches(emailPattern)) {
                    performLogin();
                    mainBinding.showProgress.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(mContext, "Please Enter Valid UserName...!", Toast.LENGTH_SHORT).show();
                }

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    checkPermission();
                }

            }
        });

    }


    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(mActivity,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.CALL_PHONE)
                + ContextCompat.checkSelfPermission(
                mActivity,Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.CALL_PHONE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    mActivity,Manifest.permission.SEND_SMS)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setMessage("Camera, Call_Phone,Send_SMS" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                mActivity,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.CALL_PHONE,
                                        Manifest.permission.SEND_SMS
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        mActivity,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.SEND_SMS
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            Toast.makeText(mContext,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ) {
                    // Permissions are granted
                    Toast.makeText(mContext, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(mContext, "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    private void performLogin() {
        String userName = mainBinding.txtUserName.getText().toString();
        String password = mainBinding.txtUserPassword.getText().toString();
        if (userName.isEmpty() || password.isEmpty()) {
            displayUserInformaction("Plese Enter Username & Password....!");

        } else {

            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUserLogin(userName, password);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("ok")) {
                            if (response.body().getResultCode() == 1) {
                                String name = response.body().getName();
                                String age = response.body().getAge();
                                String place = response.body().getPlace();
                                System.out.println("name is "+name);
                                System.out.println("name is "+age);
                                System.out.println("name is "+place);
                                appConfig.saveNameofUser(name,age,place);
                                if (isRememberUserLogin) {
                                    appConfig.updateUserLoginStatus(true);
                                    appConfig.saveNameofUser(name,age,place);
                                }

                                SharedPreferences sp = getApplicationContext().getSharedPreferences("appData2",0);
                                SharedPreferences.Editor editor;
                                editor = sp.edit();
                                editor.putString("user",userName);
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, User_Activity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("user",userName);
                                intent.putExtra("pass",password);
                                intent.putExtra("age",age);
                                intent.putExtra("place",place);
                                startActivity(intent);
                                finish();
                            } else {
                                displayUserInformaction("Login Faild....");
                                mainBinding.txtUserPassword.setText("");
                            }

                        } else {
                            displayUserInformaction("Something went wrong....");
                            mainBinding.txtUserPassword.setText("");
                        }
                    } else {
                        displayUserInformaction("Something went wrong....");
                        mainBinding.txtUserPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });

        }
    }

    private void displayUserInformaction(String message){
        Snackbar.make(mainBinding.myCoordinatorLayout,message,Snackbar.LENGTH_LONG).show();
        mainBinding.showProgress.setVisibility(View.INVISIBLE);
    }

    public void checkBoxClicked(View view){
        isRememberUserLogin = ((CheckBox)view).isChecked();
    }


    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }
}