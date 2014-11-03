package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.io.IOException;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    static Logic logic = new Logic();

    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set main.xml as user interface layout
        setContentView(R.layout.activity_main);

        try {
            logic.parseCommand(Constants.Commands.LOAD, null, getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        // get the listview
        mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
        mangaListAdapter = new MangaExpandableListAdapter(this, logic.listDataHeader, logic.listDataChild);
        mangaListView.setAdapter(mangaListAdapter);
*/

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

    public static boolean parse(Constants.Commands cmd, Manga m, Context context) throws IOException {

        logic.parseCommand(cmd, m, context);
        logic.updateExpendableList();

        return true;

    }

}