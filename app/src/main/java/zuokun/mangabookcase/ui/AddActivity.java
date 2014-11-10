package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.EventListener;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaBookcaseEventListener;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class AddActivity extends Activity implements MangaBookcaseEventListener {

    public void handleEvent() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.addListener(this);

        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.addActivityDone:
                addNewManga();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.removeListener(this);
    }

    public void addNewManga() {

        EditText mangaTitle = (EditText) findViewById(R.id.addNewMangaTitle);
        EditText lastBook = (EditText) findViewById(R.id.addNewMangaFinalBook);

        String title = mangaTitle.getText().toString();
        String books = lastBook.getText().toString();

        if (title.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_NAME_EMPTY, Toast.LENGTH_SHORT).show();
        } else if (books.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_LAST_BOOK, Toast.LENGTH_SHORT).show();
        } else {

            int book = Integer.parseInt(books);

            CheckBox completedCheckBox = (CheckBox) findViewById(R.id.addNewMangaStatus);
            boolean isOngoing = completedCheckBox.isChecked();

            Manga m = new Manga(title, book, isOngoing);


            MainActivity.logic.parseCommand(Constants.Commands.ADD, m, getApplicationContext());

            finish();
        }

    }

}
