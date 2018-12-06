package com.example.aron.comp304_teamproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BMIActivity extends AppCompatActivity {

    public boolean isMetric = true;
    public TextView result;
    public EditText et_height_metric;
    public EditText et_height_feet;
    public EditText et_height_inch;
    public EditText et_weight;
    public float BMI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        RadioGroup rg_units = findViewById(R.id.units_system);
        final RadioButton rb_metric = findViewById(R.id.metric);
        final RadioButton rb_imperial = findViewById(R.id.imperial);
        final TextView height_label = findViewById(R.id.bmi_height_label);
        final TextView weight_label = findViewById(R.id.bmi_weight_label);
        et_height_metric = findViewById(R.id.bmi_height_metric);
        final TextView height_feet_label = findViewById(R.id.bmi_height_feet_label);
        et_height_feet = findViewById(R.id.bmi_height_feet);
        final TextView height_inch_label = findViewById(R.id.bmi_height_inch_label);
        et_height_inch = findViewById(R.id.bmi_height_inch);
        et_weight = findViewById(R.id.bmi_weight);
        result = findViewById(R.id.result);


        rg_units.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rb_metric.isChecked()) {
                    height_label.setText(R.string.bmi_height_metric);
                    weight_label.setText(R.string.bmi_weight_metric);
                    et_height_metric.setVisibility(View.VISIBLE);
                    height_feet_label.setVisibility(View.GONE);
                    et_height_feet.setVisibility(View.GONE);
                    height_inch_label.setVisibility(View.GONE);
                    et_height_inch.setVisibility(View.GONE);
                    clearAll();
                    isMetric = true;
                }
                if (rb_imperial.isChecked()) {
                    height_label.setText(R.string.bmi_height_imperial);
                    weight_label.setText(R.string.bmi_weight_imperial);
                    et_height_metric.setVisibility(View.GONE);
                    height_feet_label.setVisibility(View.VISIBLE);
                    et_height_feet.setVisibility(View.VISIBLE);
                    height_inch_label.setVisibility(View.VISIBLE);
                    et_height_inch.setVisibility(View.VISIBLE);
                    clearAll();
                    isMetric = false;
                }
            }
        });

        new DownloadImageTask().execute("https://i.imgur.com/L3hY6lj.png");


    }

    public void clearAll() {
        et_height_metric.setText("");
        et_height_feet.setText("");
        et_height_inch.setText("");
        et_weight.setText("");
    }

    public void calculateBMI(View view) {
        if (isMetric) {
            float weight = Float.parseFloat(et_weight.getText().toString());
            float height = Float.parseFloat(et_height_metric.getText().toString()) / 100;
            BMI = weight / (height * height);
        } else {
            float feetToInches = Float.parseFloat(et_height_feet.getText().toString()) * 12;
            float height = Float.parseFloat(et_height_inch.getText().toString()) + feetToInches;
            float weight = Float.parseFloat(et_weight.getText().toString());
            BMI = (weight / (height * height)) * 703;
        }
        result.setText(String.format("Your BMI is %.02f", BMI));
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            Log.d("NetworkingActivity", e1.getLocalizedMessage());
        }
        return bitmap;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return DownloadImage(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            ImageView img = findViewById(R.id.imageView);
            img.setImageBitmap(result);
        }
    }
}
