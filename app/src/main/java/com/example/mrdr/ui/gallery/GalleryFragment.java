package com.example.mrdr.ui.gallery;

import android.app.Activity;
import android.content.Context;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mrdr.R;
import com.example.mrdr.activities.RegisterActivity;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {
    private AppConfig appConfig;
    Context context;
    String username2,feedb;
    EditText feedback;
    Button feedsend;
    TextView username;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        feedback = root.findViewById(R.id.feedback);
        feedsend = root.findViewById(R.id.feedsend);
        username = root.findViewById(R.id.userna);

        context = getContext();
        appConfig = new AppConfig(context);
        SharedPreferences sp = context.getSharedPreferences("appData2",0);
        username2 = sp.getString("user","");
        username.setText(username2);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        feedsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedb = feedback.getText().toString();

                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performFeedBack(username2,feedb);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("ok")) {
                                if (response.body().getResultCode() == 1) {
                                    Toast.makeText(context, "FeedBack Send :", Toast.LENGTH_LONG).show();

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

        });

    }

}