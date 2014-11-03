package zuokun.mangabookcase.logic;

import android.content.Context;
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

    public Logic () {};

    Storage storage;

    public List<String> listDataHeader;
    public HashMap<String, List<String>> listDataChild;

    //Get manga from storage
    public List<Manga> listManga;
    //listManga = Storage.loadManga();

    boolean debug = true;

    public String parseCommand(Constants.Commands command, Manga manga, Context context) throws IOException {

        switch (command) {
            case ADD:
                add(manga);
                break;

            case EDIT:
                edit(manga);
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

    private void add(Manga manga) {

        listManga.add(manga);

    }
    private static void edit(Manga manga) {

    }

    private static void load(Context context) throws IOException {
        Storage.loadFile(context);
    }

    private void save(Context context) {
        Storage.saveFile(listManga, context);
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

        }

    public void updateExpendableList() {

        if (!listManga.isEmpty()) {

            listDataHeader.clear();
            listDataChild.clear();
            updateParentData();
            updateChildData();
        }
    }

    private void updateParentData() {
        for (int i = 0; i < listManga.size(); i++) {
            listDataHeader.add(listManga.get(i).getTitle());
        }
    }

    private void updateChildData() {
        for (int i = 0; i < listManga.size(); i++) {
            List<String> childData = new ArrayList<String>();

            Manga m = listManga.get(i);

            childData.add(Constants.LAST_BOOK + m.getEndBookNumber());
            childData.add(Constants.STATUS + m.getStatus());

            listDataChild.put(listDataHeader.get(i), childData);
        }
    }

}
