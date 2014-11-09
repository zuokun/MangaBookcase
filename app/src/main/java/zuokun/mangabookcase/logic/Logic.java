package zuokun.mangabookcase.logic;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.util.MangaSQLiteHelper;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class Logic {

    public static List<Manga> listManga = new ArrayList<Manga>();
    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;

    public static final boolean debug = true;

    public static String parseCommand(Constants.Commands command, Manga manga, Context context) throws IOException {

        switch (command) {
            case ADD:
                add(manga, context);
                updateExpendableList();
                break;

            case UPDATE:
                update(manga, context);
                updateExpendableList();
                break;

            case DELETE:
                delete(manga, context);
                updateExpendableList();
                break;

            default:
                return "";
        }

        return "";

    }

    private static void add(Manga manga, Context context) {
        getSQLiteHelper().addManga(manga);
    }
    private static void update(Manga manga, Context context) {
        getSQLiteHelper().updateManga(manga);
    }

    public static void delete(Manga manga, Context context) {
       getSQLiteHelper().deleteManga(manga);
    }

    public void prepareSampleData() {
            listManga = new ArrayList<Manga>();
            MangaSQLiteHelper m = getSQLiteHelper();

            Manga One_Piece = new Manga("One Piece", 69, true);
            Manga Gintama = new Manga("Gintama", 44, true);
            Manga No_Game_No_Life = new Manga("No Game No Life", 12, false);
            Manga Google = new Manga("Google", 33, true);

            //Adding Manga
        m.addManga(One_Piece);
        m.addManga(Gintama);
        m.addManga(No_Game_No_Life);
        m.addManga(Google);

        updateExpendableList();

        }

    public static void updateExpendableList() {

        listManga = getSQLiteHelper().getAllMangas();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
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
            childData.add(Constants.STATUS + m.getStringStatus());

            listDataChild.put(listDataHeader.get(i), childData);
        }
    }

    public void prepareFirstTimeUse() {

    }

    public void prepareListData() {
        MangaSQLiteHelper m = getSQLiteHelper();
        listManga = m.getAllMangas();
        updateExpendableList();
    }

    private static MangaSQLiteHelper getSQLiteHelper() {
        return new MangaSQLiteHelper(MangaBookcaseApp.getContext());
    }

}
