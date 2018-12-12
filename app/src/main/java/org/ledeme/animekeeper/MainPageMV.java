package org.ledeme.animekeeper;

import android.databinding.ObservableField;

public class MainPageMV {

    private ObservableField<String> username = new ObservableField<>( "Welcome "+ LogginActivity.USER_USERNAME);

    public String getUsername(){
        return username.get();
    }
    public void setUsername(ObservableField<String> username) {
        this.username = username;
        this.username.notifyChange();
    }

    MainPageMV() {}

    public void onClick(){

        MainPage.MVVM_click();

    }



}
