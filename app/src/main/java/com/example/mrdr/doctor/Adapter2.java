package com.example.mrdr.doctor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrdr.R;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder>{
    LayoutInflater inflater;
    List<Doctorclass> doctors;
    Context ctx1;

    public Adapter2(Context ctx, List<Doctorclass> doctors){
        this.inflater = LayoutInflater.from(ctx);
        this.doctors = doctors;
        ctx1 = ctx;
    }


    @NonNull
    @Override
    public Adapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.doctor_custom_list,parent,false);
        return new Adapter2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter2.ViewHolder holder, int position) {

        holder.dname.setText(doctors.get(position).getDname());
        holder.dspecific.setText(doctors.get(position).getDspecific());
        holder.dphone.setText(doctors.get(position).getDphone());
        //holder.mdimage.setImageBitmap(medicines.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dname,dspecific,dphone;
        Button call,message;
        // ImageView mdimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dname = itemView.findViewById(R.id.dname);
            dspecific = itemView.findViewById(R.id.dspecific);
            dphone = itemView.findViewById(R.id.dphone);
            // mdname = itemView.findViewById(R.id.mdimage);
            call = itemView.findViewById(R.id.dcall);
            message = itemView.findViewById(R.id.message1);
            String phoneno1 = dphone.getText().toString();

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phoneno = dphone.getText().toString();
                    Intent intent1 = new Intent(ctx1, DoctorMessage.class);
                    intent1.putExtra("phoneno",phoneno);
                    ctx1.startActivity(intent1);
                }
            });

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = dphone.getText().toString();
                    System.out.println("phone number is"+number);
                    Intent intent=new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:+91"+number));
                    if(ActivityCompat.checkSelfPermission(ctx1, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        return;
                    }
                    ctx1.startActivity(intent);
                }
            });


        }
    }
}
