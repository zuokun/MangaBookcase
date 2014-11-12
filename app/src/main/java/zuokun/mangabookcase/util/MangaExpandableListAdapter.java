package zuokun.mangabookcase.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.ui.MainActivity;

/**
 * Created by ZeitiaX on 10/27/2014.
 */
public class MangaExpandableListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;

    private Context _context;
    private List<Manga> _mangaList = new ArrayList<Manga>();
    private List<Manga> _displayList = new ArrayList<Manga>();
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public MangaExpandableListAdapter(Context context, List<Manga> mangaList) {
        this._context = context;
        this._mangaList.addAll(mangaList);
        this._displayList.addAll(mangaList);

        _listDataHeader = new ArrayList<String>();
        _listDataChild = new HashMap<String, List<String>>();

        updateData();

    }

    /**
     * ************
     * Manga List
     * ************
     */

    public List<Manga> getMangaList() {
        return _mangaList;
    }

    /**
     * ************
     * Update
     * ************
     */

    public static void updateParentData(List<Manga> listManga, List<String> listDataHeader) {
        for (Manga mManga : listManga) {
            listDataHeader.add(mManga.getTitle());
        }
    }

    public static void updateChildData(List<Manga> listManga, List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {
        for (int i = 0; i < listManga.size(); i++) {
            List<String> childData = new ArrayList<String>();

            Manga mManga = listManga.get(i);

            addDataToChild(childData, mManga);
            listDataChild.put(listDataHeader.get(i), childData);
        }
    }

    private static void addDataToChild(List<String> childData, Manga mManga) {
        addMissingBooksToChild(childData, mManga);
        addPublisherToChild(childData, mManga);
        addStatusToChild(childData, mManga);
    }

    private static void addStatusToChild(List<String> childData, Manga mManga) {
        childData.add(Constants.STATUS + mManga.getStringStatus());
    }

    private static void addPublisherToChild(List<String> childData, Manga mManga) {
        if (!mManga.getPublisher().equals(Constants.EMPTY_STRING)) {
            childData.add(Constants.PUBLISHER + mManga.getPublisher());
        }
    }

    private static void addMissingBooksToChild(List<String> childData, Manga mManga) {
        if (mManga.getMissingBooks().length == 1) {
            childData.add(Constants.MISSING_BOOK + mManga.getStringDisplayMissingBooks());
        } else if (mManga.getMissingBooks().length > 1) {
            childData.add(Constants.MISSING_BOOKS + mManga.getStringDisplayMissingBooks());
        } else {
            // Do Nothing;
        }

    }

    /****************
     *    Child     *
     ***************/

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.manga_list_items, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    /**
     * **************
     * Group     *
     * **************
     */

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        String headerMangaNumber = Integer.toString(_displayList.get(groupPosition).getLastBookNumber());

        if (convertView == null) {
            inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.manga_list, null);

        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        TextView lblListHeaderMangaLastBook = (TextView) convertView
                .findViewById(R.id.lblListHeaderMangaLastBook);
        ImageView addImage = (ImageView) convertView.findViewById(R.id.lblListHeaderAddBtn);

        final View finalConvertView = convertView;
        addImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Manga mManga = _displayList.get(groupPosition);
                mManga.addOneBookBehind();
                Logic.parseCommand(Constants.Commands.UPDATE, mManga, MangaBookcaseApp.getContext());

                TextView lblUpdatedListHeaderMangaLastBook = (TextView) finalConvertView
                        .findViewById(R.id.lblListHeaderMangaLastBook);
                String headerUpdatedMangaNumber = Integer.toString(_displayList.get(groupPosition).getLastBookNumber());
                lblUpdatedListHeaderMangaLastBook.setText(headerUpdatedMangaNumber);
            }

        });

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        lblListHeaderMangaLastBook.setText(headerMangaNumber);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){

        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(_mangaList.size()));
        _displayList.clear();

        if (query.isEmpty()){
            _displayList.addAll(_mangaList);
        } else {
            for (Manga mManga : _mangaList) {
                if (mManga.getTitle().toLowerCase().contains(query)) {
                    _displayList.add(mManga);
                }
            }
        }

        updateData();

        Log.v("MyListAdapter", String.valueOf(_mangaList.size()));
        notifyDataSetChanged();
    }

    private void updateData() {
        _listDataHeader.clear();
        _listDataChild.clear();

        updateParentData(_displayList, _listDataHeader);
        updateChildData(_displayList, _listDataHeader, _listDataChild);
    }

}
