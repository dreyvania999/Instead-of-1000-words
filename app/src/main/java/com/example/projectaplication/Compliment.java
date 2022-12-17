package com.example.projectaplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class Compliment extends AppCompatActivity implements View.OnClickListener {

    private TextView textFromApi, textMyText;
    private Spinner spin, spinsex;
    private ProgressBar progressBarCompliment;
    private ImageButton btnGoBack;
    private int pol = 2, type = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compliment);
        textFromApi = findViewById(R.id.textFromApi);
        textMyText = findViewById(R.id.textMyText);
        spin = findViewById(R.id.spinnerTextIs);
        spinsex = findViewById(R.id.spinnerSex);
        btnGoBack = findViewById(R.id.btnGoBackCompliment);
        progressBarCompliment = findViewById(R.id.progressBarCompliment);
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
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = 2 - position;
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
            case R.id.btnGenerateNew:
                progressBarCompliment.setVisibility(View.VISIBLE);
                progressBarCompliment.setIndeterminate(true);
                textFromApi.setVisibility(View.INVISIBLE);
                new GetCompliment().execute();
                textMyText.setText(ComplimentsSituation.GetComplimentsSituation());
                break;
            case R.id.btnGoBackCompliment:
                Intent new1 = new Intent(this, MainActivity.class);
                startActivity(new1);
                break;

        }
    }

    private class GetCompliment extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {

                URL url = new URL("http://free-generator.ru/generator.php?action=compliment&pol=" + pol + "&type=" + type);
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

                textFromApi.setText(new JSONObject(productJson.getString("compliment")).getString("compliment"));
                progressBarCompliment.setVisibility(View.GONE);
                progressBarCompliment.setIndeterminate(false);
                textFromApi.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}