package zuokun.mangabookcase.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import zuokun.mangabookcase.R;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class MangaAdapter extends ArrayAdapter {

    public MangaAdapter (Context context, Manga[] mangas) {
        super(context, R.layout.manga_layout, mangas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.manga_layout, parent, false);

        Manga manga = (Manga) getItem(position);
        String text = manga.getTitle();

        TextView textView = (TextView) view.findViewById(R.id.textView1);
        textView.setText(text);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);
        imageView.setImageResource(android.R.drawable.ic_menu_info_details);

        if ("Google".equals(text)) {
            imageView.setImageResource(android.R.drawable.ic_menu_add);
        }

        return view;
    }

}