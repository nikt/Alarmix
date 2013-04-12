package com.niktorious.alarmix;

import android.app.Activity;
import android.os.Bundle;


public class ViewAlarmsActivity extends Activity {

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmlist);
    }
}
