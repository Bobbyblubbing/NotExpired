package nex.main;

import java.io.Serializable;

/**
 * Created by jsomani on 3/28/2016.
 */
public class Item implements Serializable {
    int daysToExpire;
    String name;

    public Item(int daysToExpire, String name) {
        this.daysToExpire = daysToExpire;
        this.name = name;
    }

    public int getDaysToExpire() {
        return daysToExpire;
    }

    public void setDaysToExpire(int daysToExpire) {
        this.daysToExpire = daysToExpire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item: " + "'" + name + "'";
    }
}
