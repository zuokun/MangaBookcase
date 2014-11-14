package zuokun.mangabookcase.ui;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.adapter.MangaTabsPagerAdapter;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaBookcaseEventListener;
import zuokun.mangabookcase.adapter.MangaExpandableListAdapter;


public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, ActionBar.TabListener {

    SharedPreferences pref;
    static Logic sLogic;
    static MangaExpandableListAdapter mangaListAdapter;
    static ExpandableListView mangaListView;

    static ArrayList<MangaBookcaseEventListener> listeners = new ArrayList<MangaBookcaseEventListener>();

    static boolean firstStart = true;
    static boolean test = false;
    static String sQuery = "";
    // Tabs

    private static ViewPager tabsViewPager;
    private MangaTabsPagerAdapter mangaTabsPagerAdapter;
    private ActionBar tabsActionBar;
    private static final String[] TABS = { "All Manga", "Ongoing", "Completed", "Favourites", "Missing"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preloadContent();
        createOriginalExpandableList();
        loadTabs();
        updateListInTabs();

    }

    private void preloadContent() {
        sLogic = new Logic();
        loadOrInitiatePreference();

        if (firstStart) {
            prepareFirstTimeUse();
        } else {
            prepareListData();
        }
    }

    private void loadTabs() {

        tabsViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        tabsActionBar = getActionBar();
        tabsActionBar.setHomeButtonEnabled(false);
        tabsActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tabs : TABS) {
            tabsActionBar.addTab(tabsActionBar.newTab().setText(tabs).setTabListener(this));
        }

        tabsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                tabsActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void updateListInTabs() {
        mangaTabsPagerAdapter = new MangaTabsPagerAdapter(getSupportFragmentManager());
        tabsViewPager.setAdapter(mangaTabsPagerAdapter);
    }

    private void loadSearchService(Menu menu) {
        SearchManager mangaSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView mainSearchView = (SearchView) menu.findItem(R.id.mainMenuSearch).getActionView();
        mainSearchView.setSearchableInfo(mangaSearchManager.getSearchableInfo(getComponentName()));
        mainSearchView.setFocusable(false);
        mainSearchView.setIconified(false);
        mainSearchView.setOnQueryTextListener(this);
        mainSearchView.setOnCloseListener(this);
    }

    public void createOriginalExpandableList() {
            mangaListView = (ExpandableListView) findViewById(R.id.mangaExpListView);
            mangaListAdapter = new MangaExpandableListAdapter(this, Logic.listManga);
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
        mangaListAdapter.filterManga("");
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

    public static boolean parse(Constants.Commands command, Manga mManga, Context context) throws IOException {
        sLogic.parseCommand(command, mManga, context);
        updateExpandableList();
        return true;
    }

    private static void updateExpandableList() {
        mangaListAdapter = new MangaExpandableListAdapter(MangaBookcaseApp.getContext(), Logic.listManga);
        tabsViewPager.getAdapter().notifyDataSetChanged();
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

    private void prepareSampleData() {
        sLogic.prepareSampleData();
    }

    private void prepareListData() {
        sLogic.prepareListData();
    }

    @Override
    public boolean onClose() {
        mangaListAdapter.filterManga("");
        tabsViewPager.getAdapter().notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mangaListAdapter.setQuery(query);
        tabsViewPager.getAdapter().notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mangaListAdapter.setQuery(query);
        tabsViewPager.getAdapter().notifyDataSetChanged();
        return false;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        tabsViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public MangaExpandableListAdapter getMangaAdapterForFragment() {
        return mangaListAdapter;
    }

    public ExpandableListView getMangaListViewForFragment() {
        return mangaListView;
    }

}