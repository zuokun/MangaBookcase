package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.content.ComponentName;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class AddActivity extends Activity {

    final private int PICK_IMAGE = 1;
    private String imgPath;
    private String mCurrentPhotoPath;

    EditText titleEditText;
    EditText lastBookEditText;
    EditText missingBooksEditText;
    EditText publisherEditText;
    CheckBox ongoingCheckBox;
    CheckBox favouriteCheckBox;
    ImageView mangaImage;
    Spinner publisherSpinner;

    private Uri outputFileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = (EditText) findViewById(R.id.addMangaTitle);
        lastBookEditText = (EditText) findViewById(R.id.addMangaLastBook);
        publisherEditText = (EditText) findViewById(R.id.addMangaPublisherEditView);
        //publisherSpinner = (Spinner) findViewById(R.id.addMangaPublisherSpinner);
        missingBooksEditText = (EditText) findViewById(R.id.addMangaMissingBookEditView);
        ongoingCheckBox = (CheckBox) findViewById(R.id.addMangaStatus);
        favouriteCheckBox = (CheckBox) findViewById(R.id.addMangaFavourite);
        mangaImage = (ImageButton) findViewById(R.id.addMangaImageView);

        mangaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputFileUri = setImageUri();
                openImageIntent();
            }
        });

        publisherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // TODO Method to get missing books
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

        String title = titleEditText.getText().toString();
        String bookString = lastBookEditText.getText().toString();
        String publisher = publisherEditText.getText().toString();
        String mangaImagePath = imgPath;
        String missingBooksString = missingBooksEditText.getText().toString();
        int[] missingBooks = null;
        boolean isOngoing = ongoingCheckBox.isChecked();
        boolean isFavourite = favouriteCheckBox.isChecked();

        if (title.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_NAME_EMPTY, Toast.LENGTH_SHORT).show();
        } else if (bookString.matches(Constants.EMPTY_STRING)) {
            Toast.makeText(getApplicationContext(), Constants.ERROR_FIELD_MANGA_LAST_BOOK, Toast.LENGTH_SHORT).show();
        } else {

            if (mangaImagePath == null) {
                mangaImagePath = "";
            } else {
                renameImage(outputFileUri, title);
            }

            int book = Integer.parseInt(bookString);
            Manga mManga = new Manga(title,
                                     publisher,
                                     mangaImagePath,
                                     book,
                                     missingBooksString,
                                     isOngoing,
                                     isFavourite);

            MainActivity.parse(Constants.Commands.ADD, mManga, getApplicationContext());

            finish();
        }

    }

    private void renameImage(Uri outputFileUri, String title) {

        File oldFile = new File(outputFileUri.getPath());
        final File file = new File(Environment.getExternalStorageDirectory() + "/MangaBookcase/" + title + ".jpg");
        oldFile.renameTo(file);
    }

    /*****************
     * Setting Image *
     ****************/

    private Uri setImageUri() {
        final File file = new File(Environment.getExternalStorageDirectory() + "/MangaBookcase/temp.jpg");
        Uri imgUri = Uri.fromFile(file);
        imgPath = file.getAbsolutePath();
        return imgUri;
    }

    private void openImageIntent() {

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<>();
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
                    imgPath = selectedImageUri.toString();
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                    mangaImage.setImageURI(selectedImageUri);
                    imgPath = selectedImageUri.getPath();
                }
            }
        }
    }
}