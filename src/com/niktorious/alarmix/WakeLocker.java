package com.niktorious.alarmix;

import android.content.Context;
import android.os.PowerManager;


public class WakeLocker
{
    private static final String LOCK_NAME_STATIC = "com.niktorious.alarmix.static";
    private static PowerManager.WakeLock m_wakeLock;

    public static void acquire(Context context)
    {
        release();

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        m_wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK        |
                                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                                    PowerManager.ON_AFTER_RELEASE,
                                    LOCK_NAME_STATIC);
        m_wakeLock.acquire();
    }

    public static void release()
    {
        if (m_wakeLock != null) m_wakeLock.release();
        m_wakeLock = null;
    }
}
