package zuokun.mangabookcase.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/7/2014.
 */
public class MangaSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MangaDB";

    private static final String TABLE_MANGA = "mangas";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_START_BOOK_NUMBER = "firstBook";
    private static final String KEY_END_BOOK_NUMBER = "lastBook";
    private static final String KEY_STATUS = "status";

    private static final String[] COLUMNS = { KEY_ID,KEY_TITLE, KEY_START_BOOK_NUMBER, KEY_END_BOOK_NUMBER, KEY };

    public MangaSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MANGA_TABLE = "CREATE TABLE mangas ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "startBookNumber INTEGER, " +
                "endBookNumber INTEGER, " +
                "status INTEGER )";

        db.execSQL(CREATE_MANGA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXIST mangas");

        this.onCreate(db);
    }

    public void addManga(Manga manga) {
        Log.d("addManga", manga.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, manga.getTitle());
        values.put(KEY_START_BOOK_NUMBER, manga.getStartBookNumber());
        values.put(KEY_END_BOOK_NUMBER, manga.getEndBookNumber());
        values.put(KEY_STATUS, manga.getStatus()); //TODO


        db.insert(TABLE_MANGA,
                null,
                values);

    }

    public Manga getManga(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_MANGA,
                        COLUMNS,
                        " id =",
                        new String[] { String.valueOf(id) },
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Manga manga = new Manga();
        manga.setId(Integer.parseInt(cursor.getString(0)));
        manga.setTitle(cursor.getString(1));
        manga.setAuthor(cursor.getString(2));

        Log.d("getManga(" + id + ")", manga.toString());

        return manga;

    }

    public List<Manga> getAllMangas() {

        List<Manga> manga = new LinkedList<Manga>();

        String query = "SELECT * FROM " + TABLE_MANGA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Manga manga = null;

        if (cursor.moveToFirst()) {
            do {
                manga = new Manga();
                manga.setID(Integer.parseInt)
            }
        }

    }

}
