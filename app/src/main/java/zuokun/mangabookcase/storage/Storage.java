package zuokun.mangabookcase.storage;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import zuokun.mangabookcase.ui.MainActivity;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Storage {

    static String filename = "manga_list";

    public static void writeToFile(List<Manga> ml, Context context) {

        File myfile = context.getFileStreamPath(filename);
        try {

            if (myfile.exists() || myfile.createNewFile()) {
                FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(ml);
                oos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Manga> loadFile(List<Manga> ml, Context context) throws IOException {

            try {
                FileInputStream fis = context.openFileInput(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                ml = (List<Manga>) ois.readObject();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return ml;
    }

}
