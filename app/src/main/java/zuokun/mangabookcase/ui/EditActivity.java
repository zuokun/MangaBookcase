package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

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
    ImageButton mangaImage;

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
        mangaImage = (ImageButton) findViewById(R.id.editMangaImageButton);

        titleEditText.setText(_manga.getTitle());
        lastBookEditText.setText(Integer.toString(_manga.getLastBookNumber()));
        publisherEditText.setText(_manga.getPublisher());
        ongoingCheckBox.setChecked(_manga.isOngoing());
        favouriteCheckBox.setChecked(_manga.isFavourite());

        if (!_manga.getImagePath().equalsIgnoreCase("")) {
            File imageFile = new File(_manga.getImagePath());
            if (imageFile.exists()) {
                Bitmap mangaBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                mangaImage.setImageBitmap(mangaBitmap);
            }
        }

        // TODO Method to get image path and missing books

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
                try {
                    editManga();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.editActivityDelete:
                deleteManga();
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void editManga() throws IOException {

        int id = _manga.getId();
        String title = titleEditText.getText().toString();
        String bookString = lastBookEditText.getText().toString();
        String publisher = publisherEditText.getText().toString();
        String mangaImagePath = null;
        boolean isOngoing = ongoingCheckBox.isChecked();
        boolean isFavourite = favouriteCheckBox.isChecked();

        if (title.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_NAME_EMPTY, Toast.LENGTH_SHORT).show();
        } else if (bookString.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_LAST_BOOK, Toast.LENGTH_SHORT).show();
        } else {

            if (mangaImagePath == null) {
                mangaImagePath = ""; // TODO Need an else to get image path
            }

            int book = Integer.parseInt(bookString);
            Manga mManga = new Manga(title,
                    publisher,
                    mangaImagePath,
                    book,
                    new int[]{},
                    isOngoing,
                    isFavourite);

            MainActivity.parse(Constants.Commands.UPDATE, mManga, getApplicationContext());

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
            builder.setMessage("Are you sure you want to delete " + _manga.getTitle() + "?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    confirmDeleteManga();
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

    private void confirmDeleteManga() {
        try {
            MainActivity.parse(Constants.Commands.DELETE, _manga, MangaBookcaseApp.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
