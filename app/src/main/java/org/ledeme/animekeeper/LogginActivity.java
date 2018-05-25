package org.ledeme.animekeeper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * loggin page
 */

public class LogginActivity extends AppCompatActivity {

    public static String USER_USERNAME;
    public static String USER_ID;

    Button btnHit;
    JSONArray txtJson;
    ProgressDialog pd;

    EditText login_txt;
    EditText mdp_txt;

    Boolean loginSucessful;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set other intents/activity
        final Intent MainPage = new Intent(this, MainPage.class);

        final EditText Lusername = (EditText) findViewById(R.id.input_loggin);
        Lusername.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        btnHit = findViewById(R.id.boutton_login);



        /* Get data from json */
        new JsonTask().execute("https://apex.oracle.com/pls/apex/anime_keeper/ak/getusers");

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login_txt = (EditText)findViewById(R.id.input_loggin);
                mdp_txt = (EditText)findViewById(R.id.input_mdp);

                String login = login_txt.getText().toString();
                String mdp = MD5(mdp_txt.getText().toString());


                JSONObject checkObj = null;
                loginSucessful = false;
                for (int itemIndex=0, totalObject = txtJson.length(); itemIndex < totalObject; itemIndex++) {

                    try {
                        checkObj = txtJson.getJSONObject(itemIndex);

                        if (checkObj.getString("loggin").equals(login) && checkObj.getString("passwd").equals(mdp)){ //

                            loginSucessful = true;
                            USER_USERNAME = login;
                            USER_ID = checkObj.getString("id");
                            startActivity(MainPage);
                            break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!loginSucessful){
                    failToLoginToast();
                }
            }
        });
    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public void failToLoginToast(){

        String message = getResources().getString(R.string.failed_login);
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    // ---------------------------------------------------------------------------------------------

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(LogginActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            JSONObject jsonobject = null;
            JSONArray jsonArray = null;
            try {
                jsonobject = new JSONObject(result);
                jsonArray = jsonobject.getJSONArray("items");
                Log.d("Info", "json has been successfully converted from string to JSONArray");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Info", "json has failed to be converted from string to JSONArray");
            }

            txtJson = jsonArray;


        }
    }
}
