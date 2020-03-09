package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView usdText;
    TextView chfText;
    TextView jpyText;
    TextView cadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        usdText = findViewById(R.id.usdText);
        chfText = findViewById(R.id.chfText);
        jpyText = findViewById(R.id.jpyText);
        cadText = findViewById(R.id.cadText);
    }

    public void getRates(View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=b19519c824a82ef150dc901380bd0792&format=1";

            downloadData.execute(url);

        }catch (Exception e) {

        }

    }

    private class DownloadData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0){

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;

            }catch(Exception e){
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //System.out.println("alınan data: " + s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");

                JSONObject jsonObject1 = new JSONObject(rates);

                String turkishLira = jsonObject1.getString("TRY");
                tryText.setText("TRY : " + turkishLira);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD : " + usd);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD : " + cad);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF : " + chf);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY : " + jpy);



            } catch (Exception e){

            }
        }
    }


}
