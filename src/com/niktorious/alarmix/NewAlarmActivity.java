package com.niktorious.alarmix;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ToggleButton;


public class NewAlarmActivity extends Activity
{
    private final int NUM_DAYS = 7;
    
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newalarm);
        
        // Set the time picker to show the time + 1 hour
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpNewTime);
        
        Calendar cal = Calendar.getInstance(); 
        
        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY) + 1);
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
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpNewTime);
        EditText fldAlarmName = (EditText) findViewById(R.id.fldNewAlarmName);
        
        // Populate fDayOfWeek
        ToggleButton[] togDay = getScheduleToggleButtons();
        boolean[] fDayOfWeek = new boolean[NUM_DAYS];
        for (int i = 0; i < NUM_DAYS; i++)
        {
            fDayOfWeek[i] = togDay[i].isChecked();
        }
        
        // Create corresponding alarm
        Alarm alarm = new Alarm(app.getNewId(this),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute(),
                                fldAlarmName.getText().toString(),
                                fDayOfWeek);
        
        // Add the new alarm to our list
        app.addAlarm(alarm);
        
        // Save the updated alarm list
        app.saveAlarmList(this, app.getModel().lstAlarms);
        
        // Set our alarm using the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("alarmId", alarm.nId);
        
        // Create the corresponding PendingIntent object
        PendingIntent alarmPI = PendingIntent.getBroadcast(this, alarm.nId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // Register the alarm with the alarm manager
        Calendar cal = alarm.getNextCalendar();
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmPI);
        
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
