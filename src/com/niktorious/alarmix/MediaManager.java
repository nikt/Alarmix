package com.niktorious.alarmix;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

public class MediaManager
{
    private Context context;
    // standard path
    final String SD_PATH = new String(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MUSIC); /* /sdcard/ */
    
    private ArrayList<HashMap<String, String>> m_lstMedia = new ArrayList<HashMap<String, String>>();
    
    // constructor
    public MediaManager(Context context)
    {
        this.context = context;
    }
    
    // find all media using a content provider
    public void buildMediaList()
    {
        String[] request = {MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DISPLAY_NAME};
        
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cur = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, request, null, null, null);
        
        int ixName = cur.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int ixFile = cur.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext())
        {
            // add this file to the list
            HashMap<String, String> media = new HashMap<String, String>();
            
            // don't forget to account for file extension
            media.put("mediaTitle", cur.getString(ixName));
            
            // store path as well in case the file needs to be accessed later
            media.put("mediaPath",  cur.getString(ixFile));
            
            // add to mediaList
            m_lstMedia.add(media);
        }
        
        // clean up
        cur.close();
    }
    
    public ArrayList<HashMap<String, String>> getMediaList()
    {
        return m_lstMedia;
    }
}
