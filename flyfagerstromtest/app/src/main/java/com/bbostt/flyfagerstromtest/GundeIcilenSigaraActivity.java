package com.bbostt.flyfagerstromtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bbostt.flyfagerstromtest.Adapters.CustomViewPager;
import com.bbostt.flyfagerstromtest.Fragments.Hafta_3Fragment;
import com.bbostt.flyfagerstromtest.Fragments.Hafta_1Fragment;
import com.bbostt.flyfagerstromtest.Fragments.Hafta_2Fragment;
import com.bbostt.flyfagerstromtest.Fragments.Hafta_4Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public class GundeIcilenSigaraActivity extends AppCompatActivity {

    private ViewPager mViewPager; // ViewPager lar, içerisinde yana doğru kaydırılabilen nesneleri aynı anda bir ortamda göstermesini sağlıyor.
    // Fragment oluşturma :
    // Android < app < java < en üsttekine sağ tıkla < new < Package < Fragments yazdım
    // Android < app < java < en üsttekine sağ tıkla < new < Package < Adapters yazdım
    // Oluşturduğumuz Fragments isimli dosyaya sağ tıkla < new < Fragment < Fragment (Blank)
    // < Hafta_1Fragment (yazdım) Finish dedim.
    private TabLayout mTablayout;
    private CustomViewPager mAdapter;
    private void init(){
        mViewPager = findViewById(R.id.activity_gundeicilensigara_viewPager); // layout kısmında bağladık
        mTablayout = findViewById(R.id.gundeicilensigara_tableyout);

        mAdapter = new CustomViewPager(getSupportFragmentManager());
        mAdapter.addFragment(new Hafta_1Fragment(), "1.Hafta");
        mAdapter.addFragment(new Hafta_2Fragment(), "2.Hafta");
        mAdapter.addFragment(new Hafta_3Fragment(), "3.Hafta");
        mAdapter.addFragment(new Hafta_4Fragment(), "4.Hafta");

        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);

    }

    private TextView txtView8_12, txtView12_16, txtView16_20, txtView20_00, txtView00_08;
    private EditText editTxt8_12, editTxt12_16, editTxt16_20, editTxt20_00, editTxt00_08;

    private String Txt8_12, Txt12_16, Txt16_20, Txt20_00, Txt00_08;

    private Button btnKayit;
    private Spinner spinnerGunler;
    // ArrayAdapterler ile spinnerların içini dolduruyoruz
    // integer tutacaksa ArrayAdapter <Integer> yazarız
    private ArrayAdapter<CharSequence> adapterspinnerGunler;
    private int gunlukSigaraToplam, gunlukSigara8_12, gunlukSigara12_16, gunlukSigara16_20;
    private int gunlukSigara20_00, gunlukSigara00_08;
    private int x;
    private int selectedSpinnerIndex = -1;

    private int selectedTablayoutIndex = -1;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private HashMap<String, Object> mData; // Key - Value olarak kayıt eder, keyler String. // veriyi güncellemek için kullanıyoruz
    private FirebaseFirestore mFirestore =  FirebaseFirestore.getInstance();

    private DocumentReference noteRef;
    private DatabaseReference mReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunde_icilen_sigara);

        init();

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference(); // mReference ile veri tabanına veriyi kaydedebiliyoruz
        mFirestore = FirebaseFirestore.getInstance();

        mTablayout = findViewById(R.id.gundeicilensigara_tableyout);
        mViewPager = findViewById(R.id.activity_gundeicilensigara_viewPager);

        txtView8_12 = (TextView)findViewById(R.id.txtView8_12); // xml e bağlıyoruz
        txtView12_16 = (TextView)findViewById(R.id.txtView12_16); // xml e bağlıyoruz
        txtView16_20 = (TextView)findViewById(R.id.txtView16_20); // xml e bağlıyoruz
        txtView20_00 = (TextView)findViewById(R.id.txtView20_00); // xml e bağlıyoruz
        txtView00_08 = (TextView)findViewById(R.id.txtView00_08); // xml e bağlıyoruz

        editTxt8_12 = (EditText)findViewById(R.id.editTxt8_12);
        editTxt12_16 = (EditText)findViewById(R.id.editTxt12_16);
        editTxt16_20 = (EditText)findViewById(R.id.editTxt16_20);
        editTxt20_00 = (EditText)findViewById(R.id.editTxt20_00);
        editTxt00_08 = (EditText)findViewById(R.id.editTxt00_08);

        btnKayit = (Button)findViewById(R.id.btnKaydet);


        spinnerGunler = findViewById(R.id.spinnerGunler);


        adapterspinnerGunler = ArrayAdapter.createFromResource(GundeIcilenSigaraActivity.this, R.array.GunlerList, android.R.layout.simple_spinner_item);
        adapterspinnerGunler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGunler.setAdapter(adapterspinnerGunler); // spinnera, adapteri bağlama

        mUser = mAuth.getCurrentUser();

        spinnerKayit(); // spinnerda seçilen index değerini kaydeder.
        // getSelectedTablayoutText(); // tablayoutta seçilen yazıyı alır. 1.Hafta, 2.Hafta gibi
        tablayoutKayit();

    }




    public void sigaraKaydet(View v){
        Txt8_12 = editTxt8_12.getText().toString();
        Txt12_16 = editTxt12_16.getText().toString();
        Txt16_20 = editTxt16_20.getText().toString();
        Txt20_00 = editTxt20_00.getText().toString();
        Txt00_08 = editTxt00_08.getText().toString();
        if(!TextUtils.isEmpty(Txt8_12) && !TextUtils.isEmpty(Txt12_16)
        && !TextUtils.isEmpty(Txt16_20) && !TextUtils.isEmpty(Txt20_00)
        && !TextUtils.isEmpty(Txt00_08)) { // eğer saatlik sigara miktarları girilmiş ise
            // Guncelleme gibi çalışacak
            gunlukİcilienSigaralariHesapla();
            mData = new HashMap<>();
            mData.put("8-12 Arası İçilen Sigara", gunlukSigara8_12); // Database deki diğer verileri silmeden 8-12 Arası İçilen Sigara bölümüne update yapar
            mData.put("12-16 Arası İçilen Sigara", gunlukSigara12_16);
            mData.put("16-20 Arası İçilen Sigara", gunlukSigara16_20);
            mData.put("20-00 Arası İçilen Sigara", gunlukSigara20_00);
            mData.put("00-08 Arası İçilen Sigara", gunlukSigara00_08);
            mData.put("Günlük içilen toplam Sigara",gunlukSigaraToplam);

        /*public int getSelectedSpinnerIndex() {
            return selectedSpinnerIndex;
        }*/
            // methodu oluşturdum
            // GundeIcilenSigaraActivity deger = (GundeIcilenSigaraActivity) this oluşturdum
            // int selectedSpinnerIndex = deger.getSelectedSpinnerIndex(); atadım
            // selectedSpinnerIndex'i eklenecek günde kullandım

            GundeIcilenSigaraActivity deger = (GundeIcilenSigaraActivity) this;
            int selectedSpinnerIndex = deger.getSelectedSpinnerIndex();
            // use the selectedSpinnerIndex variable as needed

            // tablayoutta seçilen indexi kullanabilmek için aşağıdaki iki kodu  yazıyoruz
            // GundeIcilenSigaraActivity tablelayoutDeger = (GundeIcilenSigaraActivity) this;
            // int selectedTablayoutIndex = tablelayoutDeger.getSelectedTablayoutIndex();




            // burdaki deger, spinnerda seçilen indexe göre olmalı
            // "DenemeMail" yerine mUser.getEmail() yazmak gerekir
            // Integer.toString(i) , int i değeri Stringe çevirir
            // databaseye mData içindeki verileri ekler

            mUser = mAuth.getCurrentUser();
            // mUser.getEmail()' i kullanmak için giriş yapmak gerekiyor.

            if(selectedSpinnerIndex != 0){ // selectedSpinnerIndex 0 değil ise yani spinner da Gün Seçiniz seçili değil ise
                noteRef = mFirestore.collection("Kullanıcılar").document(mUser.getEmail())
                        .collection(Integer.toString((selectedTablayoutIndex + 1))+".Hafta")
                        .document(Integer.toString(selectedSpinnerIndex)+".Gün");


                noteRef.set(mData, SetOptions.merge()); // Database deki diğer verileri silmeden Seçilen hafta ve
                // seçilen Gün için İçilen Sigara bölümüne update yapar



                Toast.makeText(GundeIcilenSigaraActivity.this, "Kayıt Başarılı", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(GundeIcilenSigaraActivity.this, "Gün Seçiniz", Toast.LENGTH_LONG).show();
            }


        }
        else{
            System.out.println("Değerler boş olamaz");
            Toast.makeText(GundeIcilenSigaraActivity.this, "Değerler Boş Olamaz", Toast.LENGTH_LONG).show();
        }



    }

    public int getSelectedSpinnerIndex() {

        return selectedSpinnerIndex;

        // spinnerda seçilen index değerini döndürür.
        // En yukarı private int selectedSpinnerIndex = -1; tanımladım
    }


    public void tablayoutKayit(){
        mViewPager = findViewById(R.id.activity_gundeicilensigara_viewPager); // layout kısmında bağladık
        mTablayout = findViewById(R.id.gundeicilensigara_tableyout);

        mAdapter = new CustomViewPager(getSupportFragmentManager());
        mAdapter.addFragment(new Hafta_1Fragment(), "1.Hafta");
        mAdapter.addFragment(new Hafta_2Fragment(), "2.Hafta");
        mAdapter.addFragment(new Hafta_3Fragment(), "3.Hafta");
        mAdapter.addFragment(new Hafta_4Fragment(), "4.Hafta");

        mViewPager.setAdapter(mAdapter);
        mTablayout.setupWithViewPager(mViewPager);

        TabLayout.Tab firstTab = mTablayout.getTabAt(0); //mTablayout taki 0. indexi firstTab e attım.
        mTablayout.selectTab(firstTab); //mTablayout daki yazılardan 0.indexli olanı seçer.
        System.out.println("firstTab: " +firstTab); // bana lazım olan bu değil
        selectedTablayoutIndex = firstTab.getPosition(); // 0. indextekini soldakine atar
        System.out.println("Tablayoutta seçilen Index : " + selectedTablayoutIndex); // lazım olan bu

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTablayoutIndex = tab.getPosition(); // tablayoutta seçili indexi soldaki değere atar.
                System.out.println("Tablayoutta seçilen Index : " + selectedTablayoutIndex);
                System.out.println("Seçilen Tab : " +tab); // TabLayout adını yazar.
                if(selectedTablayoutIndex == 0){
                    txtlerinIciniTemizle();
                }
                else if(selectedTablayoutIndex == 1){
                    txtlerinIciniTemizle();
                }
                else if(selectedTablayoutIndex == 2){
                    txtlerinIciniTemizle();
                }
                else if(selectedTablayoutIndex == 3){
                    txtlerinIciniTemizle();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /*
    public void getSelectedTablayoutText(){

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Seçilen öğenin değerini bir değişkene atar
                selectedTablayoutYazisi = tab.getText().toString();
                System.out.println(selectedTablayoutYazisi);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }*/





    public void gunlukİcilienSigaralariHesapla(){
        gunlukSigara8_12 = Integer.parseInt(String.valueOf(editTxt8_12.getText()));
        gunlukSigara12_16 = Integer.parseInt(String.valueOf(editTxt12_16.getText()));
        gunlukSigara16_20 = Integer.parseInt(String.valueOf(editTxt16_20.getText()));
        gunlukSigara20_00 = Integer.parseInt(String.valueOf(editTxt20_00.getText()));
        gunlukSigara00_08 = Integer.parseInt(String.valueOf(editTxt00_08.getText()));

        gunlukSigaraToplam = (gunlukSigara8_12 + gunlukSigara12_16 + gunlukSigara16_20 + gunlukSigara20_00 + gunlukSigara00_08);
        // System.out.println(gunlukSigaraToplam);

        // Günde belirli saatler aralığında içilen sigaraları EditTextlere giriyoruz
        // EditTextlere girilen sigara sayılarını gunlukSigara saatlerine atıyoruz
        // gunluk içilen toplam sigara sayısını buluyoruz

    }

    public void spinnerKayit(){

        spinnerGunler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // spinner seçilen indexe göre çalışır
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        System.out.println("Gün seçiniz");
                        Toast.makeText(GundeIcilenSigaraActivity.this, "Lütfen Gün Seçiniz", Toast.LENGTH_LONG).show();
                        selectedSpinnerIndex = position;
                        break;

                    case 1:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "1.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle(); // 1. gün seçilirse, saatlik içilen sigara değerlerini yazdığımız text temizlenir.

                        break;

                    case 2:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "2.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;
                    case 3:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "3.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;
                    case 4:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "4.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;
                    case 5:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "5.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;
                    case 6:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "6.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;
                    case 7:

                        selectedSpinnerIndex = position;
                        Toast.makeText(GundeIcilenSigaraActivity.this, "7.Gün Seçildi", Toast.LENGTH_LONG).show();
                        txtlerinIciniTemizle();
                        break;

                    default :
                        System.out.println("Default değer");
                        Toast.makeText(GundeIcilenSigaraActivity.this, "Error Gün Seçiniz", Toast.LENGTH_LONG).show();
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(GundeIcilenSigaraActivity.this, "Error Gün Seçiniz", Toast.LENGTH_LONG).show();
            }

        });

    }

    public void txtlerinIciniTemizle(){
        editTxt8_12.setText("");
        editTxt12_16.setText("");
        editTxt16_20.setText("");
        editTxt20_00.setText("");
        editTxt00_08.setText("");
    }



}







