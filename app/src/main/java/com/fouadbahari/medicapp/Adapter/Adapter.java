package com.fouadbahari.medicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fouadbahari.medicapp.R;
import com.fouadbahari.medicapp.db.MedicDB;
import com.fouadbahari.medicapp.db.MedicData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<MedicData> medicDataList;
    private MedicDB database;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;

    public Adapter(Context context, List<MedicData> medicDataList) {
        this.context = context;
        this.medicDataList = medicDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicament_item_layout,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicData medicData = medicDataList.get(position);
        database = MedicDB.getInstance(context);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("medicdb");

        holder.txt_classe_ther.setText(medicData.classe_therapeutique);
        holder.txt_nom_com.setText(medicData.nom_commercial);
        holder.txt_labo.setText(medicData.laboratoire);
        holder.txt_denomination_medic.setText(medicData.denomination_du_medicament);
        holder.txt_forme_pharm.setText(medicData.forme_pharmaceutique);
        holder.txt_duree_conserv.setText(medicData.duree_de_conservation);
        holder.txt_remborsable.setText(medicData.remborsable);
        holder.txt_date_fab.setText(medicData.date_de_fabrication);
        holder.txt_date_peremp.setText(medicData.date_de_peremption);
        holder.txt_prix.setText(medicData.prix);
        holder.txt_quantite_stock.setText(medicData.quantite_en_stock);
        holder.txt_lot.setText(medicData.lot);

        holder.delete_medic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicData medic = medicDataList.get(holder.getAdapterPosition());
                database.medicDAO().delete(medic);
                int position = holder.getAdapterPosition();
                medicDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,medicDataList.size());

                reference.child(medic.nom_commercial)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_classe_ther;
        private TextView txt_nom_com;
        private TextView txt_labo;
        private TextView txt_denomination_medic;
        private TextView txt_forme_pharm;
        private TextView txt_duree_conserv;
        private TextView txt_remborsable;
        private TextView txt_date_fab;
        private TextView txt_date_peremp;
        private TextView txt_prix;
        private TextView txt_quantite_stock;
        private TextView txt_lot;
        private ImageButton delete_medic;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            txt_classe_ther = itemView.findViewById(R.id.txt_classe_ther);
            txt_nom_com = itemView.findViewById(R.id.txt_nom_com);
            txt_labo = itemView.findViewById(R.id.txt_labo);
            txt_denomination_medic = itemView.findViewById(R.id.txt_denomination_medic);
            txt_forme_pharm = itemView.findViewById(R.id.txt_forme_pharm);
            txt_duree_conserv = itemView.findViewById(R.id.txt_duree_conserv);
            txt_remborsable = itemView.findViewById(R.id.txt_remborsable);
            txt_date_fab = itemView.findViewById(R.id.txt_date_fab);
            txt_date_peremp = itemView.findViewById(R.id.txt_date_peremp);
            txt_prix = itemView.findViewById(R.id.txt_prix);
            txt_quantite_stock = itemView.findViewById(R.id.txt_quantite_stock);
            txt_lot = itemView.findViewById(R.id.txt_lot);
            delete_medic = itemView.findViewById(R.id.delete_medic);

        }
    }
}
