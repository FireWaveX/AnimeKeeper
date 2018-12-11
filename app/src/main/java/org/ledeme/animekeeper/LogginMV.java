package org.ledeme.animekeeper;

import android.content.Intent;
import android.databinding.ObservableField;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class LogginMV {

    private ObservableField<String> username = new ObservableField<>("");
    private ObservableField<String> passwd = new ObservableField<>("");

    private String usrNm;
    private String mdp;

    public String getUsername(){
        return username.get();
    }
    public void setUsername(ObservableField<String> username) {
        this.username = username;
        this.username.notifyChange();
    }

    public String getPasswd(){
        return passwd.get();
    }
    public void setPasswd(ObservableField<String> passwd) {
        this.passwd = passwd;
    }

    LogginMV(){}

    public void onClick(){

        LogginActivity.MVVM_click(this.usrNm, this.mdp);

    }

    public void afterUserNameChange(CharSequence s)
    {
        //Log.i("truc", s.toString());
        //username.setName(s.toString());
        this.usrNm = s.toString();
    }

    public void afterPassWordhange(CharSequence s)
    {
        //Log.i("truc", s.toString());
        //username.setName(s.toString());
        this.mdp = s.toString();
    }

}
