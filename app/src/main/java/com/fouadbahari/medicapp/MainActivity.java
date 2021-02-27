package com.fouadbahari.medicapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fouadbahari.medicapp.Adapter.Adapter;
import com.fouadbahari.medicapp.db.MedicDB;
import com.fouadbahari.medicapp.db.MedicData;
import com.fouadbahari.medicapp.db.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;
    private RecyclerView recycler_medicament;

    private List<MedicData> dataList = new ArrayList<MedicData>();
    private LinearLayoutManager linearLayoutManager;
    private MedicDB database;
    private Adapter medicAdapter;
    private Dialog dialog;

    private String code_barre;
    private Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.add_button);
        recycler_medicament = findViewById(R.id.recycler_medicament);
        dialog = new Dialog(this);

        database = MedicDB.getInstance(this);
        repository = new Repository(getApplication());


        linearLayoutManager = new LinearLayoutManager(this);

        recycler_medicament.setLayoutManager(linearLayoutManager);

//        boolean checkNetworlkConncetion = isNetworkConnected();
//        if (checkNetworlkConncetion){
//            reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            MedicData data = dataSnapshot.getValue(MedicData.class);
//                            dataList.add(data);
//                        }
//                    }else {
//                        Toast.makeText(getApplicationContext(), "no result", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }else {
//            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
//        }

        dataList = repository.getAll();

        medicAdapter = new Adapter(MainActivity.this,dataList);

        recycler_medicament.setAdapter(medicAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddMedicamentActivity.class));

            }
        });
    }
//
//    private boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_scan_search,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.scan_search: scanSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void scanSearch(){

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
                code_barre = result.getContents();
                MedicData resultSearch = database.medicDAO().findMedicByCode(code_barre);
                if (resultSearch == null){
                    Toast.makeText(MainActivity.this, "No result", Toast.LENGTH_SHORT).show();

                }else {
                    showDialog(resultSearch);
                }

            }else {
                Toast.makeText(this, "aucune resultat", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void showDialog(MedicData resultSearch){
        dialog.setContentView(R.layout.popup_medic);

        TextView txt_classe_ther;
        TextView txt_nom_com;
        TextView txt_labo;
        TextView txt_denomination_medic;
        TextView txt_forme_pharm;
        TextView txt_duree_conserv;
        TextView txt_remborsable;
        TextView txt_date_fab;
        TextView txt_date_peremp;
        TextView txt_prix;
        TextView txt_quantite_stock;
        TextView txt_lot;
        Button cancel_btn;

        txt_classe_ther = dialog.findViewById(R.id.txt_classe_ther);
        txt_nom_com = dialog.findViewById(R.id.txt_nom_com);
        txt_labo = dialog.findViewById(R.id.txt_labo);
        txt_denomination_medic = dialog.findViewById(R.id.txt_denomination_medic);
        txt_forme_pharm = dialog.findViewById(R.id.txt_forme_pharm);
        txt_duree_conserv = dialog.findViewById(R.id.txt_duree_conserv);
        txt_remborsable = dialog.findViewById(R.id.txt_remborsable);
        txt_date_fab = dialog.findViewById(R.id.txt_date_fab);
        txt_date_peremp = dialog.findViewById(R.id.txt_date_peremp);
        txt_prix = dialog.findViewById(R.id.txt_prix);
        txt_quantite_stock = dialog.findViewById(R.id.txt_quantite_stock);
        txt_lot = dialog.findViewById(R.id.txt_lot);
        cancel_btn = dialog.findViewById(R.id.cancel_btn);

        txt_classe_ther.setText(resultSearch.classe_therapeutique);
        txt_nom_com.setText(resultSearch.nom_commercial);
        txt_labo.setText(resultSearch.laboratoire);
        txt_denomination_medic.setText(resultSearch.denomination_du_medicament);
        txt_forme_pharm.setText(resultSearch.forme_pharmaceutique);
        txt_duree_conserv.setText(resultSearch.duree_de_conservation);
        txt_remborsable.setText(resultSearch.remborsable);
        txt_date_fab.setText(resultSearch.date_de_fabrication);
        txt_date_peremp.setText(resultSearch.date_de_peremption);
        txt_prix.setText(resultSearch.prix);
        txt_quantite_stock.setText(resultSearch.quantite_en_stock);
        txt_lot.setText(resultSearch.lot);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.create();
        dialog.show();

    }
}