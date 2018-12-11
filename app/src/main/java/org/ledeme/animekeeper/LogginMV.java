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

    private ObservableField<String> username = new ObservableField<>();
    private ObservableField<String> passwd = new ObservableField<>();

    public String getUsername(){
        return username.get();
    }

    public String getPasswd(){
        return passwd.get();
    }

    LogginMV(){


    }

    public void onClick(){

        Log.i("tag", "U"+username.get());
        Log.i("tag", "M"+passwd.get());


        LogginActivity.MVVM_click(username.get(), passwd.get());

    }

}
