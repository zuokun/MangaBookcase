package zuokun.mangabookcase.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.ui.EditActivity;
import zuokun.mangabookcase.ui.MainActivity;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/12/2014.
 */
public class OngoingFragment extends Fragment {

    List<Manga> _listManga;
    MangaExpandableListAdapter _mangaListAdapter;
    ExpandableListView _mangaListView;
    String sQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ongoing_manga_list, container, false);

        _mangaListAdapter = ((MainActivity) getActivity()).getMangaAdapterForFragment();
        _mangaListView = ((MainActivity) getActivity()).getMangaListViewForFragment();
        sQuery =((MainActivity) getActivity()).getQuery();
        _listManga = _mangaListAdapter.getDisplayList();

        _mangaListAdapter.filterOngoingManga(sQuery);

        _mangaListView = (ExpandableListView) rootView.findViewById(R.id.mangaExpListViewOngoing);
        _mangaListAdapter = new MangaExpandableListAdapter(MangaBookcaseApp.getContext(), _listManga);
        _mangaListView.setAdapter(_mangaListAdapter);

        _mangaListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Manga mManga = _mangaListAdapter.getDisplayList().get(i);
                Intent intent = new Intent(MangaBookcaseApp.getContext(), EditActivity.class);
                intent.putExtra("parcelManga", mManga);
                startActivity(intent);

                return false;
            }
        });

        return rootView;
    }

}
