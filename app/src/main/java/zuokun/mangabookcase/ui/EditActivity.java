package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.app.MangaBookcaseApp;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class EditActivity extends Activity {

    final private int PICK_IMAGE = 1;
    private String imgPath;

    private Manga _manga;

    EditText titleEditText;
    EditText lastBookEditText;
    EditText publisherEditText;
    CheckBox ongoingCheckBox;
    CheckBox favouriteCheckBox;
    ImageView mangaImage;

    private Uri outputFileUri;

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
        mangaImage = (ImageView) findViewById(R.id.editMangaImageView);
        ongoingCheckBox = (CheckBox) findViewById(R.id.editMangaStatus);
        favouriteCheckBox = (CheckBox) findViewById(R.id.editMangaFavourite);
        mangaImage = (ImageButton) findViewById(R.id.editMangaImageView);

        titleEditText.setText(_manga.getTitle());
        lastBookEditText.setText(Integer.toString(_manga.getLastBookNumber()));
        publisherEditText.setText(_manga.getPublisher());
        outputFileUri = Uri.parse(_manga.getImagePath());
        ongoingCheckBox.setChecked(_manga.isOngoing());
        favouriteCheckBox.setChecked(_manga.isFavourite());

        Toast.makeText(EditActivity.this, _manga.getImagePath(), Toast.LENGTH_SHORT).show();

        if (!_manga.getImagePath().equalsIgnoreCase("")) {
            mangaImage.setImageURI(outputFileUri);
            /*
            File imageFile = new File(_manga.getImagePath());
            if (imageFile.exists()) {
                Bitmap mangaBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                mangaImage.setImageBitmap(mangaBitmap);
            }
            */
        }

        mangaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   outputFileUri = setImageUri();
                openImageIntent();
             //   Toast.makeText(EditActivity.this, imgPath, Toast.LENGTH_SHORT).show();
            }
        });

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
        String mangaImagePath = imgPath;
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
                    imgPath,
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

    /*****************
     * Setting Image *
     ****************/

    private Uri setImageUri() {
        final File file = new File(Environment.getExternalStorageDirectory() + File.separator + "MangaBookcase" + new Date().getTime() + File.separator);
        Uri imgUri = Uri.fromFile(file);
        imgPath = file.getAbsolutePath();
        return imgUri;
    }

    private void openImageIntent() {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                Uri selectedImageUri;
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                    mangaImage.setImageURI(selectedImageUri);
                    Toast.makeText(EditActivity.this, "Camera!", Toast.LENGTH_SHORT).show();
                    imgPath = selectedImageUri.getPath();
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    mangaImage.setImageURI(selectedImageUri);
                    File file = new File(selectedImageUri.getPath());
                    imgPath = selectedImageUri.toString();
                }
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
