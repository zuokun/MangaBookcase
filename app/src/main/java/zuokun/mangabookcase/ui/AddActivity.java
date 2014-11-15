package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class AddActivity extends Activity {

    public void handleEvent() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        EditText lastBookEditText = (EditText) findViewById(R.id.addMangaLastBook);
        lastBookEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                try {
                    addNewManga();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addNewManga() throws IOException {

        EditText titleEditText = (EditText) findViewById(R.id.addMangaTitle);
        EditText lastBookEditText = (EditText) findViewById(R.id.addMangaLastBook);
        EditText publisherEditText = (EditText) findViewById(R.id.addMangaPublisher);
        CheckBox ongoingCheckBox = (CheckBox) findViewById(R.id.addMangaStatus);
        CheckBox favouriteCheckBox = (CheckBox) findViewById(R.id.addMangaFavourite);

        String title = titleEditText.getText().toString();
        String bookString = lastBookEditText.getText().toString();
        String publisher = publisherEditText.getText().toString();
        boolean isOngoing = ongoingCheckBox.isChecked();
        boolean isFavourite = favouriteCheckBox.isChecked();

        if (title.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_NAME_EMPTY, Toast.LENGTH_SHORT).show();
        } else if (bookString.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_LAST_BOOK, Toast.LENGTH_SHORT).show();
        } else {

            int book = Integer.parseInt(bookString);
            Manga mManga = new Manga(title, publisher, book, new int[]{}, isOngoing, isFavourite);

            MainActivity.parse(Constants.Commands.ADD, mManga, getApplicationContext());

            finish();
        }

    }

}
