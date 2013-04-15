package com.niktorious.alarmix;

import android.provider.BaseColumns;


public class AlarmixContract
{
    // Table: media
    // +---------+------+------+
    // | mediaid | name | path |
    // +---------+------+------+
    public static abstract class AlarmixMedia implements BaseColumns
    {
        public static final String TABLE_NAME           = "Media";
        public static final String COLUMN_NAME_NAME     = "name";
        public static final String COLUMN_NAME_PATH     = "path";
    }
    
    // Table: alarm
    // +---------+------+--------+------+----------+
    // | alarmid | hour | minute | name | schedule |
    // +---------+------+--------+------+----------+
    // Note: schedule should be an unsigned char and treated as a bit flag
    //       unsigned char bits:  00000000
    //                    flags:  0MTWTFSS
    // TODO: consider using remaining unused bit as a one-time alarm flag
    // Note: SQL actually has a Time data type, maybe use that instead?
    public static abstract class AlarmixAlarm implements BaseColumns
    {
        public static final String TABLE_NAME           = "Alarm";
        public static final String COLUMN_NAME_HOUR     = "hour";
        public static final String COLUMN_NAME_MINUTE   = "minute";
        public static final String COLUMN_NAME_NAME     = "name";
        public static final String COLUMN_NAME_SCHEDULE = "schedule";
    }
    
    
    /* old implementation
    // Table: alarm
    // +------------+-----+-----+-----+-----+-----+-----+-----+
    // | scheduleid | mon | tue | wed | thu | fri | sat | sun |
    // +------------+-----+-----+-----+-----+-----+-----+-----+
    // Note: scheduleid referenced in the alarm table
    public static abstract class AlarmixSchedule implements BaseColumns
    {
        public static final String TABLE_NAME              = "schedule";
        public static final String COLUMN_NAME_SCHEDULE_ID = "scheduleid";
        public static final String COLUMN_NAME_MON         = "mon";
        public static final String COLUMN_NAME_TUE         = "tue";
        public static final String COLUMN_NAME_WED         = "wed";
        public static final String COLUMN_NAME_THU         = "thu";
        public static final String COLUMN_NAME_FRI         = "fri";
        public static final String COLUMN_NAME_SAT         = "sat";
        public static final String COLUMN_NAME_SUN         = "sun";
    }*/
    
    // Empty constructor: don't want anyone to instantiate this
    private AlarmixContract() {}
    
    
    private static final int MAX_PATH = 256;
    private static final int MAX_FILENAME_LENGTH = 128;
    private static final int MAX_NAME_LENGTH = 16;
    
    private static final String SQL_CREATE_MEDIA_TABLE = "CREATE TABLE " + AlarmixMedia.TABLE_NAME + " (" +
            AlarmixMedia._ID              + " INTEGER NOT NULL PRIMARY KEY, "                  +
            AlarmixMedia.COLUMN_NAME_NAME + " VARCHAR(" + MAX_FILENAME_LENGTH + ") NOT NULL, " +
            AlarmixMedia.COLUMN_NAME_PATH + " VARCHAR(" + MAX_PATH + ") NOT NULL)";
    
    private static final String SQL_DELETE_MEDIA_TABLE = "DROP TABLE IF EXISTS " +  AlarmixMedia.TABLE_NAME;
    
    private static final String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmixAlarm.TABLE_NAME + " (" +
            AlarmixAlarm._ID                  + " INTEGER NOT NULL PRIMARY KEY, "     +
            AlarmixAlarm.COLUMN_NAME_HOUR     + " INTEGER NOT NULL, "                 +
            AlarmixAlarm.COLUMN_NAME_MINUTE   + " INTEGER NOT NULL, "                 +
            AlarmixAlarm.COLUMN_NAME_NAME     + " VARCHAR(" + MAX_NAME_LENGTH + "), " +
            AlarmixAlarm.COLUMN_NAME_SCHEDULE + " CHAR(1) NOT NULL)";
    
    private static final String SQL_DELETE_ALARM_TABLE = "DROP TABLE IF EXISTS " +  AlarmixAlarm.TABLE_NAME;
}
