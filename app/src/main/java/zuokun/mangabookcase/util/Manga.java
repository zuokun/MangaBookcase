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
    private String _image_path;
    private int _last_book;
    private int[] _missing_books;
    private boolean _ongoing;
    private boolean _favourite;

    /********************
     *   Constructors   *
     *******************/

    // (title, last_book, missing, publisher, status, favourite)
    // Status: true = ongoing
    public Manga (String title, String publisher, String image_path, int last_book_number, int[] missing_books, boolean ongoing, boolean favourite) {

        Arrays.sort(missing_books);

        _title = title;
        _publisher = publisher;
        _image_path = image_path;
        _last_book = last_book_number;
        _missing_books = missing_books;
        _ongoing = ongoing;
        _favourite = favourite;
    }

    public Manga() {

    }

    public Manga(int id, String title, String publisher, String image_path, int last_book_number, int[] missing_books, boolean ongoing, boolean favourite) {

        _id = id;
        _title = title;
        _publisher = publisher;
        _image_path = image_path;
        _last_book = last_book_number;
        _missing_books = missing_books;
        _ongoing = ongoing;
        _favourite = favourite;

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

    public boolean isOngoing() { return _ongoing; }

    public boolean isFavourite() { return _favourite; }

    public String getPublisher() { return _publisher; }

    public int[] getMissingBooks() { return _missing_books; }

    public String getImagePath() { return _image_path; }

    /*************************
     *        Setters        *
     ************************/

    public void setId(int id) { this._id = id; }

    public void setTitle(String title) { this._title = title; }

    public void setLastBookNumber(int last_book) { this._last_book = last_book; }

    public void setStatus(boolean status) { this._ongoing = status; }

    public void setFavourite(boolean favourite) { this._favourite = favourite; }

    public void setPublisher(String publisher) { this._publisher = publisher; }

    public void setMissingBooks(int[] missing_books) { this._missing_books = missing_books; }

    public void setStringMissingBooks(String stringMissingBooks) {
        if (stringMissingBooks.equals(Constants.EMPTY_ARRAY_STRING)) {
            this._missing_books = new int[] {};
        } else {
            int[] missing_books = transformStringToIntArray(stringMissingBooks);
            this._missing_books = missing_books;
        }
    }

    public void setImagePath(String image_path) { this._image_path = image_path; }

    /************************
     *     Other methods    *
     ***********************/

    public String toString() {
        return "Manga: " + _title + ", Until " + _last_book + Constants.STATUS + getStringStatus();
    }

    public String getStringStatus() {
        if (_ongoing) {
            return Constants.ONGOING;
        } else {
            return Constants.COMPLETED;
        }
    }

    public int getIntStatus() {
        if (_ongoing) {
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
            this._ongoing = Constants.BOOL_TRUE;
        } else {
            this._ongoing = Constants.BOOL_FALSE;
        }
    }

    public void setIntFavourite(int bool) {
        if (bool == Constants.INT_TRUE) {
            this._favourite = Constants.BOOL_TRUE;
        } else {
            this._favourite = Constants.BOOL_FALSE;
        }
    }

    // From database String to int[]
    private int[] transformStringToIntArray(String missing_books) {
            String[] mStrings = missing_books.replaceAll("\\[", "").replaceAll("\\]", "").split(", ");
            int[] mIntArray = new int[mStrings.length];
            for (int iIntegerPosition = 0; iIntegerPosition < mStrings.length; iIntegerPosition++) {
                mIntArray[iIntegerPosition] = Integer.parseInt(mStrings[iIntegerPosition]);
            }
            return mIntArray;
    }

    // To use in storing in database, has brackets []
    public String getStringMissingBooks() {
        String mString = Arrays.toString(_missing_books);
        return mString;
    }

    // To use in display in child, no brackets
    public String getStringDisplayMissingBooks() {
        String mString = Arrays.toString(_missing_books).replaceAll("\\[", "").replaceAll("\\]", "");
        return mString;
    }

    public boolean hasMissingBooks() {
        return _missing_books.length > 0;
    }

    public void addOneBookBehind() {
        this._last_book++ ;
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
        parcel.writeString(_image_path);
        parcel.writeInt(_last_book);
        parcel.writeIntArray(_missing_books);
        parcel.writeByte((byte) (_ongoing ? 1 : 0));
        parcel.writeByte((byte) (_favourite ? 1 : 0));
    }

    private void readFromParcel (Parcel in) {
        _id = in.readInt();
        _title = in.readString();
        _publisher = in.readString();
        _image_path = in.readString();
        _last_book = in.readInt();
        _missing_books = in.createIntArray();
        _ongoing = in.readByte() != 0;
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


