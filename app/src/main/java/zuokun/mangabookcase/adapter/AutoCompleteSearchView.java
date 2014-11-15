package zuokun.mangabookcase.adapter;

import android.content.Context;

import android.widget.SearchView;
import android.util.AttributeSet;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;

import zuokun.mangabookcase.R;


/**
 * Created by ZeitiaX on 11/14/2014.
 */
public class AutoCompleteSearchView extends SearchView {

    private android.support.v7.widget.SearchView.SearchAutoComplete mSearchAutoComplete;

    public AutoCompleteSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public AutoCompleteSearchView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        mSearchAutoComplete = (android.support.v7.widget.SearchView.SearchAutoComplete) findViewById(android.support.v7.appcompat.R.id.search_src_text);
        this.setAdapter(null);
        this.setOnItemClickListener(null);
    }

    @Override
    public void setSuggestionsAdapter(CursorAdapter adapter) {
        //
    }

    private void setOnItemClickListener(OnItemClickListener listener) {
        mSearchAutoComplete.setOnClickListener((OnClickListener) listener);
    }

    public void setAdapter(ArrayAdapter<?> adapter) {
        mSearchAutoComplete.setAdapter(adapter);
    }
}
