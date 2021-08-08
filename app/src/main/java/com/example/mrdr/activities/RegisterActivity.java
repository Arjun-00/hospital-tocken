package com.example.mrdr.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mrdr.R;
import com.example.mrdr.databinding.ActivityRegisterBinding;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    String emailPattern= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        statusbarcolor();
        setSupportActionBar(registerBinding.myToolbar);
        getSupportActionBar().setTitle("Mr.DR");

        registerBinding.bnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerBinding.txtUserName.getText().toString().matches(emailPattern)) {
                    performSignUp();
                    registerBinding.showProgress.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Please Enter a Valid E-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void performSignUp() {
        String username = registerBinding.txtUserName.getText().toString();
        String password = registerBinding.txtUserPassword.getText().toString();
        String name = registerBinding.txtName.getText().toString();
        String age = registerBinding.txtAge.getText().toString();
        String place = registerBinding.txtPlace.getText().toString();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || age.isEmpty() || place.isEmpty()) {
            displayUserInfo("Plese Enter Register Information....!");

        } else {

            Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUserSignIn(username, password, name, age, place);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.code() == 200) {
                        if (response.body().getStatus().equals("ok")) {
                            if (response.body().getResultCode() == 1) {
                                Toast.makeText(RegisterActivity.this, "Registration success : Now You can Login", Toast.LENGTH_LONG).show();
                                onBackPressed();
                                finish();
                            } else {
                                displayUserInfo("User already existe...");
                                registerBinding.txtUserPassword.setText("");
                            }
                        } else {
                            displayUserInfo("Something went wrong...");
                            registerBinding.txtUserPassword.setText("");
                        }

                    } else {
                        displayUserInfo("Something went wrong...");
                        registerBinding.txtUserPassword.setText("");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });


        }
    }

    private void displayUserInfo(String message){
        Snackbar.make(registerBinding.myCoordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
        registerBinding.txtUserPassword.setText("");
        registerBinding.showProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }
}