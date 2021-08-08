package com.example.mrdr.payment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    private AppBarConfiguration mAppBarConfiguration;
    private AppConfig appConfig;
    Button btn_pay;
    Context context;
    String username1,place1;
    String amt,pho;
    EditText amount,phonenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amount = findViewById(R.id.amount);
        phonenumber = findViewById(R.id.Phonenumber);

        context = PaymentActivity.this;
        btn_pay = findViewById(R.id.btn_pay);
        statusbarcolor();
        appConfig = new AppConfig(context);
        appConfig = new AppConfig(context);
        SharedPreferences sp = context.getSharedPreferences("appData2",0);
        username1 = sp.getString("user","");
        place1 = appConfig.getPlaceofUser();
        amt = amount.getText().toString();
        pho = phonenumber.getText().toString();

        if (amt.isEmpty() && pho.isEmpty()) {

            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makepayment();

                }
            });
        }
        else {
            Toast.makeText(context, "PLS Fill Both Amount & PhoneNumber ..!", Toast.LENGTH_SHORT).show();
        }

    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }


    public void makepayment()
    {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_SV41HRtDVVc6Ga");
        checkout.setImage(R.mipmap.ic_launcher_round);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "NHC Narikkuni");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            int am= Integer.parseInt(amt);
            int am1 = am *100;
            options.put("amount",am1);//pass amount in currency subunits
            options.put("prefill.email",username1);
            options.put("prefill.contact",pho);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s)
    {
        finish();
    }


    @Override
    public void onPaymentError(int i, String s)
    {
//        AlertDialog.Builder builder=new AlertDialog.Builder(PaymentActivity.this);
//        builder.setMessage("PAYMENT FAILED...!"+s);
//        builder.setTitle("Alert !");
//        builder.setCancelable(true);
//        AlertDialog alertDialog=builder.create();
//        alertDialog.show();
//
//        finish();
    }


}