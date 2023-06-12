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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class GirisActivity extends AppCompatActivity {

    private Button kayitSayfasi;
    private EditText editEmail, editGsm, editSifre;
    private String txtEmail, txtGsm, txtSifre;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser; // kayıt olan kullacının bilgisini almak için kullanılır
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;
    private FirebaseFirestore mFirestore;
    // private DocumentReference docRef; // eğer sadece colection değilde dökümanlar üzerinde çalışacaksak/bilgisini alacaksak
    // kullanıcılar altında bir sürü kullanıcı varsa ve tüm kullanıcıları almak istersek documentreference kullanmak gereksiz. collection bazlı çalışmak gerekir.


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        kayitSayfasi = findViewById(R.id.kayitSayfasinaGit);

        editEmail = (EditText)findViewById(R.id.giris_yap_editEmail); // xml e bağladık
        // editGsm = (EditText)findViewById(R.id.giris_yap_editGsmNo);
        editSifre = (EditText)findViewById(R.id.giris_yap_editSifre);


        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser(); // uygulamaya giriş yaptınız, giriş yaptıktan sonra
        // bulunan kullanıcı olarak algılar. Sürekli id pass yazmadan uygulamaya giriş yapmayı sağlar.
    }

    public void kayitSayfasinaGit(View v){

        Intent intent = new Intent(GirisActivity.this, MainActivity.class); // Giris den Maine gider
        startActivity(intent);
        // finish();
    }

    @Override
    public void onBackPressed() { // Mobil cihazda geri tuşuna basıldığında çalışır
        Intent backIntent = new Intent(GirisActivity.this, MainActivity.class); // Giris Activity den Kayıtol (MainActivity) clasına dönüş yapar
        finish(); // Girisi i durdurur.
        startActivity(backIntent);
    }

    /*public void veriKayitSayfasinaGit(View v){
        Intent intent = new Intent(GirisActivity.this, GundeIcilenSigaraActivity.class); // Giris den GundeIcilenSigara ya gider
        startActivity(intent);
    }*/

    public void girisYap(View v){
        txtEmail = editEmail.getText().toString();
        // txtGsm = editGsm.getText().toString();
        txtSifre = editSifre.getText().toString();


        if(!TextUtils.isEmpty(txtEmail)  && !TextUtils.isEmpty(txtSifre)){ // txtEmail ve txtSifre boş değilse
            mAuth.signInWithEmailAndPassword(txtEmail, txtSifre)
                    .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            mUser = mAuth.getCurrentUser();

                            if(mUser != null){
                                verileriGetir(mUser.getUid());
                                // GirisActivity den GundeIcilenSigaraActivitye geçiş yapar.
                                 Intent intent = new Intent(GirisActivity.this, GundeIcilenSigaraActivity.class ); // Giris den GundeIcilenSigara ya gider
                                 startActivity(intent);
                            }



                            /*System.out.println("Kullanıcı Adı : " +mUser.getDisplayName()); // Kullanıcı adını gösterir ama biz adını girmedik
                            System.out.println("Kullanıcı Email :"+mUser.getEmail());
                            System.out.println("Kullanıcı Uid : " +mUser.getUid());
                            System.out.println("Kullanıcı Adı : " +mUser.getPhoneNumber());*/
                            Toast.makeText(GirisActivity.this, "Giriş Başarılı", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(GirisActivity.this, "Email veya Şifre Hatalı", Toast.LENGTH_LONG).show();
                        }
                    });

        }else{
            Toast.makeText(this, "Email veya Şifre Boş Olamaz", Toast.LENGTH_LONG).show();
        }

    }

    private void verileriGetir(String Email){

        mFirestore.collection("Kullanıcılar").document(Email);

        // Uid ye göre kullanıcya erişiyoruz
        // Realtimedatabase den verileri getirme
        /*mReference = FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(uid);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snp : snapshot.getChildren()){
                    System.out.println(snp.getKey() + " = " +snp.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GirisActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });*/
    }

    /*

    public void verileriSil(View v){
        mReference = FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(mUser.getUid());
        mReference.removeValue()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(GirisActivity.this, "Veriler Silindi", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(GirisActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
     */
}