package com.example.aron.comp304_teamproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // receive intents sent by sendBroadcast()
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        //override the onReceive to receive messages
        @Override
        public void onReceive(Context context, Intent intent) {
            //�-display the SMS received in the TextView-
            TextView SMSes = (TextView) findViewById(R.id.receive_sms);
            //display the content of the received message in text view
            SMSes.setText(intent.getExtras().getString("sms"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //music player
        Intent musicIntentService = new Intent(this, BackgroundMusicService.class);
        startService(musicIntentService);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        registerReceiver(intentReceiver,intentFilter);


        final ListView lvSettings = (ListView) findViewById(R.id.listView);
        //handle the item click event
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = null;
                String selectedSetting = (String) lvSettings.getItemAtPosition(position);
                //
                switch (position) {
                    case 0:
                        intent = new Intent(MainActivity.this, BMIActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("selectedSetting", selectedSetting);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MainActivity.this, CalorieIntakeActivity.class);
                        startActivity(intent);
                        break;
                }


            }
        });
    }


}
