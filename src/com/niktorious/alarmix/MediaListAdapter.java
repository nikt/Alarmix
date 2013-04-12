package com.niktorious.alarmix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;


public class MediaListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> lstMedia = new ArrayList<HashMap<String, String>>();
    private boolean[] lstSelected;
    
    // constructor: pass in the list of media that you want to display
    public MediaListAdapter(Context context, ArrayList<HashMap<String, String>> lstMedia)
    {
        this.context     = context;
        this.lstMedia    = lstMedia;
        this.lstSelected = new boolean[lstMedia.size()];
        
        // note: Here lstSelected is initialized to be all false.
        //       In the future, it may be preferable to persist some of this information
        //       and restore it now.
        Arrays.fill(lstSelected, false);
    }

    // Adapter functions
    public int getCount() {
        return lstMedia.size();
    }
    public Object getItem(int ix) {
        return lstMedia.get(ix);
    }
    public long getItemId(int ix) {
        return ix;
    }
    
    public int getItemViewType(int ix) {
        return R.layout.medialist_item;
    }
    public View getView(int ix, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.medialist_item, parent, false);
        CheckBox chkMedia = (CheckBox) rowView.findViewById(R.id.chkSelected);
        chkMedia.setText(lstMedia.get(ix).get("mediaTitle"));
        chkMedia.setChecked(lstSelected[ix]);
        chkMedia.setClickable(false);
        return rowView;
    }
    
    public boolean isEmpty() {
        return lstMedia.isEmpty();
    }
    
    // Custom helper functions
    public void setSelected(int ix, boolean isSelected)
    {
        lstSelected[ix] = isSelected;
        //notifyDataSetChanged();
    }
    public boolean isSelected(int ix)
    {
        return lstSelected[ix];
    }
}
