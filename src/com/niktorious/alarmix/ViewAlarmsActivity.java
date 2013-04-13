package com.niktorious.alarmix;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;


public class ViewAlarmsActivity extends Activity {
    
    private AlarmListAdapter listAdapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
        
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        listAdapter = new AlarmListAdapter(this, app.getModel().lstAlarms);
        
        ListView alarmListView = (ListView) findViewById(R.id.alarmListView);
        
        alarmListView.setAdapter(listAdapter);
    }
}
