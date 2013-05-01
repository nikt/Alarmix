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


public class EditAlarmActivity extends Activity
{
    final private int NUM_DAYS = 7;
    private int m_ix;
    private Alarm m_alarm;
    
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editalarm);
        
        // Get the alarm out of the intent
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        m_ix = getIntent().getIntExtra("alarmIndex", 0);
        m_alarm = app.getModel().lstAlarms.get(m_ix);
        
        // Set the time picker to show the time stored in the alarm
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpEditTime);
        
        timePicker.setCurrentHour(m_alarm.nHour);
        timePicker.setCurrentMinute(m_alarm.nMinute);
        
        // Set the Name of the alarm
        EditText fldAlarmName = (EditText) findViewById(R.id.fldEditAlarmName);
        fldAlarmName.setText(m_alarm.strName);
        
        // Set up the schedule toggle button states
        ToggleButton[] togDay = getScheduleToggleButtons();
        for (int i = 0; i < NUM_DAYS; i++)
        {
            togDay[i].setChecked(m_alarm.fDayOfWeek[i]);
        }
        
        // Hook up the Create and Cancel buttons
        Button butSave   = (Button) findViewById(R.id.butSave);
        Button butDelete = (Button) findViewById(R.id.butDelete);
        
        butSave.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickSave();
            }
        });
        
        butDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickDelete();
            }
        });
    }
    
    // Helpers
    private void handleClickSave()
    {
        // Populate dateTarget
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpEditTime);
        m_alarm.nHour   = timePicker.getCurrentHour();
        m_alarm.nMinute = timePicker.getCurrentMinute();
        
        // Populate strName
        EditText fldAlarmName = (EditText) findViewById(R.id.fldEditAlarmName);
        m_alarm.strName = fldAlarmName.getText().toString();
        
        // Populate fDayOfWeek
        ToggleButton[] togDay = getScheduleToggleButtons();
        for (int i = 0; i < NUM_DAYS; i++)
        {
            m_alarm.fDayOfWeek[i] = togDay[i].isChecked();
        }
        
        // Update the external list of alarms
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.saveAlarmList(this, app.getModel().lstAlarms);
        
        // Set our alarm using the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        
        // Create the corresponding PendingIntent object
        PendingIntent alarmPI = PendingIntent.getBroadcast(this, m_alarm.nId, alarmIntent, 0);
        
        // Register the alarm with the alarm manager
        Calendar cal = m_alarm.getNextCalendar();
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmPI);
        
        // Return to ViewAlarmsActvity
        finish();
    }
    
    private void handleClickDelete()
    {
        // Delete the alarm from the global list
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.getModel().lstAlarms.remove(m_ix);
        
        // Update the external list of alarms
        app.saveAlarmList(this, app.getModel().lstAlarms);
        
        // Return to ViewAlarmsActvity
        finish();
    }
    
    private ToggleButton[] getScheduleToggleButtons()
    {
        ToggleButton[] togDay = new ToggleButton[NUM_DAYS];
        togDay[0] = (ToggleButton) findViewById(R.id.togEditMon);
        togDay[1] = (ToggleButton) findViewById(R.id.togEditTue);
        togDay[2] = (ToggleButton) findViewById(R.id.togEditWed);
        togDay[3] = (ToggleButton) findViewById(R.id.togEditThu);
        togDay[4] = (ToggleButton) findViewById(R.id.togEditFri);
        togDay[5] = (ToggleButton) findViewById(R.id.togEditSat);
        togDay[6] = (ToggleButton) findViewById(R.id.togEditSun);
        return togDay;
    }

}
