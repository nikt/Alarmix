package com.niktorious.alarmix;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;


public class AlarmixApp extends Application {
    private final String FILE_NAME_SELECTED_MEDIA = "selectedMedia";
    private final String FILE_NAME_ALARM_LIST     = "alarmList";
    private final char   LIST_DELIMITER           = '\n';  // not sure which character is best to use here
    private final char   DATA_DELIMITER           = ';';  // not sure which character is best to use here
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
        }
        catch (IOException e)
        {
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
            FileInputStream is = context.openFileInput(FILE_NAME_SELECTED_MEDIA);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            // read form the stream to build the media list
            ArrayList<String> lstMedia = new ArrayList<String>();
            String line;
            while ((line = br.readLine()) != null)
            {
                if (!line.isEmpty()) lstMedia.add(new String(line));
            }
            
            model.lstMediaPaths = lstMedia;
            
            // clean up
            br.close();
            isr.close();
            is.close();
        }
        catch (FileNotFoundException e)
        {
            // maybe the file hasn't been created yet
            // in that case, leave model.lstMediaPaths as an empty list
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // something went wrong when reading from file?
            e.printStackTrace();
        }
    }
    
    // This function is used to save the list of alarms to internal storage
    public void saveAlarmList(Context context, ArrayList<Alarm> lstAlarms)
    {
        model.lstAlarms = lstAlarms;
        
        try
        {
            FileOutputStream os = context.openFileOutput(FILE_NAME_ALARM_LIST, MODE_PRIVATE);
            
            // build the string which will be written to this stream
            String strOutput = new String(); 
            for (Alarm alarm : lstAlarms)
            {
                if (!strOutput.isEmpty())
                {
                    strOutput += LIST_DELIMITER;
                }
                strOutput += alarm.nHour;
                strOutput += DATA_DELIMITER;
                strOutput += alarm.nMinute;
                strOutput += DATA_DELIMITER;
                strOutput += alarm.strName;
                strOutput += DATA_DELIMITER;
                
                // can save space here by storing this information into a single char (byte) using a bit-mask
                for (boolean fDay : alarm.fDayOfWeek)
                {
                    strOutput += fDay ? '1' : '0';
                }
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
        }
        catch (IOException e)
        {
            // something went wrong when writing to file?
            e.printStackTrace();
        }
    }
    
    // This function is used to load the list of alarms from internal storage
    // This should only be called from AlarmixActivity
    public void loadAlarmList(Context context)
    {
        try
        {
            FileInputStream is = context.openFileInput(FILE_NAME_ALARM_LIST);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            // read form the stream to build the media list
            ArrayList<Alarm> lstAlarms = new ArrayList<Alarm>();
            String line;
            while ((line = br.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    Alarm alarm = new Alarm();
                    int nStart = 0;
                    int nEnd   = line.indexOf(DATA_DELIMITER);
                    
                    // Populate hour field
                    alarm.nHour = Integer.parseInt(line.substring(nStart, nEnd));
                    
                    nStart = nEnd + 1;
                    nEnd   = line.indexOf(DATA_DELIMITER, nStart);
                    
                    // Populate minute field
                    alarm.nMinute = Integer.parseInt(line.substring(nStart, nEnd));
                    
                    nStart = nEnd + 1;
                    nEnd   = line.indexOf(DATA_DELIMITER, nStart);
                    
                    // Populate name field
                    alarm.strName = line.substring(nStart, nEnd);
                    
                    nStart = nEnd + 1;
                    nEnd   = line.indexOf(DATA_DELIMITER, nStart);
                    
                    // Populate schedule fields
                    for (int ix = 0; ix < alarm.fDayOfWeek.length; ix++)
                    {
                        alarm.fDayOfWeek[ix] = (line.charAt(nStart + ix) == '1') ? true : false;
                    }
                    
                    lstAlarms.add(alarm);
                }
            }
            
            model.lstAlarms = lstAlarms;
            
            // clean up
            br.close();
            isr.close();
            is.close();
        }
        catch (FileNotFoundException e)
        {
            // maybe the file hasn't been created yet
            // in that case, leave model.lstMediaPaths as an empty list
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // something went wrong when reading from file?
            e.printStackTrace();
        }
    }
}
