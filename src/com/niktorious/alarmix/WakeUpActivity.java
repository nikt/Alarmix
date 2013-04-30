package com.niktorious.alarmix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class WakeUpActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wakeup);
        
        // Hook up the Snooze and Dismiss buttons
        Button butSnooze  = (Button) findViewById(R.id.butSnooze);
        Button butDismiss = (Button) findViewById(R.id.butDismiss);
        
        butSnooze.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickSnooze();
            }
        });
        
        butDismiss.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickDismiss();
            }
        });
    }
    
    @Override
    protected void onDestroy()
    {
        // clean up
        WakeLocker.release();
    }
    
    // Helpers
    private void handleClickSnooze()
    {
        // Set a new alarm 10 min from now
        finish();
        
        // clean up
        WakeLocker.release();
    }
    
    private void handleClickDismiss()
    {
        // do we need to set a new alarm? is this a one-shot alarm?
        finish();
        
        // clean up
        WakeLocker.release();
    }
}
