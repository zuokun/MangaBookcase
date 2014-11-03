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
import java.util.List;

import zuokun.mangabookcase.ui.MainActivity;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Storage {

    static String filename = "manga_list";
    static File file = new File(filename);

    public static void saveFile(List<Manga> mangaList, Context context) {

        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mangaList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Manga> loadFile(Context context) throws IOException {

        File file2 = new File(context.getFilesDir(), filename);

        if (isEmptyFile()) {
            file2.createNewFile();
            return null;
        } else {
            FileInputStream fis;
            try {
                fis = context.openFileInput(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                List<Manga> mangaList = (List<Manga>) ois.readObject();
                ois.close();
                return mangaList;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static Manga[] loadManga() {

        Manga[] manga = null;

        return manga;

    }

    private static boolean isEmptyFile() {
        BufferedReader br;

        try {

            br = new BufferedReader(new FileReader(filename));

            if (br.readLine() == null) {
                br.close();

                return true;
            }

            br.close();

            return false;
        } catch (FileNotFoundException e) {
            //Toast.makeText(MainActivity.this, "File Not Found", Toast.LENGTH_SHORT).show();
        }catch (IOException e) {

        }

        return false;
    }

}


/*

Save a File on Internal Storage
When saving a file to internal storage, you can acquire the appropriate directory as a File by calling one of two methods:

getFilesDir()
Returns a File representing an internal directory for your app.
getCacheDir()
Returns a File representing an internal directory for your app's temporary cache files. Be sure to delete each file once it is no longer needed and implement a reasonable size limit for the amount of memory you use at any given time, such as 1MB. If the system begins running low on storage, it may delete your cache files without warning.
To create a new file in one of these directories, you can use the File() constructor, passing the File provided by one of the above methods that specifies your internal storage directory. For example:

File file = new File(context.getFilesDir(), filename);
Alternatively, you can call openFileOutput() to get a FileOutputStream that writes to a file in your internal directory. For example, here's how to write some text to a file:

String filename = "myfile";
String string = "Hello world!";
FileOutputStream outputStream;

try {
  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
  outputStream.write(string.getBytes());
  outputStream.close();
} catch (Exception e) {
  e.printStackTrace();
}
Or, if you need to cache some files, you should instead use createTempFile(). For example, the following method extracts the file name from a URL and creates a file with that name in your app's internal cache directory:

public File getTempFile(Context context, String url) {
    File file;
    try {
        String fileName = Uri.parse(url).getLastPathSegment();
        file = File.createTempFile(fileName, null, context.getCacheDir());
    catch (IOException e) {
        // Error while creating file
    }
    return file;
}
 */