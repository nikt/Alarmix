package com.niktorious.alarmix;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class AlarmListAdapter extends BaseAdapter
{
    final private int NUM_DAYS = 7;
    private Context context;
    ArrayList<Alarm> lstAlarms;
    
 // constructor: pass in the list of alarms that you want to display
    public AlarmListAdapter(Context context, ArrayList<Alarm> lstAlarms)
    {
        this.context   = context;
        this.lstAlarms = lstAlarms;
    }

    // Adapter functions
    public int getCount()
    {
        return lstAlarms.size();
    }
    public Object getItem(int ix)
    {
        return lstAlarms.get(ix);
    }
    public long getItemId(int ix)
    {
        return ix;
    }
    public View getView(int ix, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.alarmlist_item, parent, false);
        
        Alarm alarm = (Alarm) getItem(ix);
        
        // Set the time to display correctly
        TextView tvTime = (TextView) rowView.findViewById(R.id.tvTime);
        
        String display = new String();
        int hours   = alarm.dateTarget.getHours();
        int minutes = alarm.dateTarget.getMinutes();
        boolean fAM = true;
        
        if (hours > 12)
        {
            fAM = false;
            hours %= 12;
        }
        
        display  = Integer.toString(hours);
        display += ":";
        if (minutes < 10) display += "0";
        display += Integer.toString(minutes);
        display += " ";
        display += fAM ? "AM" : "PM";

        tvTime.setText(display);
        
        // Set up the days of week to display correctly
        TextView[] tvDaysOfWeek = new TextView[NUM_DAYS];
        tvDaysOfWeek[0] = (TextView) rowView.findViewById(R.id.tvMon);
        tvDaysOfWeek[1] = (TextView) rowView.findViewById(R.id.tvTue);
        tvDaysOfWeek[2] = (TextView) rowView.findViewById(R.id.tvWed);
        tvDaysOfWeek[3] = (TextView) rowView.findViewById(R.id.tvThu);
        tvDaysOfWeek[4] = (TextView) rowView.findViewById(R.id.tvFri);
        tvDaysOfWeek[5] = (TextView) rowView.findViewById(R.id.tvSat);
        tvDaysOfWeek[6] = (TextView) rowView.findViewById(R.id.tvSun);
        
        for(int i = 0; i < NUM_DAYS; i++)
        {
            if (alarm.fDayOfWeek[i])
            {
                tvDaysOfWeek[i].setTextColor(context.getResources().getColor(R.color.LimeGreen));
            }
            else
            {
                tvDaysOfWeek[i].setTextColor(context.getResources().getColor(R.color.LightGrey));
            }
        }
        
        return rowView;
    }
    
    // Helpers
    public boolean isEmpty()
    {
        return lstAlarms.isEmpty();
    }

}
