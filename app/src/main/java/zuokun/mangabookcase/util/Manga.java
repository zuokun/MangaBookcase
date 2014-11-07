package zuokun.mangabookcase.util;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga {

    private int id;
    private String _title;
    private int _start_book_number;
    private int _end_book_number;
    private boolean _status;

    public Manga (String title, int end_book_number, boolean status) {
        _title = title;
        _start_book_number = 1; //MAGIC NUMBER
        _end_book_number = end_book_number;
        _status = status;
    }

    // Getters

    public String getTitle() {
        return _title;
    }

    public int getStartBookNumber() {
        return _start_book_number;
    }

    public int getEndBookNumber() {
        return _end_book_number;
    }

    public boolean getStatus() { return _status; }

    // Setters

    public void setTitle(String title) {
        this._title = title;
    }

    public void setEndBookNumber(int end_book_number) {
        this._end_book_number = end_book_number;
    }

    public void setStartBookNumber(int start_book_number) {
        this._start_book_number = start_book_number;
    }

    public void setStatus(boolean status) {
        this._status = status;
    }

    public String getStringStatus() {
        if (_status) {
            return Constants.ONGOING;
        } else {
            return Constants.COMPLETED;
        }
    }

    public String toString() {
        return "Manga: " + _title + ", From " + _start_book_number + " to " + _end_book_number + Constants.STATUS + getStringStatus();
    }

}


