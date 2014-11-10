package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaBookcaseEventListener;
import zuokun.mangabookcase.util.MangaExpandableListAdapter;


public class MainActivity extends Activity {

    SharedPreferences pref;
    static Logic logic;
    MangaExpandableListAdapter mangaListAdapter;
    ExpandableListView mangaListView;

    static ArrayList<MangaBookcaseEventListener> listeners = new ArrayList<MangaBookcaseEventListener>();

    static boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preloadContent();
        updateView();

        mangaListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                */
                return false;
            }
        });

    }

    private void preloadContent() {
        logic = new Logic();
        loadOrInitiatePreference();

        if (firstStart) {
            Toast.makeText(this, "First time", Toast.LENGTH_SHORT).show();
            logic.prepareFirstTimeUse();
            logic.prepareSampleData();
            firstStart = false;
            savePreference();
        } else {
            logic.prepareListData();
            Toast.makeText(this, "Not first time", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateView() {
            mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
            mangaListAdapter = new MangaExpandableListAdapter(this, logic.listManga);
            mangaListView.setAdapter(mangaListAdapter);
    }

    /********************
     *     Options
     *******************/

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
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.mainMenuAddManga:
                Intent addIntent = new Intent(this, AddActivity.class);
                startActivity(addIntent);
                return true;

            case R.id.action_dev_settings:
                Intent devIntent = new Intent(this, DeveloperSettingsActivity.class);
                startActivity(devIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /*******************
     * Event Handlers
     ******************/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static void addListener(MangaBookcaseEventListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(MangaBookcaseEventListener listener) {
        listeners.remove(listener);
    }

    public static void onEvent() {
        for (MangaBookcaseEventListener m : listeners) {
            m.handleEvent();
        }
    }

    // Methods

    public boolean parse(Constants.Commands command, Manga manga, Context context) throws IOException {

        Logic.parseCommand(command, manga, context);
        updateView();
        return true;

    }

    /*************************
     *      Preferences
     ************************/

    public void savePreference() {
        Editor editor = pref.edit();
        editor.putBoolean(Constants.FIRST_START, firstStart);
        editor.apply();
    }

    public void loadOrInitiatePreference() {
        pref = getSharedPreferences(Constants.FILE_CONFIG, Context.MODE_PRIVATE);
        if (pref == null) {
            initiatePreference();
        } else {
            if (pref.contains(Constants.FIRST_START)) {
                firstStart = pref.getBoolean(Constants.FIRST_START, false);
            }
        }
    }

    private void initiatePreference() {
        PreferenceManager.setDefaultValues(MainActivity.this, R.xml.preference, false);
        pref = getSharedPreferences(Constants.FILE_CONFIG, Context.MODE_PRIVATE);
    }

    /************************
     *      onClicks
     ***********************/

    public void addOneMangaBookBehind(View view) {
        Toast.makeText(getApplicationContext(), "Button Worked", Toast.LENGTH_SHORT).show();
    }

}