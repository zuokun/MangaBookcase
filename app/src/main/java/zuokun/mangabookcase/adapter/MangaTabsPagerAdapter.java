package zuokun.mangabookcase.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.List;

import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/12/2014.
 */
public class MangaTabsPagerAdapter extends FragmentPagerAdapter {

    public MangaTabsPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new AllMangaFragment();

            case 1:
                //Filter ongoing
                return new OngoingFragment();

            case 2:
                // Filter Completed
                return new CompletedFragment();

            case 3:
                //Filter Favourites
                return new FavouritesFragment();

            case 4:
                //Filter Mangas with missing books
                return new MissingBooksFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}