package org.ledeme.animekeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main page for the app
 */

public class MainPage extends AppCompatActivity{

    Button btnAnimeList;
    Intent mainAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        //Set other intents/activity
        final Intent listAnime = new Intent(this, ListOfAnime.class);
        mainAct = new Intent(this, MainActivity.class);

        btnAnimeList = findViewById(R.id.btnGoToAnimeList);

        TextView msgWelcome =  findViewById(R.id.headerTextMainPage);
        String msg = getString(R.string.textView1);
        msgWelcome.setText(msg +" "+ LogginActivity.USER_USERNAME);

        btnAnimeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(listAnime);

            }
        });
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(R.string.GoodBye);

        // set dialog message
        alertDialogBuilder
                .setMessage(R.string.exit_msg)
                .setCancelable(false)
                .setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        startActivity(mainAct);

                        MainPage.this.finish();
                    }
                })
                .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        //nothings

                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final Intent userAccount = new Intent(this, UserAccountActivity.class);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_find_manga_store:

                //maps avec les magasins de manga les + proches

                return true;
            case R.id.action_user:

                startActivity(userAccount);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
