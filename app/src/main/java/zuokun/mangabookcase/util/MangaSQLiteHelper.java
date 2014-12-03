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
    private static final String KEY_IMAGE_PATH = "image";
    private static final String KEY_LAST = "last";
    private static final String KEY_MISSING = "missing";
    private static final String KEY_STATUS = "status";
    private static final String KEY_FAVOURITE = "favourite";


    private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_PUBLISHER, KEY_IMAGE_PATH, KEY_LAST, KEY_MISSING, KEY_STATUS, KEY_FAVOURITE };

    public MangaSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MANGA_TABLE = "CREATE TABLE mangaTable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "publisher TEXT," +
                "image TEXT," +
                "last INTEGER," +
                "missing TEXT," +
                "status INTEGER," +
                "favourite INTEGER)";

        db.execSQL(CREATE_MANGA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS mangaTable");
        this.onCreate(db);
    }

    public void addManga(Manga mManga) {
        Log.d("addManga", mManga.toString());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, mManga.getTitle());
        values.put(KEY_PUBLISHER, mManga.getPublisher());
        values.put(KEY_IMAGE_PATH, mManga.getImagePath());
        values.put(KEY_LAST, mManga.getLastBookNumber());
        values.put(KEY_MISSING, mManga.getStringMissingBooks());
        values.put(KEY_STATUS, mManga.getIntStatus());
        values.put(KEY_FAVOURITE, mManga.getIntFavourite());

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

        Manga mManga = new Manga();

        mManga.setId(Integer.parseInt(cursor.getString(0)));
        mManga.setTitle(cursor.getString(1));
        mManga.setPublisher(cursor.getString(2));
        mManga.setImagePath(cursor.getString(3));
        mManga.setLastBookNumber(Integer.parseInt(cursor.getString(4)));
        mManga.setStringMissingBooks(cursor.getString(5));
        mManga.setIntStatus(Integer.parseInt(cursor.getString(6)));
        mManga.setIntFavourite(Integer.parseInt(cursor.getString(7)));

        Log.d("getManga(" + id + ")", mManga.toString());

        cursor.close();
        db.close();

        return mManga;

    }

    public List<Manga> getAllMangas() {

        List<Manga> mangas = new LinkedList<Manga>();

        String query = "SELECT * FROM " + TABLE_MANGA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Manga mManga = null;

        if (cursor.moveToFirst()) {
            do {
                mManga = new Manga();
                mManga.setId(Integer.parseInt(cursor.getString(0)));
                mManga.setTitle(cursor.getString(1));
                mManga.setPublisher(cursor.getString(2));
                mManga.setImagePath(cursor.getString(3));
                mManga.setLastBookNumber(Integer.parseInt(cursor.getString(4)));
                mManga.setStringMissingBooks(cursor.getString(5));
                mManga.setIntStatus(Integer.parseInt(cursor.getString(6)));
                mManga.setIntFavourite(Integer.parseInt(cursor.getString(7)));

                mangas.add(mManga);

            } while (cursor.moveToNext());
        }

        Log.d("getAllMangas()", mangas.toString());

        cursor.close();
        db.close();

        return mangas;
    }

    public int updateManga(Manga mManga) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, mManga.getTitle());
        values.put(KEY_PUBLISHER, mManga.getPublisher());
        values.put(KEY_IMAGE_PATH, mManga.getImagePath());
        values.put(KEY_LAST, mManga.getLastBookNumber());
        values.put(KEY_MISSING, mManga.getStringMissingBooks());
        values.put(KEY_STATUS, mManga.getIntStatus());
        values.put(KEY_FAVOURITE, mManga.getIntFavourite());

        int i = db.update(TABLE_MANGA,
                values,
                KEY_ID + " = ?",
                new String[] { String.valueOf(mManga.getId()) });

        db.close();

        return i;

    }

    public void deleteManga(Manga mManga) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MANGA,
                KEY_ID + " = ?",
                new String[] { String.valueOf(mManga.getId()) });

        db.close();

        Log.d("deleteManga", mManga.toString());

    }

}
