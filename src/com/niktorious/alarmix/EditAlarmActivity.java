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
import android.widget.Toast;
import android.widget.ToggleButton;


public class EditAlarmActivity extends Activity
{
    final private int NUM_DAYS = 7;
    private Alarm m_alarm;
    private int   m_nHour;
    private int   m_nMinute;
    
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editalarm);
        
        // Get the alarm out of the intent
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        m_alarm = app.getAlarmById(getIntent().getIntExtra("targetId", 0));
        
        if (m_alarm == null)
        {
            Toast toast = Toast.makeText(this, "Oops, Couldn't find that alarm!", Toast.LENGTH_SHORT);
            toast.show();
            
            // Early out: not much to do without an alarm
            finish();
        }
        
        // Set the time picker to show the time stored in the alarm
        TimePicker timePicker = (TimePicker) findViewById(R.id.tpEditTime);
        
        timePicker.setCurrentHour(m_alarm.nHour);
        timePicker.setCurrentMinute(m_alarm.nMinute);
        
        m_nHour   = m_alarm.nHour;
        m_nMinute = m_alarm.nMinute;
        
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
    
    // Override onResume because the TimePicker does not repaint properly after an orientation change
    @Override
    public void onResume() {
        super.onResume();

        TimePicker timePicker = (TimePicker) findViewById(R.id.tpEditTime);
        
        int nHour   = timePicker.getCurrentHour();
        int nMinute = timePicker.getCurrentMinute();
        
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);
        timePicker.setCurrentHour(nHour);
        timePicker.setCurrentMinute(nMinute);
    }
    
    // Helpers
    private void handleClickSave()
    {
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        
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
        
        // Update the internal list of alarms
        // (only update if the time has been changed otherwise user might be surprised by and ordering change)
        if ((m_nHour != m_alarm.nHour) || (m_nMinute != m_alarm.nMinute))
        {
            //  Note: This seems quite slow... O(n) time
            //        Might be more efficient to bubble up/down
            app.deleteAlarmById(m_alarm.nId);
            app.addAlarm(m_alarm);
        }
        
        // Update the external list of alarms
        app.saveAlarmList(this, app.getModel().lstAlarms);
        
        // Set our alarm using the AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        alarmIntent.putExtra("alarmId", m_alarm.nId);
        
        // Create the corresponding PendingIntent object
        PendingIntent alarmPI = PendingIntent.getBroadcast(this, m_alarm.nId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        // Register the alarm with the alarm manager
        Calendar cal = m_alarm.getNextCalendar();
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmPI);
        
        // Return to ViewAlarmsActvity
        finish();
    }
    
    private void handleClickDelete()
    {
        // Create the corresponding PendingIntent object
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmPI = PendingIntent.getBroadcast(this, m_alarm.nId, alarmIntent, 0);
        
        // Cancel the alarm with the alarm manager
        alarmManager.cancel(alarmPI);
        
        // Delete the alarm from the global list
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        app.deleteAlarmById(m_alarm.nId);
        
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
