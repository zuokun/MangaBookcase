package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView.*;
import android.widget.*;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.storage.Storage;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaAdapter;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    //Get manga from storage
    Manga[] manga = Storage.loadManga();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set main.xml as user interface layout
        setContentView(R.layout.activity_main);

        //Test for list view
        if (false) {

            Manga One_Piece = new Manga("One Piece");
            Manga Gintama = new Manga("Gintama");
            Manga No_Game_No_Life = new Manga("No Game No Life");
            Manga Google = new Manga("Google");

            Manga[] mangas = new Manga[]{One_Piece, Gintama, No_Game_No_Life, Google};

            MangaAdapter mangaAdapter = new MangaAdapter(this, mangas);

            ListView listView = (ListView) findViewById(R.id.mangaList1);
            listView.setAdapter(mangaAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> l, View v, int position, long id) {

                    Manga m = (Manga) l.getItemAtPosition(position);
                    String s = m.getTitle();
                    Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });

        }

        //Test for expandable list view
        if (true) {

            // get the listview
            mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);

            // preparing list data
            prepareListData();

            mangaListAdapter = new MangaExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            mangaListView.setAdapter(mangaListAdapter);

            // Listview Group click listener
            mangaListView.setOnGroupClickListener(new OnGroupClickListener() {

                @Override
                public boolean onGroupClick(ExpandableListView parent, View v,
                                            int groupPosition, long id) {
                    // Toast.makeText(getApplicationContext(),
                    // "Group Clicked " + listDataHeader.get(groupPosition),
                    // Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            // Listview Group expanded listener
            mangaListView.setOnGroupExpandListener(new OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Expanded",
                            Toast.LENGTH_SHORT).show();
                }
            });

            // Listview Group collasped listener
            mangaListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            listDataHeader.get(groupPosition) + " Collapsed",
                            Toast.LENGTH_SHORT).show();

                }
            });

            // Listview on child click listener
            mangaListView.setOnChildClickListener(new OnChildClickListener() {

                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    // TODO Auto-generated method stub
                    Toast.makeText(
                            getApplicationContext(),
                            listDataHeader.get(groupPosition)
                                    + " : "
                                    + listDataChild.get(
                                    listDataHeader.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT)
                            .show();
                    return false;
                }
            });

        }

    }

        private void prepareListData () {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            listDataHeader.add("Top 250");
            listDataHeader.add("Now Showing");
            listDataHeader.add("Coming Soon..");

            // Adding child data
            List<String> top250 = new ArrayList<String>();
            top250.add("The Shawshank Redemption");
            top250.add("The Godfather");
            top250.add("The Godfather: Part II");
            top250.add("Pulp Fiction");
            top250.add("The Good, the Bad and the Ugly");
            top250.add("The Dark Knight");
            top250.add("12 Angry Men");

            List<String> nowShowing = new ArrayList<String>();
            nowShowing.add("The Conjuring");
            nowShowing.add("Despicable Me 2");
            nowShowing.add("Turbo");
            nowShowing.add("Grown Ups 2");
            nowShowing.add("Red 2");
            nowShowing.add("The Wolverine");

            List<String> comingSoon = new ArrayList<String>();
            comingSoon.add("2 Guns");
            comingSoon.add("The Smurfs 2");
            comingSoon.add("The Spectacular Now");
            comingSoon.add("The Canyons");
            comingSoon.add("Europa Report");

            listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
            listDataChild.put(listDataHeader.get(1), nowShowing);
            listDataChild.put(listDataHeader.get(2), comingSoon);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}