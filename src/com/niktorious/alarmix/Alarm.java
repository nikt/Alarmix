package com.niktorious.alarmix;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

/** Holds data about each alarm created by the user */
public class Alarm implements Parcelable {
    public int       nHour;                         // which hour the alarm should go off (0-23) 1-24?
    public int       nMinute;                       // which minute the alarm should go off (0-59)
    public String    strName;                       // the name of the alarm (user-defined)
    public boolean[] fDayOfWeek = new boolean[7];   // boolean array holding which days of week the alarm should go off
    
    public Alarm()
    {
        this.nHour   = 0;
        this.nMinute = 0;
        this.strName = new String();
        Arrays.fill(this.fDayOfWeek, false);
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
