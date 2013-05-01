package com.niktorious.alarmix;

import java.util.Arrays;
import java.util.Calendar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

/** Holds data about each alarm created by the user */
public class Alarm implements Parcelable {
    private final int NUM_DAYS = 7;
    
    public int       nId;
    public int       nHour;                                // which hour the alarm should go off
    public int       nMinute;                              // which minute the alarm should go off
    public String    strName;                              // the name of the alarm (user-defined)
    public boolean[] fDayOfWeek = new boolean[NUM_DAYS];   // boolean array holding which days of week the alarm should go off
    
    public Alarm(Context context)
    {
        AlarmixApp app = (AlarmixApp) context.getApplicationContext();
        
        this.nId     = app.getNewId(context);
        this.nHour   = 0;
        this.nMinute = 0;
        this.strName = new String();
        Arrays.fill(this.fDayOfWeek, false);
    }
    
    public Alarm(int nId, int nHour, int nMinute, String strName, boolean[] fDayOfWeek)
    {
        this.nId        = nId;
        this.nHour      = nHour;
        this.nMinute    = nMinute;
        this.strName    = strName;
        this.fDayOfWeek = fDayOfWeek;
    }
    
    public Calendar getNextCalendar()
    {
        Calendar cal = Calendar.getInstance();
        
        cal.set(Calendar.HOUR_OF_DAY, nHour  );
        cal.set(Calendar.MINUTE,      nMinute);
        cal.set(Calendar.SECOND,      0      );
        
        if (cal.after(Calendar.getInstance()))
        {
            // Early out: if the alarm is set for later today
            return cal;
        }
        else
        {
            // Finding the current day is a little tricky:
            //  The Calendar object enumerates the days from Sunday = 1 to Saturday = 7
            //  The Calendar value for Monday is 2, but I treat Monday as 0
            final int nCurrentDay = (cal.get(Calendar.DAY_OF_WEEK) + 5) % NUM_DAYS;
            int ix;

            // Find the next day that this alarm should go off
            for (ix = (nCurrentDay + 1) % NUM_DAYS; ix != nCurrentDay; ix = (ix + 1) % NUM_DAYS)
            {
                cal.add(Calendar.DATE, 1);
                if (fDayOfWeek[ix]) break;
            }
            
            if (ix == nCurrentDay)
            {
                // If the user didn't set any schedule, treat this as a one-time alarm
                return null;
            }
        }
        
        return cal;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(nHour);
        dest.writeInt(nMinute);
        dest.writeString(strName);
        dest.writeBooleanArray(fDayOfWeek);
    }
    
    public static final Parcelable.Creator<Alarm> CREATOR = new Parcelable.Creator<Alarm>() {
        public Alarm createFromParcel(Parcel src)
        {
            return new Alarm(src);
        }
        
        public Alarm[] newArray(int size)
        {
            return new Alarm[size];
        }
    };
    
    private Alarm(Parcel src)
    {
        nHour   = src.readInt();
        nMinute = src.readInt();
        strName = src.readString();
        src.readBooleanArray(fDayOfWeek);
    }
}
