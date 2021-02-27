package com.fouadbahari.medicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fouadbahari.medicapp.db.MedicDB;
import com.fouadbahari.medicapp.db.MedicData;
import com.fouadbahari.medicapp.db.Repository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class AddMedicamentActivity extends AppCompatActivity {

    private EditText classe_therapeutique;
    private EditText nom_commercial;
    private EditText laboratoire;
    private EditText denomination_du_medicament;
    private EditText forme_pharmaceutique;
    private EditText duree_de_conservation;
    private EditText remborsable;
    private EditText date_de_fabrication;
    private EditText date_de_peremption;
    private EditText description_de_composant;
    private EditText prix;
    private EditText quantite_en_stock;
    private EditText lot;
    private Button qr_code;
    private Button ajouter_medicament;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;


    private String code_barre;

    Repository repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicament);

        classe_therapeutique = findViewById(R.id.classe_therapeutique);
        nom_commercial = findViewById(R.id.nom_commercial);
        laboratoire = findViewById(R.id.laboratoire);
        denomination_du_medicament = findViewById(R.id.denomination_du_medicament);
        forme_pharmaceutique = findViewById(R.id.forme_pharmaceutique);
        duree_de_conservation = findViewById(R.id.duree_de_conservation);
        remborsable = findViewById(R.id.remborsable);
        date_de_fabrication = findViewById(R.id.date_de_fabrication);
        date_de_peremption = findViewById(R.id.date_de_peremption);
        description_de_composant = findViewById(R.id.description_de_composant);
        prix = findViewById(R.id.prix);
        quantite_en_stock = findViewById(R.id.quantite_en_stock);
        lot = findViewById(R.id.lot);
        qr_code = findViewById(R.id.qr_code);
        ajouter_medicament = findViewById(R.id.ajouter_medicament);



        repository = new Repository(getApplication());

        firebaseDatabase =  FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("medicdb");


        ajouter_medicament.setEnabled(false);


        TextWatcher tw = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date_de_fabrication.setText(current);
                    date_de_fabrication.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };

        TextWatcher tw2 = new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    date_de_peremption.setText(current);
                    date_de_peremption.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        };


        date_de_fabrication.addTextChangedListener(tw);
        date_de_peremption.addTextChangedListener(tw2);

        qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scanCode();

            }
        });


        ajouter_medicament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String classe_ther = classe_therapeutique.getText().toString().trim();
                String nom_commerc = nom_commercial.getText().toString().trim();
                String labor = laboratoire.getText().toString().trim();
                String denom = denomination_du_medicament.getText().toString().trim();
                String forme = forme_pharmaceutique.getText().toString().trim();
                String duree = duree_de_conservation.getText().toString().trim();
                String remb = remborsable.getText().toString().trim();
                String fab = date_de_fabrication.getText().toString().trim();
                String peremp = date_de_peremption.getText().toString().trim();
                String desc = description_de_composant.getText().toString().trim();
                String price = prix.getText().toString().trim();
                String quantite = quantite_en_stock.getText().toString().trim();
                String lott = lot.getText().toString().trim();

                if (TextUtils.isEmpty(classe_ther)){
                    classe_therapeutique.setError("Merci de remplir ce champ");
                    classe_therapeutique.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(nom_commerc)){
                    nom_commercial.setError("Merci de remplir ce champ");
                    nom_commercial.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(labor)){
                    laboratoire.setError("Merci de remplir ce champ");
                    laboratoire.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(denom)){
                    denomination_du_medicament.setError("Merci de remplir ce champ");
                    denomination_du_medicament.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(forme)){
                    forme_pharmaceutique.setError("Merci de remplir ce champ");
                    forme_pharmaceutique.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(duree)){
                    duree_de_conservation.setError("Merci de remplir ce champ");
                    duree_de_conservation.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(remb)){
                    remborsable.setError("Merci de remplir ce champ");
                    remborsable.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(fab)){
                    date_de_fabrication.setError("Merci de remplir ce champ");
                    date_de_fabrication.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(peremp)){
                    date_de_peremption.setError("Merci de remplir ce champ");
                    date_de_peremption.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(lott)){
                    lot.setError("Merci de remplir ce champ");
                    lot.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(desc)){
                    description_de_composant.setError("Merci de remplir ce champ");
                    description_de_composant.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(price)){
                    prix.setError("Merci de remplir ce champ");
                    prix.setFocusable(true);
                    return;
                }

                if (TextUtils.isEmpty(quantite)){
                    quantite_en_stock.setError("Merci de remplir ce champ");
                    quantite_en_stock.setFocusable(true);
                    return;
                }




                MedicData medicData = new MedicData(classe_ther,nom_commerc,labor,denom,forme,
                                                    duree,remb,fab,peremp,desc,price,quantite,lott,code_barre);

                repository.insert(medicData);

                reference.child(medicData.nom_commercial).setValue(medicData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                startActivity(new Intent(AddMedicamentActivity.this,MainActivity.class));
                finish();
            }
        });
    }


    private void scanCode(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CapActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanner le QR");
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() != null){
                code_barre = result.getContents().toString();
                ajouter_medicament.setEnabled(true);

            }else {
                Toast.makeText(this, "aucune resultat", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}