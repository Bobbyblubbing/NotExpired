package nex.util;

import nex.main.Database;

import java.io.*;

/**
 * Created by jsomani on 3/28/2016.
 */
public class io {
    public static void saveDatabase(String path, Database d) {
        try(FileOutputStream fs = new FileOutputStream(path)) {

            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(d);

            os.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Database readDatabase(String path) {
        Database dbOut = new Database();
        try(FileInputStream fi = new FileInputStream(path)) {

            ObjectInputStream os = new ObjectInputStream(fi);

            dbOut = (Database)os.readObject();

            os.close();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dbOut;
    }
}
