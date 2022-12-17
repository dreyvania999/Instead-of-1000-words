package com.example.projectaplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Desecration extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnGoBack;
    Button btnGetNew;
    private TextView textView;
    private ProgressBar progressBarDesecration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desecration);
        btnGoBack = findViewById(R.id.btnGoBackDesecration);
        textView = findViewById(R.id.textView);
        progressBarDesecration = findViewById(R.id.progressBarDesecration);
        btnGetNew = findViewById(R.id.btnGetNew);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoBackDesecration:
                Intent new1 = new Intent(this, MainActivity.class);
                startActivity(new1);
                break;

            case R.id.btnGetNew:
                progressBarDesecration.setVisibility(View.VISIBLE);
                progressBarDesecration.setIndeterminate(true);
                textView.setVisibility(View.INVISIBLE);
                new GetDesecration().execute();
                break;
        }
    }

    private class GetDesecration extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {

                URL url = new URL("https://evilinsult.com/generate_insult.php?lang=ru&type=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject productJson = new JSONObject(s);

                textView.setText(productJson.getString("insult"));
                progressBarDesecration.setVisibility(View.GONE);
                progressBarDesecration.setIndeterminate(false);
                textView.setVisibility(View.VISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}