package zuokun.mangabookcase.util;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga {

    private String _title = Constants.EMPTY_STRING;
    private int _start_book_number;
    private int _end_book_number;

    public Manga (String title) {
        _title = title;
    }

    public String getTitle() {
        return _title;
    }

    public int getStartBookNumber() {
        return _start_book_number;
    }

    public int get_end_book_number() {
        return _end_book_number;
    }

}


