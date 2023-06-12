package com.bbostt.flyfagerstromtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import java.util.ArrayList;

public class HastaListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HastalarimSinifi> hastalarimSinifi = new ArrayList<>();
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasta_list);
        recyclerView = findViewById(R.id.recycler_view); // recycler_view id si activity_hasta_list.xml içerisinde en üstte
        // layoutManager verilerin nasıl konumlanacağını belirler.
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); // verileri dikey hizalar
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true); // performansı arttırıyormuş
        // verileri ekliyoruz.
        hastalarimSinifi.add(new HastalarimSinifi("GEORGE","Günlük Ortalama Tüketim 15 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("NANCY","Günlük Ortalama Tüketim 3 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("BILL","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("GANDALF","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("LEGOLAS","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("ARAGORN","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("GIMLY","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("BILLBO","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("BAGGINS","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("HOBBITS","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("ELVES","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("ORCS","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));
        hastalarimSinifi.add(new HastalarimSinifi("GOBLINS","Günlük Ortalama Tüketim 8 Adet",R.mipmap.hasta_icon));


        // veriler nerden alınacak ? nerede çalıştırılacak
        // hastalarimSinifi isimli ArrayList den alınacak, context de çalıştırılacak
        CustomAdapter customAdapter = new CustomAdapter(hastalarimSinifi, context);
        recyclerView.setAdapter(customAdapter);


    }

    @Override
    public void onBackPressed() { // Mobil cihazda geri tuşuna basıldığında çalışır
        // HastaList den TerapistGiris clasına dönüş yapar
        Intent backIntent = new Intent(HastaListActivity.this, TerapistGirisActivity.class);
        finish(); // HastaList i durdurur.
        startActivity(backIntent);
    }
}