package com.example.mrdr.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private AppConfig appConfig;
    TextView username;
    EditText password,age,place,name;
    Button update;
    Context context;
    String username1,age1,place1,name1;
    Button addphoto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        username = root.findViewById(R.id.username2);
        password = root.findViewById(R.id.password2);
        age = root.findViewById(R.id.age2);
        place = root.findViewById(R.id.place2);
        name = root.findViewById(R.id.name2);
        update = root.findViewById(R.id.update);

        addphoto = root.findViewById(R.id.addphoto);
        context = getContext();

        appConfig = new AppConfig(context);
        SharedPreferences sp = context.getSharedPreferences("appData2",0);
        username1 = sp.getString("user","");
        name1 = appConfig.getNameofUser();
        age1 = appConfig.getAgeofUser();
        place1 = appConfig.getPlaceofUser();
        System.out.println("The user age is :"+age1);
        System.out.println("The user place is :"+place1);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        name1 = appConfig.getNameofUser();
        age1 = appConfig.getAgeofUser();
        place1 = appConfig.getPlaceofUser();
        username.setText(username1);
        name.setText(name1);
        age.setText(age1);
        place.setText(place1);
        Context context = getContext();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upname = name.getText().toString();
                String upplace = place.getText().toString();
                String upage = age.getText().toString();
                String uppassword = password.getText().toString();

                if (uppassword.isEmpty())
                {
                    Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUpdateNotPassword(username1,upname,upage,upplace);
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.code() == 200) {
                                if (response.body().getStatus().equals("ok")) {
                                    if (response.body().getResultCode() == 1) {
                                        Toast.makeText(context, "Updated Successfuly...!", Toast.LENGTH_LONG).show();
                                    } else {
                                    }
                                } else {

                                }

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {

                        }
                    });



                }
                else
                {
                    Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performUpdatePassword(username1,upname,upage,upplace,uppassword);
                    call.enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.code() == 200) {
                                if (response.body().getStatus().equals("ok")) {
                                    if (response.body().getResultCode() == 1) {
                                        Toast.makeText(context, "Updated Successfuly...!", Toast.LENGTH_LONG).show();
                                    } else {

                                    }
                                } else {

                                }

                            } else {

                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {

                        }
                    });


                }


            }
        });

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoUploadActivity.class);
                startActivity(intent);
            }
        });
    }


}