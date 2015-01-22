package zuokun.mangabookcase.app;

import android.app.Application;
import android.content.Context;

import java.util.List;

import zuokun.mangabookcase.logic.Logic;
import zuokun.mangabookcase.util.Publisher;

/**
 * Created by ZeitiaX on 11/9/2014.
 */
public class MangaBookcaseApp extends Application {

    private static List<Publisher> publishers;

    private static MangaBookcaseApp instance;

    public MangaBookcaseApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public static List<Publisher> getPublishers() {
        return publishers;
    }

}
