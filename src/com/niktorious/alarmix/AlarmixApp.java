package com.niktorious.alarmix;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;


public class AlarmixApp extends Application {
    private final String FILE_NAME_SELECTED_MEDIA = "selectedMedia";
    private final char LIST_DELIMITER = '|';  // not sure which character is best to use here
    private Model model;
    
    public AlarmixApp()
    {
        model = new Model();
    }
    
    public Model getModel()
    {
        return model;
    }
    
    // This function is used to save the list of selected media to internal storage
    public void saveSelectedMedia(Context context, ArrayList<String> lstMedia)
    {
        model.lstMediaPaths = lstMedia;
        
        try
        {
            FileOutputStream os = context.openFileOutput(FILE_NAME_SELECTED_MEDIA, MODE_PRIVATE);
            
            // build the string which will be written to this stream
            String strOutput = new String(); 
            for (String filePath : lstMedia)
            {
                if (!strOutput.isEmpty())
                {
                    strOutput += LIST_DELIMITER;
                }
                strOutput += filePath;
            }
            
            // write to the output stream
            os.write(strOutput.getBytes());
            
            // clean up
            os.close();
        }
        catch (FileNotFoundException e)
        {
            // please make sure you are calling this by passing in an Activity context...
            e.printStackTrace();
        } catch (IOException e) {
            // something went wrong when writing to file?
            e.printStackTrace();
        }
    }
    
    // This function is used to load the list of selected media from internal storage
    // This should only be called from AlarmixActivity
    public void loadSelectedMedia(Context context)
    {
        try
        {
            BufferedInputStream is = new BufferedInputStream(context.openFileInput(FILE_NAME_SELECTED_MEDIA));
            
            // read form the stream to build the media list
            ArrayList<String> lstMedia = new ArrayList<String>();
            String strBuilder = new String();
            while (true)
            {
                int i = is.read();
                
                // exit the loop if we are done reading from the stream
                if (-1 == i) break;
                
                char c = (char) i;
                if (LIST_DELIMITER == c)
                {
                    if (!strBuilder.isEmpty()) lstMedia.add(strBuilder);
                    strBuilder = new String();
                }
                else
                {
                    strBuilder += c;
                }
            }
            
            model.lstMediaPaths = lstMedia;
            
            // clean up
            is.close();
        }
        catch (FileNotFoundException e)
        {
            // maybe the file hasn't been created yet
            // in that case, leave model.lstMediaPaths as an empty list
            e.printStackTrace();
        } catch (IOException e) {
            // something went wrong when reading from file?
            e.printStackTrace();
        }
    }
}
