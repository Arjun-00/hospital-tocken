package com.example.mrdr.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.databinding.ActivityRegisterBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class User_Activity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    private AppBarConfiguration mAppBarConfiguration;
    ImageView image;
    String proimage;
    TextView textname,textusername;
    private AppConfig appConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_user_);

        textusername = findViewById(R.id.textUsername);
        appConfig = new AppConfig(this);
        statusbarcolor();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra("name");
        String user = getIntent().getStringExtra("user");
        String pass = getIntent().getStringExtra("pass");
        String age = getIntent().getStringExtra("age");
        String place = getIntent().getStringExtra("place");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        image = (ImageView)hView.findViewById(R.id.imageView);
        textname = (TextView) hView.findViewById(R.id.textName);
        textusername = (TextView) hView.findViewById(R.id.textUsername);
        SharedPreferences pimage = PreferenceManager.getDefaultSharedPreferences(this);
        proimage = pimage.getString("profileimage","");
        String url = "https://000webhostsetting.000webhostapp.com/logintest/Images/"+proimage;
        Glide.with(getApplicationContext())
                .load(url).centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .into(image);

        System.out.println("The Image is"+proimage);


        textname.setText(name);
        textusername.setText(user);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }
}