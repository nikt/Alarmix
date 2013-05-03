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
        
        int nId = intent.getIntExtra("alarmId", -1);
        
        Intent i = new Intent(context, WakeUpActivity.class);
        
        i.putExtra("alarmId", nId);
        
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK   |
                   Intent.FLAG_ACTIVITY_SINGLE_TOP |
                   Intent.FLAG_ACTIVITY_CLEAR_TOP  |
                   Intent.FLAG_ACTIVITY_NO_HISTORY);
        
        context.startActivity(i);
    }
}
