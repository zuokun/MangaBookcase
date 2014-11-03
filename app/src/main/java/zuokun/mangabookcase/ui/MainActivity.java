package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.database.Cursor;

import java.io.IOException;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    SharedPreferences pref;
    static Logic logic = new Logic();
    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;
    //SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        logic.setContext(this);
        loadPreference(getBaseContext());

        //db = openOrCreateDatabase("MangaDB", Context.MODE_PRIVATE, null);
        //db.execSQL("CREATE TABLE IF NOT EXISTS student(mangano VARCHAR,title VARCHAR,lastbook VARCHAR);");

        if (logic.firstStart) {

            Toast.makeText(this, "First time", Toast.LENGTH_SHORT).show();
            logic.prepareFirstTimeUse();
            logic.prepareListData();

        } else {
            Toast.makeText(this, "Not first time", Toast.LENGTH_SHORT).show();

            try {
                logic.parseCommand(Constants.Commands.LOAD, null, getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (logic.listManga == null || logic.listManga.isEmpty()) {
                Toast.makeText(this, "No Manga", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, logic.listManga.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        }

        logic.prepareListData();

        // get the listview
        mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
        mangaListAdapter = new MangaExpandableListAdapter(this, logic.listDataHeader, logic.listDataChild);
        mangaListView.setAdapter(mangaListAdapter);

        logic.firstStart = false;
        savePreference();

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

    @Override
    protected void onPause() {
        super.onPause();
        try {
            logic.parseCommand(Constants.Commands.SAVE, null, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        logic.listManga.clear();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            logic.parseCommand(Constants.Commands.SAVE, null, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean parse(Constants.Commands cmd, Manga m, Context context) throws IOException {

        String feedback = Logic.parseCommand(cmd, m, context);
        logic.updateExpendableList();
        //showToast(feedback);
        return true;

    }

    public void savePreference() {

        Editor editor = pref.edit();
        editor.putBoolean(Constants.FIRST_START, logic.firstStart);

        editor.commit();

    }

    public void loadPreference(Context baseContext) {

        if (pref.contains(Constants.FIRST_START)) {
            logic.firstStart = pref.getBoolean(Constants.FIRST_START, false);
        }

    }

}