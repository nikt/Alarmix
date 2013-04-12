package com.niktorious.alarmix;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Environment;

public class MediaManager {
    // standard path
    final String SD_PATH = new String(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MUSIC); /* /sdcard/ */
    
    private ArrayList<HashMap<String, String>> lstMedia = new ArrayList<HashMap<String, String>>();
    
    // constructor
    public MediaManager() {
        // do nothing
    }
    
    // find all media satisfying FileExtensionFilter and store in mediaList 
    public void buildMediaList() {
        System.out.println("start building list: " + SD_PATH);
        File home = new File(SD_PATH);
        
        if (home != null && home.isDirectory()) {
            lstMedia = getDirectoryContents(home);
        }
    }
    
    // helper recursive function to find all media files
    // depth first search
    private ArrayList<HashMap<String, String>> getDirectoryContents(File root) {
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
    
    public ArrayList<HashMap<String, String>> getMediaList() {
        return lstMedia;
    }
    
    // update this to include other types of media/file extensions
    // should first make all lower case, then compare (less extensions this way)
    class MusicFileFilter implements FileFilter {
        public boolean accept(File pathname) {
            return (pathname.isDirectory()              ||
                    pathname.getName().endsWith(".mp3") ||
                    pathname.getName().endsWith(".MP3"));
        }
    }
}
