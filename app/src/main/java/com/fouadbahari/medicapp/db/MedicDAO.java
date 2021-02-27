package com.fouadbahari.medicapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import static androidx.room.OnConflictStrategy.REPLACE;

import java.util.List;

@Dao
public interface MedicDAO {

    @Insert(onConflict = REPLACE)
    void insert(MedicData medicData);

    @Delete
    void delete(MedicData medicData);

    @Query("SELECT * FROM medic_table")
    List<MedicData> getAll();

    @Query("SELECT * FROM medic_table WHERE code_barre = :code")
    MedicData findMedicByCode(String code);
}
