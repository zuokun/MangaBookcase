package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    SharedPreferences pref;
    static Logic logic;
    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;

    boolean firstStart = true;

    boolean reset = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (reset) {

            this.deleteDatabase("MangaDB");

        } else {

            preloadContent();
            setView();
        }

    }

    private void preloadContent() {
        logic = new Logic(this);
        loadPreference();

        if (firstStart) {

            Toast.makeText(this, "First time", Toast.LENGTH_SHORT).show();
            // PreferenceManager.setDefaultValues(this, R.xml.preference, false);
            logic.prepareFirstTimeUse();
            logic.prepareSampleData();
            firstStart = false;
            savePreference();

        } else {

            logic.prepareListData();
            Toast.makeText(this, "Not first time", Toast.LENGTH_SHORT).show();
        }

    }

    private void setView() {

            mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
            mangaListAdapter = new MangaExpandableListAdapter(this, logic.listDataHeader, logic.listDataChild);
            mangaListView.setAdapter(mangaListAdapter);

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

    public static boolean parse(Constants.Commands command, Manga manga, Context context) throws IOException {

        Logic.parseCommand(command, manga, context);
        logic.updateExpendableList();
        return true;

    }

    public void savePreference() {

        Editor editor = pref.edit();
        editor.putBoolean(Constants.FIRST_START, firstStart);

        editor.commit();

    }

    public void loadPreference() {

        pref = getSharedPreferences(Constants.FILE_CONFIG, Context.MODE_PRIVATE);

        if (pref.contains(Constants.FIRST_START)) {
            firstStart = pref.getBoolean(Constants.FIRST_START, false);
        }

    }

}