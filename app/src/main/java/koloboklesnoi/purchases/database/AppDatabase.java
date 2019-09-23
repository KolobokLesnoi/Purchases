package koloboklesnoi.purchases.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Purchase.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public abstract Base getBase();
}
