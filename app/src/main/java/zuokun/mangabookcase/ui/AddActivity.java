package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Constants;
import zuokun.mangabookcase.util.Manga;

/**
 * Created by ZeitiaX on 11/2/2014.
 */
public class AddActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

    }

    public void addNewManga(View view) {

        EditText mangaTitle = (EditText) findViewById(R.id.addNewMangaTitle);
        String s = mangaTitle.getText().toString();

        EditText lastBook = (EditText) findViewById(R.id.addNewMangaFinalBook);
        int book = Integer.parseInt(lastBook.getText().toString());

        CheckBox completedCheckBox = (CheckBox) findViewById(R.id.addNewMangaStatus);
        boolean isCompleted = completedCheckBox.isChecked();

        Manga m = new Manga(s, book, isCompleted);

        MainActivity.parse(Constants.Commands.ADD, m);

        finish();

    }

}
