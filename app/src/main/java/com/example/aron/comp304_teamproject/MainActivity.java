package com.example.aron.comp304_teamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //music player
        Intent musicIntentService  = new Intent(this, BackgroundMusicService.class);
        startService(musicIntentService);



        final ListView lvSettings=(ListView)findViewById(R.id.listView);
        //handle the item click event
        lvSettings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent=null;
                String selectedSetting = (String)lvSettings.getItemAtPosition(position);
                //
                switch (position)
                {
                    case 0:

                        break;
                    case 1:
                        intent = new Intent(MainActivity.this,MapsActivity.class);
                        intent.putExtra("selectedSetting",selectedSetting );
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent (MainActivity.this, CalorieIntakeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:

                        break;

                }


            }
        });
    }

}
