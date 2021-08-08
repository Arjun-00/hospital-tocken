package com.example.mrdr.medicines;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrdr.R;

import java.util.List;

public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder> {
    LayoutInflater inflater;
    List<Medicineclass> medicines;

    public Adapter1(Context ctx,List<Medicineclass> medicines){
        this.inflater = LayoutInflater.from(ctx);
        this.medicines = medicines;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.medicine_custom_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mdname.setText(medicines.get(position).getMdname());
        holder.mdprice.setText(medicines.get(position).getPrice());
        holder.mdcompany.setText(medicines.get(position).getCompany());
        //holder.mdimage.setImageBitmap(medicines.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mdname,mdprice,mdcompany;
       // ImageView mdimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mdname = itemView.findViewById(R.id.mdname);
            mdprice = itemView.findViewById(R.id.mdprice);
            mdcompany = itemView.findViewById(R.id.mdcompany);
           // mdname = itemView.findViewById(R.id.mdimage);


        }
    }
}
