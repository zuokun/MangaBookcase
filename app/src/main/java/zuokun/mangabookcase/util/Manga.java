package zuokun.mangabookcase.util;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga {

    private int _id;
    private String _title;
    private int _first_book;
    private int _last_book;
    private boolean _status;

    public Manga (String title, int last_book_number, boolean status) {
        _title = title;
        _first_book = 1; //MAGIC NUMBER
        _last_book = last_book_number;
        _status = status;
    }

    public Manga() {

    }

    // Getters

    public int getId() { return _id;  }

    public String getTitle() {
        return _title;
    }

    public int getFirstBookNumber() {
        return _first_book;
    }

    public int getLastBookNumber() {
        return _last_book;
    }

    public boolean getStatus() { return _status; }

    public String getStringStatus() {
        if (_status) {
            return Constants.ONGOING;
        } else {
            return Constants.COMPLETED;
        }
    }

    public int getIntStatus() {
        if (_status) {
            return Constants.INT_TRUE;
        } else {
            return Constants.INT_FALSE;
        }
    }

    // Setters

    public void setId(int id) { this._id = id; }

    public void setTitle(String title) {
        this._title = title;
    }

    public void setLastBookNumber(int last_book) {
        this._last_book = last_book;
    }

    public void setFirstBookNumber(int first_book) {
        this._first_book = first_book;
    }

    public void setStatus(boolean status) {
        this._status = status;
    }

    public void setIntStatus(int bool) {
        if (bool == Constants.INT_TRUE) {
            this._status = Constants.BOOL_TRUE;
        } else {
            this._status = Constants.BOOL_FALSE;
        }
    }

    public String toString() {
        return "Manga: " + _title + ", From " + _first_book + " to " + _last_book + Constants.STATUS + getStringStatus();
    }

}


