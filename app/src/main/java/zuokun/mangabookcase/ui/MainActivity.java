package zuokun.mangabookcase.ui;

import android.support.v4.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.adapter.AutoCompleteSearchView;
import zuokun.mangabookcase.adapter.SlidingTabsFragment;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.adapter.MangaExpandableListAdapter;


public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    SharedPreferences pref;
    static MangaExpandableListAdapter mangaListAdapter;
    static ExpandableListView mangaListView;

    static boolean firstStart = true;
    static boolean test = false;
    static String[] autoCompleteMangas;

    // Tabs

    static SlidingTabsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupFragments(savedInstanceState);
        preloadContent();
        createOriginalExpandableList();
        //updateAutoComplete();

    }

    private void setupFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment = new SlidingTabsFragment();
            transaction.replace(R.id.tabs_fragment, fragment);
            transaction.commit();
        }
    }

    /*
    TODO add autocomplete for manga names
    private void updateAutoComplete() {
        final AutoCompleteTextView mangaAutoComplete = (AutoCompleteTextView) findViewById(R.id.mainAutoCompleteManga);
        autoCompleteMangas = new String[mangaListAdapter.getMangaList().size()];

        for (int iManga = 0; iManga < mangaListAdapter.getMangaList().size(); iManga++) {
                    autoCompleteMangas[iManga] = mangaListAdapter.getMangaList().get(iManga).getTitle();
            }
        ArrayAdapter<String> autocompleteAdapter = new ArrayAdapter<String>(this, R.layout.autocomplete_manga_list_item, autoCompleteMangas);
        mangaAutoComplete.setAdapter(autocompleteAdapter);
    }
*/
    private void preloadContent() {
        loadOrInitiatePreference();

        if (firstStart) {
            prepareFirstTimeUse();
        }
    }

    private void loadSearchService(Menu menu) {
        SearchManager mangaSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mainSearchView = (SearchView) menu.findItem(R.id.mainMenuSearch).getActionView();
        mainSearchView.setSearchableInfo(mangaSearchManager.getSearchableInfo(getComponentName()));
        mainSearchView.setFocusable(false);
        mainSearchView.setIconified(true);
        mainSearchView.setOnQueryTextListener(this);
        mainSearchView.setOnCloseListener(this);
    }

    public void createOriginalExpandableList() {
            mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
            mangaListAdapter = new MangaExpandableListAdapter(this, Logic.getListManga());
            mangaListView.setAdapter(mangaListAdapter);
    }

    /********************
     *     Options
     *******************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

        loadSearchService(menu);

        MenuItem searchItem = menu.findItem(R.id.mainMenuSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        } else {
            Toast.makeText(this, "No View", Toast.LENGTH_SHORT).show();
        }

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
        mangaListAdapter.filterManga("");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // Methods

    public static boolean parse(Constants.Commands command, Manga mManga, Context context) throws IOException {
        Logic.parseCommand(command, mManga, context);
        updateExpandableList();
        updateFragments();
        return true;
    }

    private static void updateExpandableList() {
        mangaListAdapter.updateList(Logic.getListManga());

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

    /************************
     *     Other Methods    *
     ***********************/

    public void prepareFirstTimeUse() {
        prepareSampleData();
        disableFirstStart();
        savePreference();
    }

    private void disableFirstStart() {
        firstStart = false;
    }

    private void prepareSampleData() { Logic.prepareSampleData(); }

    @Override
    public boolean onClose() {
        mangaListAdapter.filterManga("");
        updateFragments();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mangaListAdapter.setQuery(query);
        updateFragments();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mangaListAdapter.setQuery(query);
        updateFragments();
        return false;
    }

    public MangaExpandableListAdapter getMangaAdapterForFragment() {
        return mangaListAdapter;
    }

    public ExpandableListView getMangaListViewForFragment() {
        return mangaListView;
    }

    private static void updateFragments() {
        fragment.getViewPager().getAdapter().notifyDataSetChanged();
    }

}