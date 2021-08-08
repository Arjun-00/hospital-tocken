package com.example.mrdr.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrdr.payment.PaymentActivity;
import com.example.mrdr.map.MapsActivity;
import com.example.mrdr.appointment.AppointmentActivity;
import com.example.mrdr.R;
import com.example.mrdr.apputil.AppConfig;
import com.example.mrdr.doctor.DoctorActivity;
import com.example.mrdr.help.HelpActivity;
import com.example.mrdr.hospitalweb.HospitalwebActivity;
import com.example.mrdr.logout.LogoutActivity;
import com.example.mrdr.medicines.MedicinesActivity;
import com.example.mrdr.ui.home.HomeFragment;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private AppConfig appConfig;
    List<String> titles;
    List<Integer> images;
   // Context context;
    LayoutInflater inflater;
    public Adapter( HomeFragment homeFragment, List<String> titles, List<Integer> images){
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(homeFragment.getContext());

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_grid_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(titles.get(position));
        holder.gridIcon.setImageResource(images.get(position));


    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView gridIcon;
        HomeFragment homeFragment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Context context = itemView.getContext();
            title = itemView.findViewById(R.id.text1);
            gridIcon = itemView.findViewById(R.id.image1);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   final Intent intent;
                  switch (getAdapterPosition()){
                      case 0:
                          intent = new Intent(context,AppointmentActivity.class);
                          context.startActivity(intent);
                          break;
                      case 1:
                          intent = new Intent(context, MedicinesActivity.class);
                          context.startActivity(intent);
                          break;
                      case 2:
                          intent = new Intent(context, DoctorActivity.class);
                          context.startActivity(intent);
                          break;
                      case 3:
                          intent = new Intent(context, HospitalwebActivity.class);
                          context.startActivity(intent);
                          break;
                      case 4:
                          intent = new Intent(context, MapsActivity.class);
                          context.startActivity(intent);
                          break;
                      case 5:
                          intent = new Intent(context, PaymentActivity.class);
                          context.startActivity(intent);
                          break;
                      case 6:
                          intent = new Intent(context, HelpActivity.class);
                          context.startActivity(intent);
                          break;
                      case 7:
                          intent = new Intent(context, LogoutActivity.class);
                          context.startActivity(intent);
                          break;

                      default:
                          Toast.makeText(context, "Click On GridView..!", Toast.LENGTH_SHORT).show();
                  }

               }
           });
        }
    }
}
