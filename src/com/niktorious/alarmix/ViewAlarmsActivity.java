package com.niktorious.alarmix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


public class ViewAlarmsActivity extends Activity
{
    private AlarmListAdapter m_listAdapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
        
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        m_listAdapter = new AlarmListAdapter(this, app.getModel().lstAlarms);
        
        ListView alarmListView = (ListView) findViewById(R.id.alarmListView);
        
        alarmListView.setAdapter(m_listAdapter);
        
        alarmListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int ix, long id) {
                handleClickAlarmList(ix);
            }
        });
        
        Button butNew  = (Button) findViewById(R.id.butNew);
        Button butDone = (Button) findViewById(R.id.butDone);
        
        butNew.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                // switch to NewAlarmActivity
                handleClickNewAlarm();
            }
        });
        
        butDone.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                // return to AlarmixActvity
                finish();
            }
        });
    }
    
    /** Called when the activity is resumed. */
    @Override
    protected void onResume()
    {
        super.onResume();
        m_listAdapter.notifyDataSetChanged();
    }
    
    // Helpers
    private void handleClickNewAlarm()
    {
        startActivity(new Intent(this, NewAlarmActivity.class));
    }
    
    private void handleClickAlarmList(int ix)
    {
        // start EditAlarmActivity for the selected alarm
        Intent data = new Intent(this, EditAlarmActivity.class);
        //data.putExtra("alarm", (Alarm) listAdapter.getItem(ix));
        data.putExtra("alarmIndex", ix);
        startActivity(data);
    }
}
