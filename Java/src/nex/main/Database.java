package nex.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsomani on 3/28/2016.
 */
public class Database implements Serializable {
    List database = new ArrayList<Item>();


    public void addItem(Item item) {
        database.add(item);
    }
    public void delItem(Item item) {
        database.remove(item);
    }


    @Override
    public String toString() {
        return "Database{" +
                "database=" + database +
                '}';
    }
}
