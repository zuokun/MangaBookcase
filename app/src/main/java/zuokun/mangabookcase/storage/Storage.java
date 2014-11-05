package zuokun.mangabookcase.storage;

import android.content.Context;
import android.widget.Toast;

import com.thoughtworks.xstream.XStream;

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
    static XStream xstream = new XStream();

    public static void writeToFile(List<Manga> ml, Context context) {

        File myfile = new File(context.getFilesDir(), filename);

        try {
            if (myfile.exists() || myfile.createNewFile()) {

                xstream.alias("Manga List", List.class);
                xstream.alias("Manga", Manga.class);
                String xml = xstream.toXML(ml);

                FileOutputStream fos = new FileOutputStream(myfile);
                fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
                byte[] bytes = xml.getBytes("UTF-8");
                Toast.makeText(context, "Writing: " +  ml.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                fos.write(bytes);
                Toast.makeText(context, "Written " +  ml.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                fos.close();

                try {
                    ml = (List<Manga>) xstream.fromXML(myfile);
                    Toast.makeText(context, "Loading: " + ml.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Manga> loadFile(List<Manga> ml, Context context) throws IOException {

        FileInputStream fis;
        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show();
        try {
            fis = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ml = (ArrayList<Manga>) ois.readObject();
            Toast.makeText(context, "Loading: " + ml.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ml;
    }

    public static void deleteFile(Context context) {

        File file = new File(context.getFilesDir(), filename);
        file.delete();

    }

}
