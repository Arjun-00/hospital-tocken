package com.example.mrdr.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrdr.adapter.Adapter;
import com.example.mrdr.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    Activity context;
    RecyclerView datalist;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       context = getActivity();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        datalist = (RecyclerView) context.findViewById(R.id.datalist);

        titles = new ArrayList<>();
        images = new ArrayList<>();

        titles.add("APPOINTMENTS");
        titles.add("MEDICINE");
        titles.add("DOCTOR'S");
        titles.add("HOSPITAL WEB");
        titles.add("LOCATION");
        titles.add("PAYMENTS");
        titles.add("HELP");
        titles.add("LOGOUT");

        images.add(R.drawable.ic_baseline_menu_appointments);
        images.add(R.drawable.ic_baseline_medicines);
        images.add(R.drawable.ic_baseline_local_doctor_24);
        images.add(R.drawable.ic_baseline_web_24);
        images.add(R.drawable.ic_baseline_location_on_24);
        images.add(R.drawable.ic_baseline_payment_24);
        images.add(R.drawable.ic_baseline_help_help_24);
        images.add(R.drawable.ic_baseline_logout);

        adapter = new Adapter(this,titles,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,
                2,GridLayoutManager.VERTICAL,false);
        datalist.setLayoutManager(gridLayoutManager);
        datalist.setAdapter(adapter);

    }
}