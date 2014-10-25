package zuokun.mangabookcase.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import zuokun.mangabookcase.R;
import zuokun.mangabookcase.util.Manga;
import zuokun.mangabookcase.util.MangaAdapter;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set main.xml as user interface layout
        setContentView(R.layout.activity_main);

        Manga One_Piece = new Manga ("One Piece");
        Manga Gintama = new Manga ("Gintama");
        Manga No_Game_No_Life = new Manga ("No Game No Life");
        Manga Google = new Manga ("Google");

        Manga[] mangas = new Manga[] {One_Piece, Gintama, No_Game_No_Life, Google};

        MangaAdapter mangaAdapter = new MangaAdapter(this, mangas);

        ListView listView = (ListView) findViewById(R.id.mangaList1);
        listView.setAdapter(mangaAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView <?> l, View v, int position, long id) {

                Manga m = (Manga) l.getItemAtPosition(position);
                String s = m.getTitle();
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}