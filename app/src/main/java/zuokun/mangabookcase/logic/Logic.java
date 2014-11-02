package zuokun.mangabookcase.logic;

import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.storage.Storage;
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
        updateParentData();
        updateChildData();
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
