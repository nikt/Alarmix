package com.niktorious.alarmix;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;


public class MediaListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<HashMap<String, String>> m_lstMedia = new ArrayList<HashMap<String, String>>();
    private boolean[] m_lstSelected;
    
    // constructor: pass in the list of media that you want to display
    public MediaListAdapter(Context context, ArrayList<HashMap<String, String>> lstMedia)
    {
        this.context       = context;
        this.m_lstMedia    = lstMedia;
        this.m_lstSelected = new boolean[lstMedia.size()];
        
        // note: Here lstSelected is initialized to be all false.
        //       In the future, it may be preferable to persist some of this information
        //       and restore it now.
        //Arrays.fill(m_lstSelected, false);
        // note: this is probably pretty slow in general
        //       O(n*m) where n is m_lstMedia.size()
        //              and m is lstSelectedMedia.size()
        AlarmixApp app = (AlarmixApp) context.getApplicationContext();
        ArrayList<String> lstSelectedMedia = app.getModel().lstMediaPaths;
        for (int ix = 0; ix < m_lstMedia.size(); ix++)
        {
            m_lstSelected[ix] = lstSelectedMedia.contains(m_lstMedia.get(ix).get("mediaPath"));
        }
    }

    // Adapter functions
    public int getCount()
    {
        return m_lstMedia.size();
    }
    public Object getItem(int ix)
    {
        return m_lstMedia.get(ix);
    }
    public long getItemId(int ix)
    {
        return ix;
    }
    
    public int getItemViewType(int ix)
    {
        return R.layout.medialist_item;
    }
    public View getView(int ix, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.medialist_item, parent, false);
        CheckBox chkMedia = (CheckBox) rowView.findViewById(R.id.chkSelected);
        chkMedia.setText(m_lstMedia.get(ix).get("mediaTitle"));
        chkMedia.setChecked(m_lstSelected[ix]);
        chkMedia.setClickable(false);
        return rowView;
    }
    
    // Custom helper functions
    public boolean isEmpty()
    {
        return m_lstMedia.isEmpty();
    }
    public void setSelected(int ix, boolean isSelected)
    {
        m_lstSelected[ix] = isSelected;
    }
    public boolean isSelected(int ix)
    {
        return m_lstSelected[ix];
    }
    public ArrayList<String> getFileList()
    {
        ArrayList<String> lstFiles = new ArrayList<String>();
        
        for (int ix = 0; ix < m_lstMedia.size(); ix++)
        {
            if (m_lstSelected[ix])
            {
                lstFiles.add(m_lstMedia.get(ix).get("mediaPath"));
            }
        }
        
        return lstFiles;
    }
}
