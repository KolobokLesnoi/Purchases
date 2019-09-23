package koloboklesnoi.purchases.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Purchase {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String description;
    public String imageURI;
}
