package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class EditActivity extends Activity {

    private Manga _manga;

    EditText titleEditText;
    EditText lastBookEditText;
    EditText publisherEditText;
    CheckBox ongoingCheckBox;
    CheckBox favouriteCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        _manga = i.getExtras().getParcelable("parcelManga");

        titleEditText = (EditText) findViewById(R.id.editMangaTitle);
        lastBookEditText = (EditText) findViewById(R.id.editMangaLastBook);
        publisherEditText = (EditText) findViewById(R.id.editMangaPublisher);
        ongoingCheckBox = (CheckBox) findViewById(R.id.editMangaStatus);
        favouriteCheckBox = (CheckBox) findViewById(R.id.editMangaFavourite);

        titleEditText.setText(_manga.getTitle());
        lastBookEditText.setText(Integer.toString(_manga.getLastBookNumber()));
        publisherEditText.setText(_manga.getPublisher());
        ongoingCheckBox.setChecked(_manga.getStatus());
        favouriteCheckBox.setChecked(_manga.isFavourite());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.editActivityDone:
                editManga();
                break;
            case R.id.editActivityDelete:
                deleteManga();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void editManga() {

        int id = _manga.getId();
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
            Manga mManga = new Manga(id, title, publisher, book, _manga.getMissingBooks(), isOngoing, isFavourite);

            MainActivity.sLogic.parseCommand(Constants.Commands.UPDATE, mManga, getApplicationContext());

            finish();
        }

    }

    private void deleteManga() {

        DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
        deleteDialogFragment.show(getFragmentManager(), "Confirm Delete");
    }

    public class DeleteDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirm delete");
            builder.setMessage("Are you sure you want to delete the Manga " + _manga.getTitle() + "?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.sLogic.parseCommand(Constants.Commands.DELETE, _manga, MangaBookcaseApp.getContext());
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            return builder.create();
        }
    }

}
