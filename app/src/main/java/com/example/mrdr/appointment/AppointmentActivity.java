package com.example.mrdr.appointment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mrdr.R;
import com.example.mrdr.activities.RegisterActivity;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.databinding.ActivityMainBinding;
import com.example.mrdr.databinding.ActivityRegisterBinding;
import com.example.mrdr.models.ApiResponse;
import com.example.mrdr.retrofitutil.ApiClient;
import com.example.mrdr.retrofitutil.ApiInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.sql.Time;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ActivityRegisterBinding registerBinding;
    public static final int CAMERA_PERMISSION = 100;
    public  final static int QRCodeWidth = 500;
    private AppConfig appConfig;
    private String username1,age,place;
    Bitmap bitmap;
    private Button download;
    private EditText name1,place1;
    private EditText mDateDisplay,mTimeDisplay;
    private Button mPickDate;
    private Button generator;
    private ImageView imageview;
    int day, month, year, hour, minute;
    int myday, myMonth, myYear, myHour, myMinute;
    Bitmap code;
    String date;
    String time;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_appointment);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_appointment);
        statusbarcolor();
        download = findViewById(R.id.download);
        name1 = findViewById(R.id.name1);
        place1 = findViewById(R.id.place1);
        mDateDisplay = findViewById(R.id.date1);
        mTimeDisplay = findViewById(R.id.time1);
        mPickDate = findViewById(R.id.datepicker1);
        generator = findViewById(R.id.generator);
        imageview = findViewById(R.id.imageview1);

        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this, AppointmentActivity.this,year, month,day);
                datePickerDialog.show();
            }
        });


        appConfig = new AppConfig(this);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("appData2",0);
        username1 = sp.getString("user","");
        String name = appConfig.getNameofUser();
        age = appConfig.getAgeofUser();
        place = appConfig.getPlaceofUser();

        System.out.println("Username is"+place);
        download.setVisibility(View.INVISIBLE);
        name1.setText(name);
        place1.setText(place);

            generator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION);

                    if (mDateDisplay.getText().toString().isEmpty()){

                        Toast.makeText(AppointmentActivity.this, "Plese Select Date,Time", Toast.LENGTH_SHORT).show();

                    }
                    else {

                        if (username1.toString().trim().length() == 0) {
                            Toast.makeText(AppointmentActivity.this, "Enter Text..", Toast.LENGTH_SHORT).show();
                        } else {
                            qrcodeupload();
                            try {
                                bitmap = textToImageEncode(username1.toString());
                                imageview.setImageBitmap(bitmap);

                                code = bitmap;
                                download.setVisibility(View.VISIBLE);
                                download.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner", null);
                                        Toast.makeText(AppointmentActivity.this, "Saved To Gallery..!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                }
            });

    }
    public void checkPermission(String permission,int requestCode){
        if(ContextCompat.checkSelfPermission(AppointmentActivity.this,permission)== PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AppointmentActivity.this,new String[]{permission},requestCode);
        }
        else
        {
            Toast.makeText(this, "Permission Already Granded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granded", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap textToImageEncode(String value) throws WriterException{
        BitMatrix bitmatrix;
        try {
            bitmatrix = new MultiFormatWriter().encode(value, BarcodeFormat.DATA_MATRIX.QR_CODE,QRCodeWidth,QRCodeWidth,null);
        }catch (IllegalArgumentException e){
            return null;
        }
        int bitMatrixWidth = bitmatrix.getWidth();
        int bitMatrixHeight = bitmatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for(int y=0; y<bitMatrixHeight; y++){
            int offSet = y*bitMatrixWidth;
            for(int x = 0; x<bitMatrixWidth;x++){
                pixels[offSet + x] = bitmatrix.get(x,y)? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth,bitMatrixHeight,Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels,0,500,0,0,bitMatrixWidth,bitMatrixHeight);
        return bitmap;
    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_500));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        myYear = year;
        myday = day;
        myMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this, AppointmentActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        myHour = hourOfDay;
        myMinute = minute;

        String AM_PM ;
        if(hourOfDay < 12) {
            AM_PM = "AM";

        } else {
            AM_PM = "PM";
            myHour=myHour-12;
        }
        mTimeDisplay.setText(myHour+":"+myMinute+" "+AM_PM);

        StringBuilder times = new StringBuilder().append(myHour).append(":").append(myMinute).append(":").append(00);
        time = times.toString();

        mDateDisplay.setText(new StringBuilder() .append(myday).append("/").append(myMonth+1).append("/").append(myYear));

        StringBuilder dates = new StringBuilder().append(myYear).append("-").append(myMonth+1).append("-").append(myday);
        date = dates.toString();
//
//        System.out.println("Date "+date);
//        System.out.println("time "+time);
//        System.out.println("usrname "+username1);
//        System.out.println("place "+place);
//        System.out.println("age "+age);

//        mDateDisplay.setText("Year: " + myYear + "\n" +
//                "Month: " + myMonth + "\n" +
//                "Day: " + myday + "\n" +
//                "Hour: " + myHour + "\n" +
//                "Minute: " + myMinute);
    }


    private void qrcodeupload(){

        Call<ApiResponse> call = ApiClient.getApiClient().create(ApiInterface.class).performQrcodeUpload(username1,date,time,age,place);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getResultCode() == 1) {
                            Toast.makeText(AppointmentActivity.this, "Registration success : Now You can Login", Toast.LENGTH_LONG).show();
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

    private void displayUserInfo(String message){
        Snackbar.make(registerBinding.myCoordinatorLayout,message,Snackbar.LENGTH_SHORT).show();
        registerBinding.txtUserPassword.setText("");
        registerBinding.showProgress.setVisibility(View.INVISIBLE);
    }

}