package com.niktorious.alarmix;

import java.util.Arrays;
import java.util.Date;

/** Holds data about each alarm created by the user */
public class Alarm {
    public Date      dateTarget;                    // when the alarm should go off
    public String    strMediaPath;                  // which media the alarm should play
    public boolean[] fDayOfWeek = new boolean[7];   // boolean array holding which days of week the alarm should go off
    
    public Alarm()
    {
        Arrays.fill(this.fDayOfWeek, false);
    }
}
