package com.example.mrdr.ui.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoUploadActivity extends AppCompatActivity {
    private AppConfig appConfig;
    Button selectbtn,uploadbtn;
    ImageView profilepic;
    Context context;
    String username1;
    Bitmap bitmap;
    String encodedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_upload);
        selectbtn = findViewById(R.id.selectbtn);
        uploadbtn = findViewById(R.id.uploadbtn);
        profilepic = findViewById(R.id.profilepic);
        context = PhotoUploadActivity.this;
        appConfig = new AppConfig(context);
        statusbarcolor();
        SharedPreferences sp = context.getSharedPreferences("appData2",0);
        username1 = sp.getString("user","");

        imageaccessing();

        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dexter.withActivity(PhotoUploadActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {

                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performImageUpload(username1,encodedImage);
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.code() == 200) {
                            if (response.body().getStatus().equals("ok")) {
                                if (response.body().getResultCode() == 1) {
                                    Toast.makeText(PhotoUploadActivity.this, "Image Uploaded Successfuly....!", Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                    finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profilepic.setImageBitmap(bitmap);

                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);


    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }

    private void imageaccessing(){

        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performImageAccess(username1);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            String name = response.body().getImage();
                            SharedPreferences pimage = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = pimage.edit();
                            editor.putString("profileimage",name);
                            editor.commit();
                            System.out.println("The image name is :"+name);
                            String url = "https://000webhostsetting.000webhostapp.com/logintest/Images/"+name;
                            Glide.with(getApplicationContext())
                                    .load(url).centerCrop()
                                    .placeholder(R.mipmap.ic_launcher_round)
                                    .into(profilepic);

                            System.out.println("The Image is"+name);

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