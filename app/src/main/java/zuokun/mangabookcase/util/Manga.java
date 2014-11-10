package zuokun.mangabookcase.util;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Manga implements Parcelable {

    private int _id;
    private String _title;
    private int _last_book;
    private boolean _status;

    /********************
     *   Constructors   *
     *******************/
    public Manga (int id, String title, int last_book_number, boolean status) {
        _id = id;
        _title = title;
        _last_book = last_book_number;
        _status = status;
    }

    public Manga (String title, int last_book_number, boolean status) {
        _title = title;
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
        return "Manga: " + _title + ", Until " + _last_book + Constants.STATUS + getStringStatus();
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
        parcel.writeInt(_last_book);
        parcel.writeByte((byte) (_status ? 1 : 0));
    }

    private void readFromParcel (Parcel in) {
        _id = in.readInt();
        _title = in.readString();
        _last_book = in.readInt();
        _status = in.readByte() != 0;
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


