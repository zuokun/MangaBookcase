package zuokun.mangabookcase.util;

/**
 * Created by ZeitiaX on 10/26/2014.
 */
public class Constants {

    public enum Commands { ADD, UPDATE, DELETE};

    // Strings
    public static final String EMPTY_STRING = "";
    public static final String BLANK_SPACE = " ";

    // Settings

    public static final String FILE_CONFIG = "config";
    public static final String FIRST_START = "config_first_start";
    //-Manga Child
    public static final String MISSING_BOOKS = "Missing books: ";
    public static final String MISSING_BOOK = "Missing book: ";
    public static final String PUBLISHER = "Publisher: ";
    public static final String STATUS = "Status: ";

    public static final String ONGOING = "Ongoing";
    public static final String COMPLETED = "Completed";

    // Integers
    public static final int MANGA_ATTRIBUTES = 4;
    public static final int INT_TRUE = 1;
    public static final int INT_FALSE = 0;


    // public static final int
    // Floats

    // Booleans
    public static final boolean BOOL_TRUE = true;
    public static final boolean BOOL_FALSE = false;

    // Errors

    public static final String ERROR_FIELD_MANGA_NAME_EMPTY = "Manga Title cannot be empty!";
    public static final String ERROR_FIELD_MANGA_LAST_BOOK = "Enter the last book you have!";

    // Others


}