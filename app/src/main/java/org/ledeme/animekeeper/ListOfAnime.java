package org.ledeme.animekeeper;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static android.widget.GridLayout.VERTICAL;

public class ListOfAnime extends AppCompatActivity {

    public RecyclerView rv_animeList;
    public String name_title;
    public String genres_title;
    public JSONArray data;
    Button btnAddAnime;
    AnimeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);

        IntentFilter intentFilter = new IntentFilter(ANIME_LIST_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new AnimeUpdate(),intentFilter);

        //Set other intents/activity
        final Intent addAnime = new Intent(this, AddAnime.class);
        final Intent listAnime = new Intent(this, ListOfAnime.class);
        //final Intent animeDetail = new Intent(this, AnimeDetail.class);

        name_title = getResources().getString(R.string.rv_anime_list_name);
        rv_animeList = findViewById(R.id.rv_animeList);

        GetAnimeListService.startActionAnimeList(ListOfAnime.this);

        //definit comment le recycler view va scroller
        rv_animeList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //crée & gere le recyclerView
        data = getJsonFromFile();
        mAdapter = new AnimeListAdapter(data);
        rv_animeList.setAdapter(mAdapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(rv_animeList.getContext(), VERTICAL);
        itemDecor.setOrientation(VERTICAL);
        rv_animeList.addItemDecoration(itemDecor);

        btnAddAnime = findViewById(R.id.rv_button_add_anime);

        btnAddAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(addAnime);

            }
        });

        rv_animeList.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv_animeList ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        startActivity(addAnime);

                    }

                    @Override public void onLongItemClick(final View view, final int position) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListOfAnime.this);
                        // set title of Alert
                        alertDialogBuilder.setTitle(R.string.Delete_Anime);

                        // set dialog message of Alert
                        alertDialogBuilder
                                .setMessage(R.string.Delete_Anime_msg)
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        try {

                                            JSONObject obj = data.getJSONObject(position);

                                            //obj.get("id")


                                            // deletes this anime
                                            String url = "https://apex.oracle.com/pls/apex/anime_keeper/ak/postDeleteAnime";

                                            // Optional Parameters to pass as POST request
                                            JSONObject js = new JSONObject();
                                            try {
                                                js.put("ID", obj.get("id"));
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


                                                @Override
                                                public Map<String, String> getHeaders() throws AuthFailureError {
                                                    HashMap<String, String> headers = new HashMap<String, String>();
                                                    headers.put("Content-Type", "application/json; charset=utf-8");
                                                    return headers;
                                                }

                                            };

                                            // Adding request to request queue
                                            Volley.newRequestQueue(ListOfAnime.this).add(jsonObjReq);


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        //startActivity(listAnime);
                                        mAdapter.notifyDataSetChanged();

                                    }
                                })
                                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {

                                        // do nothing

                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }
                })
        );


    }

    //get the notification that the json file has been received
    public static final String ANIME_LIST_UPDATE = "org.ledeme.animekeeper.ANIME_LIST_UPDATE";
    public class AnimeUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("test","ended");

            AnimeListAdapter adapter = (AnimeListAdapter) rv_animeList.getAdapter();
            JSONArray data = getJsonFromFile();
            adapter.setNewAnimeList(data);
        }

    }

    // ---------------------------------------------------------------------------------------------

    //Adapter : c'est le tableau
    private class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.AnimeListHolder>{

        //Holder : c'est la case
        public class AnimeListHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView genres;

            private AnimeListHolder(View view){
                super(view);
                name = view.findViewById(R.id.rv_anime_list_element_name);
                genres = view.findViewById(R.id.rv_anime_list_element_genres);
            }
        }

        private JSONArray animeList;

        private AnimeListAdapter(JSONArray AnimeList){
            animeList = AnimeList;
        }

        public void setNewAnimeList(JSONArray newData){

            animeList = newData;
            notifyDataSetChanged();

        }

        @Override
        public AnimeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_anime_list_element, parent, false);

            return new AnimeListHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AnimeListHolder holder, int position) {

            try {
                JSONObject data = animeList.getJSONObject(position);
                holder.name.setText(getString(R.string.rv_anime_list_name) + data.getString("name"));
                holder.genres.setText(getString(R.string.rv_anime_list_genres) + data.getString("genres"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return animeList.length();
        }

        public JSONObject getItem(int position) throws JSONException {
            return animeList.getJSONObject(position);
        }
    }

    // ---------------------------------------------------------------------------------------------

    //Get the json from file
    public JSONArray getJsonFromFile(){
        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"animeList.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return  new JSONObject(new String(buffer,"UTF-8")).getJSONArray("items");
        } catch (IOException e){
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }

}


