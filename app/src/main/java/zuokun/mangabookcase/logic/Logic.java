package zuokun.mangabookcase.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.storage.Storage;
import zuokun.mangabookcase.ui.MainActivity;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class Logic {

    Context _context;
    Storage _storage;

    public static List<String> listDataHeader;
    public static HashMap<String, List<String>> listDataChild;

    //Get manga from storage
    public static List<Manga> listManga;

    public boolean firstStart = true;

    // SharedPreferences pref = PreferenceManager.setDefaultValues(_context, R.xml.preference, false);

    static boolean debug = true;

    public static String parseCommand(Constants.Commands command, Manga manga, Context context) throws IOException {

        switch (command) {
            case ADD:
                add(manga, context);
                break;

            case EDIT:
                edit(manga, context);
                break;

            case SAVE:
                save(context);
                break;

            case LOAD:
                load(context);
                break;

            case DELETE:
                break;
            default:
                return "";
        }

        if (debug) {
            return "Done";
        } else {
            return "";
        }
    }

    private static void add(Manga manga, Context context) {

        listManga.add(manga);
        Storage.writeToFile(listManga, context);
    }
    private static void edit(Manga manga, Context context) {

    }

    private static void load(Context context) throws IOException {
        Storage.loadFile(listManga, context);
    }

    private static void save(Context context) {
        Storage.writeToFile(listManga, context);
    }



    public void prepareListData() {
            listManga = new ArrayList<Manga>();
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            Manga One_Piece = new Manga("One Piece", 69, true);
            Manga Gintama = new Manga("Gintama", 44, true);
            Manga No_Game_No_Life = new Manga("No Game No Life", 12, false);
            Manga Google = new Manga("Google", 33, true);

            //Adding Manga
            listManga.add(One_Piece);
            listManga.add(Gintama);
            listManga.add(No_Game_No_Life);
            listManga.add(Google);

        updateExpendableList();

        }

    public static void updateExpendableList() {

        if (!listManga.isEmpty()) {

            listDataHeader.clear();
            listDataChild.clear();
            updateParentData();
            updateChildData();
        }
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

            childData.add(Constants.LAST_BOOK + m.getEndBookNumber());
            childData.add(Constants.STATUS + m.getStatus());

            listDataChild.put(listDataHeader.get(i), childData);
        }
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public void prepareFirstTimeUse() {

    }
}
