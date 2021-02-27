package com.fouadbahari.medicapp.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class Repository {
    private MedicDAO medicDAO;
    private List<MedicData> allMedic;

    public Repository(Application application){
        MedicDB medicDB = MedicDB.getInstance(application);
        medicDAO = medicDB.medicDAO();
        allMedic = medicDAO.getAll();
    }

    public void insert(MedicData medicData){
        new InsertMedicAsynckTask(medicDAO).execute(medicData);

    }

    public void delete(MedicData medicData){
        new DeleteMedicAsynckTask(medicDAO).execute(medicData);

    }

    public List<MedicData> getAll(){
        return allMedic;
    }

    public static class InsertMedicAsynckTask extends AsyncTask<MedicData,Void,Void> {
        private MedicDAO medicDAO;
        private InsertMedicAsynckTask(MedicDAO medicDAO){
            this.medicDAO = medicDAO;
        }

        @Override
        protected Void doInBackground(MedicData... medicData) {
            medicDAO.insert(medicData[0]);
            return null;
        }
    }


    public static class DeleteMedicAsynckTask extends AsyncTask<MedicData,Void,Void> {
        private MedicDAO medicDAO;
        private DeleteMedicAsynckTask(MedicDAO medicDAO){
            this.medicDAO = medicDAO;
        }

        @Override
        protected Void doInBackground(MedicData... medicData) {
            medicDAO.delete(medicData[0]);
            return null;
        }
    }


}
