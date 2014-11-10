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

    EditText editMangaTitle;
    EditText editMangaFinalBook;
    CheckBox editMangaStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        _manga = i.getExtras().getParcelable("parcelManga");

        editMangaTitle = (EditText) findViewById(R.id.editMangaTitle);
        editMangaFinalBook = (EditText) findViewById(R.id.editMangaFinalBook);
        editMangaStatus = (CheckBox) findViewById(R.id.editMangaStatus);

        editMangaTitle.setText(_manga.getTitle());
        editMangaFinalBook.setText(Integer.toString(_manga.getLastBookNumber()));
        editMangaStatus.setChecked(_manga.getStatus());

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

        String title = editMangaTitle.getText().toString();
        String books = editMangaFinalBook.getText().toString();

        if (title.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_NAME_EMPTY, Toast.LENGTH_SHORT).show();
        } else if (books.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_LAST_BOOK, Toast.LENGTH_SHORT).show();
        } else {

            int book = Integer.parseInt(books);
            int id = _manga.getId();
            boolean isOngoing = editMangaStatus.isChecked();

            Manga mManga = new Manga(id, title, book, isOngoing);


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
