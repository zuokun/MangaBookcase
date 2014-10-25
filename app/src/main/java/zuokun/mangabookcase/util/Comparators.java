package zuokun.mangabookcase.util;

import java.util.Comparator;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Comparators  {

    public static class MangaComparator implements Comparator<Manga> {

        @Override
        public int compare(Manga m1, Manga m2) {
            return m1.getTitle().compareTo(m2.getTitle());
        }

    }

}
