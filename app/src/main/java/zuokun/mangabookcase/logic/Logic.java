package zuokun.mangabookcase.logic;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public static String parseCommand(Constants.Commands command, Manga manga, Context context) {

        switch (command) {
            case ADD:
                add(manga, context);
                updateLogicList();
                break;

            case UPDATE:
                update(manga, context);
                updateLogicList();
                break;

            case DELETE:
                delete(manga, context);
                updateLogicList();
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

        // (title, last_book, publisher, status, favourite)
        Manga One_Piece = new Manga("One Piece", "Tong Li", 69, new int[]{1, 3, 9}, true, true);
        Manga Gintama = new Manga("Gintama", "Tong Li", 44, new int[]{}, true, false);
        Manga No_Game_No_Life = new Manga("No Game No Life", "Kadokawa", 12, new int[]{2, 9}, false, false);
        Manga Google = new Manga("Google", "Nexus", 33, new int[]{7, 9}, true, false);

        // Adding Manga
        m.addManga(One_Piece);
        m.addManga(Gintama);
        m.addManga(No_Game_No_Life);
        m.addManga(Google);

        updateLogicList();

    }

    public static void updateLogicList() {
        listManga = getSQLiteHelper().getAllMangas();
        sortAlphabetically(listManga);
    }

    private static void sortAlphabetically(List<Manga> listManga) {
        Collections.sort(listManga, new Comparator<Manga>() {
            @Override
            public int compare(Manga m1, Manga m2) {
                return m1.getTitle().compareToIgnoreCase(m2.getTitle());
            }
        });

    }

    public void prepareListData() {
        MangaSQLiteHelper m = getSQLiteHelper();
        listManga = m.getAllMangas();
        updateLogicList();
    }

    public static MangaSQLiteHelper getSQLiteHelper() {
        return new MangaSQLiteHelper(MangaBookcaseApp.getContext());
    }

}
