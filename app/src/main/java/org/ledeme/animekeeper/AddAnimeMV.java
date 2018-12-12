package org.ledeme.animekeeper;

import android.databinding.ObservableField;
import android.util.Log;

public class AddAnimeMV {

    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> genres = new ObservableField<>();
    private ObservableField<String> season = new ObservableField<>();
    private ObservableField<String> nbEpisodes = new ObservableField<>();

    private String anmName;
    private String anmGenres;
    private String anmSeason;
    private String anmNbEpisodes;


    public String getName(){
        return name.get();
    }
    public void setName(ObservableField<String> name) {
        this.name = name;
        this.name.notifyChange();
    }

    public String getGenres(){
        return genres.get();
    }
    public void setGenres(ObservableField<String> genres) {
        this.genres = genres;
        this.genres.notifyChange();
    }

    public String getSeason(){
        return season.get();
    }
    public void setSeason(ObservableField<String> season) {
        this.season = season;
        this.season.notifyChange();
    }

    public String getNbEpisodes(){
        return nbEpisodes.get();
    }
    public void nbEpisodes(ObservableField<String> nbEpisodes) {
        this.nbEpisodes = nbEpisodes;
        this.nbEpisodes.notifyChange();
    }


    public void afterAnimeNameChange(CharSequence s)
    {
        this.anmName = s.toString();
        Log.d("Test", "name anime changed");
    }

    public void afterAnimeGenresChange(CharSequence s)
    {
        this.anmGenres = s.toString();
    }

    public void afterAnimeSeasonChange(CharSequence s)
    {
        this.anmSeason = s.toString();
    }

    public void afterAnimeNbEpChange(CharSequence s)
    {
        this.anmNbEpisodes = s.toString();
    }


    public void onClick(){

        Log.d("Test", "click on button in MV");

        AddAnime.MVVM_click(anmName, anmGenres, anmSeason, anmNbEpisodes);

    }


}
