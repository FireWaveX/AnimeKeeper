package org.ledeme.animekeeper;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by alexl on 11/05/2018.
 */

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();

    }

    public static Context getContext() {
        return App.context;
    }
}
