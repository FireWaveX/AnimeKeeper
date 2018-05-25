package org.ledeme.animekeeper;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.widget.GridLayout.VERTICAL;

public class ListOfAnime extends AppCompatActivity {

    public RecyclerView rv_animeList;
    public String name_title;
    public String genres_title;
    Button btnAddAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_list);

        IntentFilter intentFilter = new IntentFilter(ANIME_LIST_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new AnimeUpdate(),intentFilter);

        //Set other intents/activity
        final Intent addAnime = new Intent(this, AddAnime.class);

        name_title = getResources().getString(R.string.rv_anime_list_name);
        rv_animeList = findViewById(R.id.rv_animeList);

        GetAnimeListService.startActionAnimeList(ListOfAnime.this);

        //definit comment le recycler view va scroller
        rv_animeList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //crée & gere le recyclerView
        JSONArray data = getJsonFromFile();
        rv_animeList.setAdapter(new AnimeListAdapter(data));

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

            public TextView name;
            public TextView genres;

            public AnimeListHolder(View view){
                super(view);
                name = view.findViewById(R.id.rv_anime_list_element_name);
                genres = view.findViewById(R.id.rv_anime_list_element_genres);
            }
        }

        private JSONArray animeList = new JSONArray();

        public AnimeListAdapter(JSONArray AnimeList){
            this.animeList = AnimeList;
        }

        public void setNewAnimeList(JSONArray newData){

            this.animeList = newData;
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


