package com.bbostt.flyfagerstromtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TerapistGirisActivity extends AppCompatActivity {


    EditText editEmail, editSifre;
    RelativeLayout rlt_terapistGiris;

    String txtEmail, txtSifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terapist_giris);

        editEmail = (EditText)findViewById(R.id.giris_yap_editEmail);
        editSifre = (EditText)findViewById(R.id.giris_yap_editSifre);

        rlt_terapistGiris = (RelativeLayout)findViewById(R.id.rlt_terapist_giris);
        rlt_terapistGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtEmail = editEmail.getText().toString();
                txtSifre = editSifre.getText().toString();

                if(!TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtSifre)){
                    if(editEmail.getText().toString().equals("terapist@flytelligence.com")
                            && editSifre.getText().toString().equals("terapist123")){
                        Toast.makeText(TerapistGirisActivity.this, "Giriş Yapıldı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TerapistGirisActivity.this, HastaListActivity.class); // TerapistGiris den HastaList e gider
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(TerapistGirisActivity.this, "Mail veya Şifre Hatalı", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TerapistGirisActivity.this, "Email veya Şifre Boş Olamaz", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}