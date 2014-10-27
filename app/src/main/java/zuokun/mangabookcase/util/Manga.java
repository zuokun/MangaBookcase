package zuokun.mangabookcase.util;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga {

    private String _title = Constants.EMPTY_STRING;
    private int _start_book_number = 0;
    private int _end_book_number = 0;
    private boolean _ongoing = false;

    public Manga (String title, int end_book_number, boolean ongoing) {
        _title = title;
        _start_book_number = 1; //MAGIC NUMBER
        _end_book_number = end_book_number;
        _ongoing = ongoing;
    }

    public String getTitle() {
        return _title;
    }

    public int getStartBookNumber() {
        return _start_book_number;
    }

    public int getEndBookNumber() {
        return _end_book_number;
    }

    public boolean isOngoing() { return _ongoing; }

    public String getStatus() {
        if (_ongoing) {
            return Constants.ONGOING;
        } else {
            return Constants.COMPLETED;
        }
    }

}


