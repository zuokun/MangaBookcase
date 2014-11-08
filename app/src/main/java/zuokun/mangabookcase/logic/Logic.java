package zuokun.mangabookcase.logic;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.storage.MangaSQLiteHelper;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class Logic {

    public Logic(Context context) {
        db = new MangaSQLiteHelper(context);
    }

    static MangaSQLiteHelper db;
    public static List<Manga> listManga = new ArrayList<Manga>();
    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;

    public static final boolean debug = true;

    public static String parseCommand(Constants.Commands command, Manga manga, Context context) throws IOException {

        switch (command) {
            case ADD:
                add(manga, context);
                break;

            case UPDATE:
                update(manga, context);
                break;

            case DELETE:
                delete(manga, context);
                break;

            case SAVE:
                save(context);
                break;

            case LOAD:
                load(context);
                break;


            default:
                return "";
        }

        return "";

    }

    private static void add(Manga manga, Context context) {
        db.addManga(manga);
    }
    private static void update(Manga manga, Context context) {
        db.updateManga(manga);
    }

    public static void delete(Manga manga, Context context) {
        db.deleteManga(manga);
    }

    private static void load(Context context) throws IOException {

    }

    private static void save(Context context) {

    }



    public void prepareSampleData() {
            listManga = new ArrayList<Manga>();
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            Manga One_Piece = new Manga("One Piece", 69, true);
            Manga Gintama = new Manga("Gintama", 44, true);
            Manga No_Game_No_Life = new Manga("No Game No Life", 12, false);
            Manga Google = new Manga("Google", 33, true);

            //Adding Manga
            db.addManga(One_Piece);
            db.addManga(Gintama);
            db.addManga(No_Game_No_Life);
            db.addManga(Google);

        updateExpendableList();

        }

    public static void updateExpendableList() {

        listManga = db.getAllMangas();
        listDataHeader.clear();
        listDataChild.clear();
        updateParentData();
        updateChildData();
    }

    private static void updateParentData() {
        for (int i = 0; i < listManga.size(); i++) {
            listDataHeader.add(listManga.get(i).getTitle());
        }
    }

    private static void updateChildData() {
        for (int i = 0; i < listManga.size(); i++) {
            List<String> childData = new ArrayList<String>();

            Manga m = listManga.get(i);

            childData.add(Constants.LAST_BOOK + m.getLastBookNumber());
            childData.add(Constants.STATUS + m.getStatus());

            listDataChild.put(listDataHeader.get(i), childData);
        }
    }

    public void prepareFirstTimeUse() {

    }

    public void prepareListData() {

        listManga = db.getAllMangas();

    }
}
