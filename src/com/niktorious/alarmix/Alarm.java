package com.niktorious.alarmix;

import java.util.Arrays;
import java.util.Date;

/** Holds data about each alarm created by the user */
public class Alarm {
    public Date      dateTarget;                    // when the alarm should go off
    public String    strName;                       // the name of the alarm (user-defined)
    public boolean[] fDayOfWeek = new boolean[7];   // boolean array holding which days of week the alarm should go off
    
    public Alarm()
    {
        this.dateTarget = new Date();
        this.strName    = new String();
        Arrays.fill(this.fDayOfWeek, false);
    }
}
