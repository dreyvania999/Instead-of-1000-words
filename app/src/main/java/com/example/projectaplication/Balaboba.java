package com.example.projectaplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Balaboba extends AppCompatActivity implements View.OnClickListener {

    public static String Str;
    TextView textView;
    ImageButton btnGoBack;
    private ProgressBar progressBarBalaboba;
    private EditText editTextTextPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balaboba);
        btnGoBack = findViewById(R.id.btnGoBackBalaboba);
        textView = findViewById(R.id.textFromBalaboba);
        editTextTextPass = findViewById(R.id.editTextTextPassword);
        progressBarBalaboba = findViewById(R.id.progressBarBalaboba);
        editTextTextPass.setText(Str);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGenerate:
                progressBarBalaboba.setVisibility(View.VISIBLE);
                progressBarBalaboba.setIndeterminate(true);
                textView.setVisibility(View.INVISIBLE);
                String SearchString = editTextTextPass.getText().toString();
                new GetCompliment().execute(SearchString);
                break;

            case R.id.btnGoBackBalaboba:
                Intent new1 = new Intent(this, MainActivity.class);
                startActivity(new1);
                break;
        }
    }

    private class GetCompliment extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... Strings1) {
            try {
                //connections settings

                String data = "{\"query\": \"" + Strings1[0] + "\", \"intro\": 0, \"filter\": 1}";
                URL url = new URL("https://zeapi.yandex.net/lab/api/yalm/text3");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("'Origin'", "https://yandex.ru");
                con.setRequestProperty("'Referer'", "https://yandex.ru/");

                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(data);
                writer.flush();

                Scanner reader = new Scanner(new InputStreamReader(con.getInputStream()));
                String response;
                response = reader.nextLine();
                return response;
            } catch (Exception e) {
                return "Запрос не отработал";
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != "Запрос не отработал") {
                String str = "что-то пошло не по плану";
                int osh = 0;
                try {
                    JSONObject obj = new JSONObject(s);
                    str = obj.getString("query");
                    str += "\n";
                    str += obj.getString("text");

                    osh = obj.getInt("bad_query");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (osh == 0) {
                    textView.setText(str);
                } else {
                    textView.setText("Запрос не отработал, потому что введено неприемлимое содержание, затронуты личные границы человека или религия и политика");
                }
            } else {
                textView.setText(s);
            }
            progressBarBalaboba.setVisibility(View.GONE);
            progressBarBalaboba.setIndeterminate(false);
            textView.setVisibility(View.VISIBLE);
        }
    }
}