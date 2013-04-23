package com.niktorious.alarmix;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpNewTime);
        
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
    
    // Helpers
    private void handleClickCreate()
    {
        // Create new alarm
        Alarm alarm = new Alarm();
        
        // Populate dateTarget
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpNewTime);
        alarm.nHour   = timePicker.getCurrentHour();
        alarm.nMinute = timePicker.getCurrentMinute();
        
        // Populate strName
        EditText fldAlarmName = (EditText) findViewById(R.id.fldNewAlarmName);
        alarm.strName = fldAlarmName.getText().toString();
        
        // Populate fDayOfWeek
        ToggleButton[] togDay = getScheduleToggleButtons();
        for (int i = 0; i < NUM_DAYS; i++)
        {
            alarm.fDayOfWeek[i] = togDay[i].isChecked();
        }
        
        // Add the new alarm to our list
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.getModel().lstAlarms.add(alarm);
        
        // Save the updated alarm list
        app.saveAlarmList(this, app.getModel().lstAlarms);
        
        // return to ViewAlarmsActvity
        finish();
    }
    
    private ToggleButton[] getScheduleToggleButtons()
    {
        ToggleButton[] togDay = new ToggleButton[NUM_DAYS];
        togDay[0] = (ToggleButton) findViewById(R.id.togNewMon);
        togDay[1] = (ToggleButton) findViewById(R.id.togNewTue);
        togDay[2] = (ToggleButton) findViewById(R.id.togNewWed);
        togDay[3] = (ToggleButton) findViewById(R.id.togNewThu);
        togDay[4] = (ToggleButton) findViewById(R.id.togNewFri);
        togDay[5] = (ToggleButton) findViewById(R.id.togNewSat);
        togDay[6] = (ToggleButton) findViewById(R.id.togNewSun);
        return togDay;
    }
}
