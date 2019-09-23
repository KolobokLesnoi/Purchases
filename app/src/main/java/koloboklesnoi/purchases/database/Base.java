package koloboklesnoi.purchases.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Base {

    @Query("SELECT * FROM purchase")
    List<Purchase> getAll();

    @Insert
    void insert(Purchase purchase);

    @Insert
    void insertAll(List<Purchase> purchaseList);

    @Delete
    void deleteAll(List<Purchase> purchaseList);
}
