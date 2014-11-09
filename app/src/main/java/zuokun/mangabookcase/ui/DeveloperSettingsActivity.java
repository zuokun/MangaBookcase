package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.storage.MangaSQLiteHelper;

/**
 * Created by ZeitiaX on 11/8/2014.
 */
public class DeveloperSettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dev_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Button deleteFirstManga = (Button) findViewById(R.id.devRemoveMangaButton);
        TextView mangaNumber = (TextView) findViewById(R.id.devMangaNumber);
        Switch sfo = (Switch) findViewById(R.id.devSwitchFirstOpen);

        String numberOfManga = Integer.toString(MainActivity.logic.listManga.size());
        mangaNumber.setText(numberOfManga);

        sfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton sfo, boolean isChecked) {

                if (isChecked) {
                    MainActivity.firstStart = true;
                    showToast(getApplicationContext(), "firstStart is now true");
                } else {
                    MainActivity.firstStart = false;
                    showToast(getApplicationContext(), "firstStart is now false");
                }
            }

        });

    }

    private static void showToast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public void removeOneMangaBtn (View view) {

        try {
            MangaSQLiteHelper db = new MangaSQLiteHelper(MangaBookcaseApp.getContext());
            db.deleteManga(db.getManga(0));
        } catch (ArrayIndexOutOfBoundsException e) {
            Toast.makeText(this, "No Manga", Toast.LENGTH_SHORT).show();
        }
        TextView mangaNumber = (TextView) findViewById(R.id.devMangaNumber);
        String numberOfManga = Integer.toString(MainActivity.logic.listManga.size());
        mangaNumber.setText(numberOfManga);

    }

}
