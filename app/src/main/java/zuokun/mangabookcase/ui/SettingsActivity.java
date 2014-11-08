package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;

import zuokun.mangabookcase.R;

/**
 * Created by ZeitiaX on 11/8/2014.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}