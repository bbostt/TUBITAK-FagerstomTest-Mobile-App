package com.bbostt.flyfagerstromtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Önce Kullanıcıyı Authentication bölümüne kaydeder.
    // Kayıt başarılı ise
    // Kullanıcıyı Mail adresi ve Telefon numarası ile Giriş Yapma Yöntemine kaydeder ve
    // database de Kullanıcılar koleksiyonu altında, mail adresine göre document açar.
    // mail adresindeki document içerisine, kullaniciMail
    //                                      kullaniciGsm,
    //                                      kullaniciId, bilgilerini kaydeder.

    private Button hastaGirisSayfasi;
    private Button terapistGirisSayfasi;
    private EditText editEmail, editGsm, editSifre;
    private String txtEmail, txtGsm, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData; // Key - Value olarak kayıt eder, keyler String.
    private FirebaseFirestore mFirestore;






    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        hastaGirisSayfasi = (Button)findViewById(R.id.btn_hastaGirisSayfasi);
        terapistGirisSayfasi = (Button)findViewById(R.id.btn_terapistGirisSayfasi);


        editEmail = (EditText)findViewById(R.id.kayit_ol_editEmail); // xml e bağladık
        editSifre = (EditText)findViewById(R.id.kayit_ol_editSifre);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference(); // veri tabanına veriyi kaydedebiliyoruz
        mFirestore = FirebaseFirestore.getInstance();


    }



    public void hastagirisSayfasinaGit(View v){
        Intent intent = new Intent(MainActivity.this, GirisActivity.class); // Main den Giris e gider
        startActivity(intent);
    }

    public void terapistGirisSayfasinaGit(View v){
        Intent intent = new Intent(MainActivity.this, TerapistGirisActivity.class); // Main den TerapistGiris e gider
        startActivity(intent);
    }

    public void kayitOl(View v){
        txtEmail = editEmail.getText().toString();

        // txtGsm = editGsm.getText().toString();

        txtSifre = editSifre.getText().toString();

        // Not ! Gsm girmek için EditText şuan yok.
        if(!TextUtils.isEmpty(txtEmail) &&  /* !TextUtils.isEmpty(txtGsm) && */ !TextUtils.isEmpty(txtSifre)){
            mAuth.createUserWithEmailAndPassword(txtEmail, txtSifre)
                    // bulunduğumuz aktivite this
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){ // Kullanıcıyı Giriş Yapma Yöntemine kaydetme başarılı ise
                                mUser = mAuth.getCurrentUser();
                                mData = new HashMap<>();
                                mData.put("kullaniciMail", txtEmail);
                                mData.put("kullaniciGsm", txtGsm);
                                mData.put("kullaniciSifre", txtSifre);
                                mData.put("kullaniciId", mUser.getUid());



                                // Firestore database e verileri ekliyoruz.
                                // Database e kullanıcılar ismi altında maile göre isimlendirip ekleme yaptık
                                mFirestore.collection("Kullanıcılar").document(mUser.getEmail())
                                        .set(mData)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(MainActivity.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                                                }else{
                                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                

                                // Realtime Database
                                /* mReference.child("Kullanıcılar").child(mUser.getUid())
                                        .setValue(mData)
                                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){ // Database Kayıt başarılı ise
                                                    Toast.makeText(MainActivity.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }); */
                            }else{
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }else{
            Toast.makeText(this, "Email, Şifre ve Numara Boş Olamaz", Toast.LENGTH_LONG).show();
        }

    }
}