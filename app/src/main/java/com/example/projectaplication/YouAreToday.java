package com.example.projectaplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YouAreToday extends AppCompatActivity implements View.OnClickListener {
    Button buttonAI, buttonStart;
    int predsk = 0;
    TextView textProgress, textYouAreToday;
    private ProgressBar progressYouAreToday;
    private ImageButton btnGoBack;
    private Spinner spinsex, spinnerTextIs;
    private int pol = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_are_today);
        buttonAI = findViewById(R.id.buttonAI);
        buttonAI.setVisibility(View.INVISIBLE);
        spinnerTextIs = findViewById(R.id.spinnerTextIs2);
        buttonStart = findViewById(R.id.buttonStart);
        btnGoBack = findViewById(R.id.btnGoBackYouAreToday);
        spinsex = findViewById(R.id.spinnerSex2);
        textYouAreToday = findViewById(R.id.textYouAreToday);
        textYouAreToday.setVisibility(View.INVISIBLE);
        textProgress = findViewById(R.id.textProgress);
        textProgress.setVisibility(View.INVISIBLE);
        progressYouAreToday = findViewById(R.id.progressYouAreToday);
        progressYouAreToday.setVisibility(View.INVISIBLE);
        spinsex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                pol = 2 - position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAI:

                Intent new12 = new Intent(this, Balaboba.class);
                Balaboba.Str = textYouAreToday.getText().toString();
                startActivity(new12);
                break;
            case R.id.btnGoBackYouAreToday:
                Intent new1 = new Intent(this, MainActivity.class);
                startActivity(new1);
                break;
            case R.id.buttonStart:
                textYouAreToday.setVisibility(View.VISIBLE);
                buttonStart.setVisibility(View.INVISIBLE);
                progressYouAreToday.setVisibility(View.VISIBLE);
                progressYouAreToday.setIndeterminate(true);
                textProgress.setVisibility(View.VISIBLE);

                setInform();
                break;
        }
    }

    public void setInform() {
        new GetComplimentDay().execute();

        new GetComplimentDay().execute();

        new GetComplimentDay().execute();


        progressYouAreToday.setVisibility(View.GONE);
        progressYouAreToday.setIndeterminate(false);
        textProgress.setVisibility(View.INVISIBLE);
        buttonAI.setVisibility(View.VISIBLE);
    }

    private class GetComplimentDay extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {

                URL url = new URL("http://free-generator.ru/generator.php?action=compliment&pol=" + pol + "&type=1");
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
                String str = textYouAreToday.getText().toString() + " ";
                str += new JSONObject(productJson.getString("compliment")).getString("compliment");
                textYouAreToday.setText(str);

                predsk++;
                textProgress.setText("Количество предсказаний в поиске " + (3 - predsk));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}