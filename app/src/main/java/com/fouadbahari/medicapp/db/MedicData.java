package com.fouadbahari.medicapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "medic_table")
public class MedicData implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "classe_therapeutique")
    public String classe_therapeutique;

    @ColumnInfo(name = "nom_commercial")
    public String nom_commercial;

    @ColumnInfo(name = "laboratoire")
    public String laboratoire;

    @ColumnInfo(name = "denomination_du_medicament")
    public String denomination_du_medicament;

    @ColumnInfo(name = "forme_pharmaceutique")
    public String forme_pharmaceutique;

    @ColumnInfo(name = "duree_de_conservation")
    public String duree_de_conservation;

    @ColumnInfo(name = "remborsable")
    public String remborsable;

    @ColumnInfo(name = "date_de_fabrication")
    public String date_de_fabrication;

    @ColumnInfo(name = "date_de_peremption")
    public String date_de_peremption;

    @ColumnInfo(name = "description_de_composant")
    public String description_de_composant;

    @ColumnInfo(name = "prix")
    public String prix;

    @ColumnInfo(name = "quantite_en_stock")
    public String quantite_en_stock;

    @ColumnInfo(name = "lot")
    public String lot;

    @ColumnInfo(name = "code_barre")
    public String code_barre;


    public MedicData() {

    }


    public MedicData(int id, String classe_therapeutique, String nom_commercial, String laboratoire, String denomination_du_medicament, String forme_pharmaceutique, String duree_de_conservation, String remborsable, String date_de_fabrication, String date_de_peremption, String description_de_composant, String prix, String quantite_en_stock, String lot, String code_barre) {
        this.id = id;
        this.classe_therapeutique = classe_therapeutique;
        this.nom_commercial = nom_commercial;
        this.laboratoire = laboratoire;
        this.denomination_du_medicament = denomination_du_medicament;
        this.forme_pharmaceutique = forme_pharmaceutique;
        this.duree_de_conservation = duree_de_conservation;
        this.remborsable = remborsable;
        this.date_de_fabrication = date_de_fabrication;
        this.date_de_peremption = date_de_peremption;
        this.description_de_composant = description_de_composant;
        this.prix = prix;
        this.quantite_en_stock = quantite_en_stock;
        this.lot = lot;
        this.code_barre = code_barre;
    }

    public MedicData(String classe_therapeutique, String nom_commercial, String laboratoire, String denomination_du_medicament, String forme_pharmaceutique, String duree_de_conservation, String remborsable, String date_de_fabrication, String date_de_peremption, String description_de_composant, String prix, String quantite_en_stock, String lot, String code_barre) {
        this.classe_therapeutique = classe_therapeutique;
        this.nom_commercial = nom_commercial;
        this.laboratoire = laboratoire;
        this.denomination_du_medicament = denomination_du_medicament;
        this.forme_pharmaceutique = forme_pharmaceutique;
        this.duree_de_conservation = duree_de_conservation;
        this.remborsable = remborsable;
        this.date_de_fabrication = date_de_fabrication;
        this.date_de_peremption = date_de_peremption;
        this.description_de_composant = description_de_composant;
        this.prix = prix;
        this.quantite_en_stock = quantite_en_stock;
        this.lot = lot;
        this.code_barre = code_barre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasse_therapeutique() {
        return classe_therapeutique;
    }

    public void setClasse_therapeutique(String classe_therapeutique) {
        this.classe_therapeutique = classe_therapeutique;
    }

    public String getNom_commercial() {
        return nom_commercial;
    }

    public void setNom_commercial(String nom_commercial) {
        this.nom_commercial = nom_commercial;
    }

    public String getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(String laboratoire) {
        this.laboratoire = laboratoire;
    }

    public String getDenomination_du_medicament() {
        return denomination_du_medicament;
    }

    public void setDenomination_du_medicament(String denomination_du_medicament) {
        this.denomination_du_medicament = denomination_du_medicament;
    }

    public String getForme_pharmaceutique() {
        return forme_pharmaceutique;
    }

    public void setForme_pharmaceutique(String forme_pharmaceutique) {
        this.forme_pharmaceutique = forme_pharmaceutique;
    }

    public String getDuree_de_conservation() {
        return duree_de_conservation;
    }

    public void setDuree_de_conservation(String duree_de_conservation) {
        this.duree_de_conservation = duree_de_conservation;
    }

    public String getRemborsable() {
        return remborsable;
    }

    public void setRemborsable(String remborsable) {
        this.remborsable = remborsable;
    }

    public String getDate_de_fabrication() {
        return date_de_fabrication;
    }

    public void setDate_de_fabrication(String date_de_fabrication) {
        this.date_de_fabrication = date_de_fabrication;
    }

    public String getDate_de_peremption() {
        return date_de_peremption;
    }

    public void setDate_de_peremption(String date_de_peremption) {
        this.date_de_peremption = date_de_peremption;
    }

    public String getDescription_de_composant() {
        return description_de_composant;
    }

    public void setDescription_de_composant(String description_de_composant) {
        this.description_de_composant = description_de_composant;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getQuantite_en_stock() {
        return quantite_en_stock;
    }

    public void setQuantite_en_stock(String quantite_en_stock) {
        this.quantite_en_stock = quantite_en_stock;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getCode_barre() {
        return code_barre;
    }

    public void setCode_barre(String code_barre) {
        this.code_barre = code_barre;
    }
}
