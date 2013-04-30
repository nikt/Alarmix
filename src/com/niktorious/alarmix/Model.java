package com.niktorious.alarmix;

import java.util.ArrayList;


public class Model {
    public int gId; // sadly, Java does not support unsigned ints D:
    public ArrayList<String> lstMediaPaths = new ArrayList<String>();
    public ArrayList<Alarm>  lstAlarms     = new ArrayList<Alarm>();
}
