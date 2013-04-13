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
    
    private ArrayList<HashMap<String, String>> lstMedia = new ArrayList<HashMap<String, String>>();
    
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
            lstMedia.add(media);
        }
        
        // clean up
        cur.close();
    }
    
    public ArrayList<HashMap<String, String>> getMediaList()
    {
        return lstMedia;
    }
    
    
    // old implementation
    /*
    // find all media satisfying FileExtensionFilter and store in mediaList 
    public void buildMediaList()
    {
        File home = new File(SD_PATH);
        
        if (home != null && home.isDirectory()) {
            lstMedia = getDirectoryContents(home);
        }
    }
    
    // helper recursive function to find all media files
    // depth first search
    private ArrayList<HashMap<String, String>> getDirectoryContents(File root)
    {
        ArrayList<HashMap<String, String>> lst = new ArrayList<HashMap<String, String>>();
        
        if (root.isDirectory())
        {
            for (File file : root.listFiles(new MusicFileFilter())) {
                lst.addAll(getDirectoryContents(file));
            }
        }
        else
        {
            // add this file to the list
            HashMap<String, String> media = new HashMap<String, String>();
            
            // don't forget to account for file extension
            media.put("mediaTitle", root.getName().substring(0, (root.getName().lastIndexOf('.'))));
            
            // store path as well in case the file needs to be accessed later
            media.put("mediaPath",  root.getPath());
            
            // add to mediaList
            lst.add(media);
        }
        
        return lst;
    }
    
    // update this to include other types of media/file extensions
    // should first make all lower case, then compare (less extensions this way)
    class MusicFileFilter implements FileFilter
    {
        public boolean accept(File pathname) {
            return (pathname.isDirectory()              ||
                    pathname.getName().endsWith(".mp3") ||
                    pathname.getName().endsWith(".MP3"));
        }
    }*/
}
