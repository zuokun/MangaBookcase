package zuokun.mangabookcase.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga implements Parcelable {

    private int _id;
    private String _title;
    private String _publisher;
    private int _last_book;
    private int[] _missing_books;
    private boolean _status;
    private boolean _favourite;

    /********************
     *   Constructors   *
     *******************/

    // (<id>, title, last_book, missing, publisher, status, favourite)
    public Manga (int id, String title, String publisher, int last_book_number, int[] missing_books, boolean status, boolean favourite) {
        _id = id;
        _title = title;
        _publisher = publisher;
        _last_book = last_book_number;
        _missing_books = missing_books;
        _status = status;
        _favourite = favourite;
    }

    public Manga (String title, String publisher, int last_book_number, int[] missing_books, boolean status, boolean favourite) {
        _title = title;
        _publisher = publisher;
        _last_book = last_book_number;
        _missing_books = missing_books;
        _status = status;
        _favourite = favourite;
    }

    public Manga() {

    }

    /*************************
     *        Setters        *
     ************************/

    public int getId() { return _id;  }

    public String getTitle() {
        return _title;
    }

    public int getLastBookNumber() {
        return _last_book;
    }

    public boolean getStatus() { return _status; }

    public boolean isFavourite() { return _favourite; }

    public String getPublisher() { return _publisher; }

    public int[] getMissingBooks() { return _missing_books; }

    /*************************
     *        Setters        *
     ************************/

    public void setId(int id) { this._id = id; }

    public void setTitle(String title) { this._title = title; }

    public void setLastBookNumber(int last_book) { this._last_book = last_book; }

    public void setStatus(boolean status) { this._status = status; }

    public void setFavourite(boolean favourite) { this._favourite = favourite; }

    public void setPublisher(String publisher) { this._publisher = publisher; }

    public void setMissingBooks(int[] missing_books) { this._missing_books = missing_books; }

    public void setStringMissingBooks(String stringMissingBooks) {
        int[] missing_books = transformStringToIntArray(stringMissingBooks);
        this._missing_books = missing_books;
    }

    private int[] transformStringToIntArray(String missing_books) {
        return new int[0];
    }

    /************************
     *     Other methods    *
     ***********************/

    public String toString() {
        return "Manga: " + _title + ", Until " + _last_book + Constants.STATUS + getStringStatus();
    }

    public String getStringStatus() {
        if (_status) {
            return Constants.ONGOING;
        } else {
            return Constants.COMPLETED;
        }
    }

    public String getStringMissingBooks() {
        return "";
        //return Arrays.toString(_missing_books);
    }

    public int getIntStatus() {
        if (_status) {
            return Constants.INT_TRUE;
        } else {
            return Constants.INT_FALSE;
        }
    }

    public int getIntFavourite() {
        if (_favourite) {
            return Constants.INT_TRUE;
        } else {
            return Constants.INT_FALSE;
        }
    }

    public void setIntStatus(int bool) {
        if (bool == Constants.INT_TRUE) {
            this._status = Constants.BOOL_TRUE;
        } else {
            this._status = Constants.BOOL_FALSE;
        }
    }

    public void setIntFavourite(int bool) {
        if (bool == Constants.INT_TRUE) {
            this._favourite = Constants.BOOL_TRUE;
        } else {
            this._favourite = Constants.BOOL_FALSE;
        }
    }

    public void addOneBookBehind() {
        this._last_book++;
    }

    /***********************
     *  Parcelable Methods
     **********************/

    @Override
    public int describeContents() {
        return 0;
    }

    public Manga (Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(_id);
        parcel.writeString(_title);
        parcel.writeString(_publisher);
        parcel.writeInt(_last_book);
        //parcel.writeIntArray(_missing_books);
        parcel.writeByte((byte) (_status ? 1 : 0));
        parcel.writeByte((byte) (_favourite ? 1 : 0));
    }

    private void readFromParcel (Parcel in) {
        _id = in.readInt();
        _title = in.readString();
        _publisher = in.readString();
        _last_book = in.readInt();
        //_missing_books = in.readIntArray();
        _status = in.readByte() != 0;
        _favourite = in.readByte() != 0;
    }

    public static final Creator CREATOR = new Creator() {
        public Manga createFromParcel(Parcel in) {
            return new Manga(in);
        }

        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

}


