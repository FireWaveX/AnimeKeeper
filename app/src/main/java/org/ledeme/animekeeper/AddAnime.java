package org.ledeme.animekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.ledeme.animekeeper.databinding.ContentActivityAddAnimeBinding;
import org.ledeme.animekeeper.databinding.ContentActivityMainBinding;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddAnime extends AppCompatActivity {

    /*public String name;
    public String genres;
    public String season;
    public String nb_episodes;
    public Button btnAddAnime;*/

    Intent listOfAnime;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_anime);
        AddAnimeMV addAnimeMV = new AddAnimeMV();
        ((ContentActivityAddAnimeBinding) DataBindingUtil.setContentView(this, R.layout.content_activity_add_anime))
                .setLoginVM(addAnimeMV);

        //Set other intents/activity
        listOfAnime = new Intent(this, ListOfAnime.class);

        /*
        final EditText Ename = (EditText)findViewById(R.id.add_anime_name_input);
        final EditText Egenre = (EditText)findViewById(R.id.add_anime_genres_input);
        final EditText Eseason = (EditText)findViewById(R.id.add_anime_season_input);
        final EditText Eepisodes = (EditText)findViewById(R.id.add_anime_episodes_input);

        Ename.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        Egenre.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        */

        mContext = this;

        /*
        btnAddAnime = findViewById(R.id.add_anime_btn);

        btnAddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        */

    }

    public static void MVVM_click(String name, String genres, String season, String nb_episodes){


        Log.d("Test", "click on button");

        String url = "https://apex.oracle.com/pls/apex/anime_keeper/ak/postAnime";

        // Optional Parameters to pass as POST request
        JSONObject js = new JSONObject();
        try {
            js.put("NAME", name);
            js.put("GENRES", genres);
            js.put("SEASON", season);
            js.put("NB_EPISODE", nb_episodes);
            js.put("RATING", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Info", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Info", "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        // Adding request to request queue
        Volley.newRequestQueue(mContext).add(jsonObjReq);


        final Intent LOA = new Intent(mContext, ListOfAnime.class);
        mContext.startActivity(LOA);

    }

    @Override
    public void onBackPressed() {

        startActivity(listOfAnime);

    }

}
