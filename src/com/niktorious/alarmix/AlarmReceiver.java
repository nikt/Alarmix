package com.niktorious.alarmix;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Acquire wake lock
        WakeLocker.acquire(context);
        
        Intent i = new Intent(context, WakeUpActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        context.startActivity(i);
    }
}
