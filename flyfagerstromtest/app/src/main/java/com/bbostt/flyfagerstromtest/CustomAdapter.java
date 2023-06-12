package com.bbostt.flyfagerstromtest;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<HastalarimSinifi> hastalarimSinifiArrayList = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;

    // veriler context te çalışacak, veriler hastalarimSinifiArrayList ten doldurulacak.

    public CustomAdapter(ArrayList<HastalarimSinifi> hastalarimSinifiArrayList, Context context) {
        this.hastalarimSinifiArrayList = hastalarimSinifiArrayList;
        this.context = context;
    }

    // Her bir satır için temsil edilecek arayüz seçilir.
    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup viewGroup, int position) {
        layoutInflater = LayoutInflater.from(context);
        // bendeki viewGroup videoda parent
        View v = layoutInflater.inflate(R.layout.row_list,viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder viewHolder, int position) {
        viewHolder.txtHastaisim.setText(hastalarimSinifiArrayList.get(position).getHastaIsim());
        viewHolder.txtGunlukOrtTuketim.setText(hastalarimSinifiArrayList.get(position).getGunlukOrtTuketim());
        viewHolder.hasta_image.setImageResource(hastalarimSinifiArrayList.get(position).getImgSrc());
        viewHolder.linearLayout.setTag(viewHolder);
        // en dıştaki linearlayout a tıklanıldığında yapılacak
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder viewHolder = (ViewHolder)v.getTag();
                int position = viewHolder.getLayoutPosition();
                String hastaIsim = hastalarimSinifiArrayList.get(position).getHastaIsim();
                String hastaOrtTuketim = hastalarimSinifiArrayList.get(position).getGunlukOrtTuketim();
                Toast.makeText(context, hastaIsim + " " +hastaOrtTuketim, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        // hastalarimSinifiArrayList e eklenen verilerin boyutu kadar döndürür.
        return hastalarimSinifiArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtHastaisim, txtGunlukOrtTuketim;
        ImageView hasta_image;
        LinearLayout linearLayout;

        public ViewHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            txtHastaisim = itemView.findViewById(R.id.txtHastaisim);
            txtGunlukOrtTuketim = itemView.findViewById(R.id.txtGunlukOrtTuketim);
            hasta_image = itemView.findViewById(R.id.hasta_image);
            linearLayout = itemView.findViewById(R.id.linear);
        }
    }
}
