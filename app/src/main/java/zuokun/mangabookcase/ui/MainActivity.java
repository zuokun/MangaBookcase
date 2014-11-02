package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    //Get manga from storage
    List<Manga> listManga;
    //listManga = Storage.loadManga();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set main.xml as user interface layout
        setContentView(R.layout.activity_main);

        // get the listview
        mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);

        // preparing list data
        prepareListData();

        mangaListAdapter = new MangaExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        mangaListView.setAdapter(mangaListAdapter);

        mangaListView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent = new Intent (MainActivity.this, EditActivity.class);
                startActivity(intent);

                return false;
            }
        });

    }

        private void prepareListData () {
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

            for (int i = 0; i < listManga.size(); i++) {
                listDataHeader.add(listManga.get(i).getTitle());
            }

            for (int i = 0; i < listManga.size(); i++) {
                List<String> childData = new ArrayList<String>();

                Manga m = listManga.get(i);

                childData.add(Constants.LAST_BOOK + m.getEndBookNumber());
                childData.add(Constants.STATUS + m.getStatus());

                listDataChild.put(listDataHeader.get(i), childData);
            }

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case R.id.action_settings:
                return true;

            case R.id.mainMenuAddManga:
                Intent intent = new Intent(this, AddActivity.class);
                startActivity(intent);
                return true;



        }

        return super.onOptionsItemSelected(item);
    }

}