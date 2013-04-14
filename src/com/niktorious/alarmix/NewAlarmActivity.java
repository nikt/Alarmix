package com.niktorious.alarmix;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;


public class NewAlarmActivity extends Activity
{
    final private int NUM_DAYS = 7;
    
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newalarm);
        
        // Set the time picker to show the time + 1 hour
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpTime);
        
        Calendar cal = Calendar.getInstance(); 
        
        timePicker.setCurrentHour(cal.get(Calendar.HOUR) + 1);
        timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
        
        // Hook up the Create and Cancel buttons
        Button butCreate = (Button) findViewById(R.id.butCreate);
        Button butCancel = (Button) findViewById(R.id.butCancel);
        
        butCreate.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickCreate();
            }
        });
        
        butCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                // return to ViewAlarmsActvity
                finish();
            }
        });
    }
    
    private void handleClickCreate()
    {
        // Create new alarm
        Alarm alarm = new Alarm();
        
        // Populate dateTarget
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpTime);
        alarm.dateTarget.setHours(timePicker.getCurrentHour());
        alarm.dateTarget.setMinutes(timePicker.getCurrentMinute());
        
        // Populate strName
        TextView tvName = (TextView) findViewById(R.id.tvAlarmName);
        alarm.strName = (String) tvName.getText();
        
        // Populate fDayOfWeek
        ToggleButton[] togDay = new ToggleButton[NUM_DAYS];
        togDay[0] = (ToggleButton) findViewById(R.id.togMon);
        togDay[1] = (ToggleButton) findViewById(R.id.togTue);
        togDay[2] = (ToggleButton) findViewById(R.id.togWed);
        togDay[3] = (ToggleButton) findViewById(R.id.togThu);
        togDay[4] = (ToggleButton) findViewById(R.id.togFri);
        togDay[5] = (ToggleButton) findViewById(R.id.togSat);
        togDay[6] = (ToggleButton) findViewById(R.id.togSun);
        for (int i = 0; i < NUM_DAYS; i++)
        {
            alarm.fDayOfWeek[i] = togDay[i].isChecked();
        }
        
        // Add the new alarm to our list
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.getModel().lstAlarms.add(alarm);
        
        // return to ViewAlarmsActvity
        finish();
    }
}
