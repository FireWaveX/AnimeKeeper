package org.ledeme.animekeeper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static org.ledeme.animekeeper.R.string.title_notif;

public class UserAccountActivity extends AppCompatActivity {

    String user_id;
    Button btnUpdateUser;
    String username;
    String newMdp;
    String CHANNEL_ID = "Anime channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        //Set other intents/activity
        final Intent mainPage = new Intent(this, MainPage.class);
        final Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        final EditText EnewMdp = (EditText)findViewById(R.id.user_mdp_input);
        final EditText Eusername = (EditText) findViewById(R.id.user_name_input);
        Eusername.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        Eusername.setText(LogginActivity.USER_USERNAME);

        user_id = LogginActivity.USER_ID;

        btnUpdateUser = findViewById(R.id.update_user);

        //---------------------------- NOTIFICATION

        createNotificationChannel();

        String title = getString(R.string.title_notif);
        String msg = getString(R.string.msg_notif);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.avat)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentTitle(title)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        //---------------------------- END NOTIFICATION


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.user_mdp_error);
        builder.setMessage(R.string.user_mdp_error_msg);
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });




        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Test", "click on button");

                username = Eusername.getText().toString();
                newMdp = LogginActivity.MD5(EnewMdp.getText().toString());

                String passwd1 = ((EditText) findViewById(R.id.user_mdp_input)).getText().toString();
                String passwd2 = ((EditText) findViewById(R.id.user_mdp_input_confirm)).getText().toString();

                if (passwd1.equals(passwd2)){
                    String url = "https://apex.oracle.com/pls/apex/anime_keeper/ak/postUser";

                    // Optional Parameters to pass as POST request
                    JSONObject js = new JSONObject();
                    try {
                        js.put("ID", user_id);
                        js.put("USERNAME", username);
                        js.put("PASSWORD", newMdp);
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
                    Volley.newRequestQueue(UserAccountActivity.this).add(jsonObjReq);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(UserAccountActivity.this);

                    // notificationId is a unique int for each notification that you must define
                    int notificationId = 1;
                    notificationManager.notify(notificationId, mBuilder.build());


                    startActivity(mainPage);
                }
                else{

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }





            }
        });



    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
