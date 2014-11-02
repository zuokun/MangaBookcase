package zuokun.mangabookcase.storage;

import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Storage {

    public static Manga[] loadManga() {

        Manga[] manga = null;

        return manga;

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