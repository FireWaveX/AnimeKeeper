package org.ledeme.animekeeper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class GetAnimeListService extends IntentService{

    private static final String ACTION_ANIME_LIST= "org.ledeme.animekeeper.action.FOO";

    public GetAnimeListService() {
        super("GetAnimeListService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionAnimeList(Context context) {
        Intent intent = new Intent(context, GetAnimeListService.class);
        intent.setAction(ACTION_ANIME_LIST);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_ANIME_LIST.equals(action)) {
                handleActionAnimeList();
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionAnimeList() {

        Log.d("Test", "Thread service name :" + Thread.currentThread().getName());

        URL url = null;

        try{
            url = new URL("https://apex.oracle.com/pls/apex/anime_keeper/ak/getAnimeList");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "animeList.json"));
                Log.d("Test", "Anime list json downloaded !");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ListOfAnime.ANIME_LIST_UPDATE));
            }
        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file) {
        try{
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
