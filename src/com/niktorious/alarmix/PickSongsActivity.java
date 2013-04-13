package com.niktorious.alarmix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;


public class PickSongsActivity extends Activity {
    
    private MediaListAdapter listAdapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medialist);
        
        MediaManager manager = new MediaManager(this);
        manager.buildMediaList();
        
        ListView mediaListView = (ListView) findViewById(R.id.mediaListView);
        
        listAdapter = new MediaListAdapter(this, manager.getMediaList()); /*manager.getMediaList()*/
        
        mediaListView.setAdapter(listAdapter);
        
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAll);
        Button butDone = (Button) findViewById(R.id.butDone);
        
        mediaListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int ix, long id) {
                // switch the selection state of the clicked item
                boolean isSelected = !listAdapter.isSelected(ix);
                listAdapter.setSelected(ix, isSelected);
                listAdapter.notifyDataSetChanged();
                
                // update state of the 'check all' checkbox
                // note: The 'check all' checkbox will never auto-update to be checked.
                //       It will only update to be unchecked if an item is unchecked.
                if (!isSelected)
                {
                    ListView mediaListView = (ListView) findViewById(R.id.mediaListView);
                    if (null != mediaListView)
                    {
                        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAll);
                        assert(false == isSelected);    // should never get here if isSelected is true
                        if (null != chkAll) chkAll.setChecked(isSelected);
                    }
                }
            }
        });
        
        // can't use OnCheckedChangeListener here because I don't want this to be triggered when
        // the user unchecks an item in the list which forces this to also uncheck
        chkAll.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ListView mediaListView = (ListView) findViewById(R.id.mediaListView);
                CheckBox chkAll = (CheckBox) view;
                // set all items in the list to match this state (check/uncheck)
                if (null != mediaListView && null != chkAll)
                {
                    for (int ix = 0; ix < mediaListView.getCount(); ix++)
                    {
                        listAdapter.setSelected(ix, chkAll.isChecked());
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        
        // old check all implementation
        /*chkAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ListView mediaListView = (ListView) findViewById(R.id.mediaListView);
                // set all items in the list to match this state (check/uncheck)
                if (null != mediaListView)
                {
                    for (int ix = 0; ix < mediaListView.getCount(); ix++)
                    {
                        listAdapter.setSelected(ix, isChecked);
                        listAdapter.notifyDataSetChanged();
                    }
                }
            }
        });*/
        
        butDone.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent data = new Intent();
                
                // fill data with the media the user has chosen
                
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

}
