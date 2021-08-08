package com.example.mrdr.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mrdr.R;
import com.example.mrdr.databinding.ActivityRegisterBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private ActivityRegisterBinding registerBinding;
    RecyclerView recyclerView;
    List<Doctorclass> doctors;
    private static String JSON_URL ="https://000webhostsetting.000webhostapp.com/logintest/doctorfetch.php";
    Adapter2 adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_doctor);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_doctor);
        statusbarcolor();
        recyclerView = findViewById(R.id.recycler);
        doctors = new ArrayList<>();
        extractdoctorinfo();

    }

    private void statusbarcolor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200,this.getTheme()));
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.red_200));
        }
    }
    private void extractdoctorinfo(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

//                        Medicineclass medicineclass = new Medicineclass();
//                        medicineclass.setMdname(jsonObject.getString("mdname").toString());
//                        medicineclass.setPrice(jsonObject.getString("price").toString());
//                        medicineclass.setCompany(jsonObject.getString("company").toString());

//                        medicines.add(medicineclass);
                        doctors.add(new Doctorclass(
                                jsonObject.getString("dname"),
                                jsonObject.getString("dspecific"),
                                jsonObject.getString("dphone")
                        ));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(DoctorActivity.this));
                adapter2 = new Adapter2(DoctorActivity.this,doctors);
                recyclerView.setAdapter(adapter2);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: ");
            }
        });
        queue.add(jsonArrayRequest);
    }

}