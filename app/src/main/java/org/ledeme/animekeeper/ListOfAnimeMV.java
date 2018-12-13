package org.ledeme.animekeeper;

import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListOfAnimeMV {

    ListOfAnimeMV(){}

    private ObservableField<String> name = new ObservableField<>("");
    private ObservableField<String> genre = new ObservableField<>("");

    public String getName(){
        return name.get();
    }
    public void setName(ObservableField<String> name) {
        this.name = name;
        this.name.notifyChange();
    }

    public String getGenre(){
        return genre.get();
    }
    public void setGenre(ObservableField<String> genre) {
        this.genre = genre;
        this.genre.notifyChange();
    }

    public void onClick(){

        Log.i("truc", "clicked add anime");
        ListOfAnime.MVVM_click();

    }

    // ---------------------------------------------------------------------------------------------

    //Adapter : c'est le tableau
    public static class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.AnimeListHolder>{

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

        AnimeListAdapter(JSONArray AnimeList){
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
                holder.name.setText("Name : " + data.getString("name"));
                holder.genres.setText("Genre : " + data.getString("genres"));
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





}
