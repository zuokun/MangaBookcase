package zuokun.mangabookcase.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import zuokun.mangabookcase.ui.MainActivity;

/**
 * Created by ZeitiaX on 11/9/2014.
 */
public class MangaBookcaseApp extends Application {

    private static MangaBookcaseApp instance;

        public MangaBookcaseApp() {
            instance = this;
        }

    public static Context getContext() {
        return instance;
    }

}
