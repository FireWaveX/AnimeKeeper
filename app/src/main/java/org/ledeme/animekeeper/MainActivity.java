package org.ledeme.animekeeper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ledeme.animekeeper.databinding.ContentActivityMainBinding;
import org.ledeme.animekeeper.databinding.HomepageBinding;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //var globales
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        MainMV mainMV = new MainMV();
        ((ContentActivityMainBinding) DataBindingUtil.setContentView(this, R.layout.content_activity_main))
                .setLoginVM(mainMV);

        //Set other intents/activity
        //final Intent LogginPage = new Intent(this, LogginActivity.class);

        mContext = this;

        /*
        Button start = findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startToast();
                startActivity(LogginPage);

            }
        });
        */
    }

    public static void MVVM_click(){

        final Intent LogginPage = new Intent(mContext, LogginActivity.class);
        mContext.startActivity(LogginPage);

    }

    public void startToast(){

        String message = getResources().getString(R.string.start_toast);
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}
