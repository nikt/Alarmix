package com.niktorious.alarmix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlarmixActivity extends Activity
{
    //private final int MEDIA_LIST_REQUEST = 1;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.loadSelectedMedia(this);
        app.loadAlarmList(this);
        
        Button butView = (Button) findViewById(R.id.butViewAlarms);
        Button butPick = (Button) findViewById(R.id.butPickSongs);
        
        butView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // switch to ViewAlarmsActivity
                handleClickViewAlarms();
            }
        });
        
        butPick.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // switch to PickSongsActivity
                handleClickPickSongs();
            }
        });
    }
    
    private void handleClickPickSongs()
    {
        startActivity(new Intent(this, PickSongsActivity.class));
        //startActivityForResult(new Intent(this, PickSongsActivity.class), MEDIA_LIST_REQUEST);
    }
    
    private void handleClickViewAlarms()
    {
        startActivity(new Intent(this, ViewAlarmsActivity.class));
    }
}