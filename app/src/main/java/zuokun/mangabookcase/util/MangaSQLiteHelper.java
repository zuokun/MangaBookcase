package zuokun.mangabookcase.util;

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

    private static final String TABLE_MANGA = "mangaTable";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_PUBLISHER = "publisher";
    private static final String KEY_LAST = "last";
    private static final String KEY_MISSING = "missing";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FAVOURITE = "favourite";


    private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_PUBLISHER, KEY_LAST, KEY_MISSING, KEY_STATUS, KEY_FAVOURITE };

    public MangaSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MANGA_TABLE = "CREATE TABLE mangaTable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "publisher TEXT" +
                "last INTEGER," +
                "missing TEXT" +
                "status INTEGER" +
                "favourite INTEGER)";

        db.execSQL(CREATE_MANGA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS mangaTable");
        this.onCreate(db);
    }

    public void addManga(Manga manga) {
        Log.d("addManga", manga.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, manga.getTitle());
        values.put(KEY_PUBLISHER, manga.getPublisher());
        values.put(KEY_LAST, manga.getLastBookNumber());
        values.put(KEY_MISSING, manga.getStringMissingBooks());
        values.put(KEY_STATUS, manga.getIntStatus());
        values.put(KEY_FAVOURITE, manga.getIntFavourite());





        db.insert(TABLE_MANGA,
                null,
                values);

        db.close();

    }

    public Manga getManga(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_MANGA,
                        COLUMNS,
                        "id=?",
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
        manga.setLastBookNumber(Integer.parseInt(cursor.getString(2)));
        manga.setIntStatus(Integer.parseInt(cursor.getString(3)));

        Log.d("getManga(" + id + ")", manga.toString());

        cursor.close();
        db.close();

        return manga;

    }

    public List<Manga> getAllMangas() {

        List<Manga> mangas = new LinkedList<Manga>();

        String query = "SELECT * FROM " + TABLE_MANGA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Manga manga = null;

        if (cursor.moveToFirst()) {
            do {
                manga = new Manga();
                manga.setId(Integer.parseInt(cursor.getString(0)));
                manga.setTitle(cursor.getString(1));
                manga.setPublisher(cursor.getString(2));
                manga.setLastBookNumber(Integer.parseInt(cursor.getString(3)));
                manga.setMissingBooks(cursor.getString(4));
                manga.setIntStatus(Integer.parseInt(cursor.getString(5)));
                manga.setIntFavourite(Integer.parseInt(cursor.getString(6)));

                mangas.add(manga);

            } while (cursor.moveToNext());
        }

        Log.d("getAllMangas()", mangas.toString());

        cursor.close();
        db.close();

        return mangas;
    }

    public int updateManga(Manga manga) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, manga.getTitle());
        values.put(KEY_PUBLISHER, manga.getPublisher());
        values.put(KEY_LAST, manga.getLastBookNumber());
        values.put(KEY_MISSING, manga.getStringMissingBooks());
        values.put(KEY_STATUS, manga.getIntStatus());
        values.put(KEY_FAVOURITE, manga.getIntFavourite());

        int i = db.update(TABLE_MANGA,
                values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(manga.getId()) });

        db.close();

        return i;

    }

    public void deleteManga(Manga manga) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MANGA,
                KEY_ID + " = ?",
                new String[] { String.valueOf(manga.getId()) });

        db.close();

        Log.d("deleteManga", manga.toString());

    }

}
