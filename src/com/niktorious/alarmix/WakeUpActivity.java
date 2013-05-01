package com.niktorious.alarmix;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class WakeUpActivity extends Activity
{
    private MediaPlayer m_mediaPlayer;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wakeup);
        
        // Make sure the screen is properly unlocked
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                             WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                             WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON   |
                             WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON   );
        
        // Hook up the Snooze and Dismiss buttons
        Button butSnooze  = (Button) findViewById(R.id.butSnooze);
        Button butDismiss = (Button) findViewById(R.id.butDismiss);
        
        butSnooze.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickSnooze();
            }
        });
        
        butDismiss.setOnClickListener(new OnClickListener() {
            public void onClick(View v)
            {
                handleClickDismiss();
            }
        });
        
        // Set up the MediaPlayer
        m_mediaPlayer = new MediaPlayer();
        m_mediaPlayer.reset();
        m_mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        m_mediaPlayer.setLooping(true);
        
        // Set up the random number generator and seed it with the current time
        Random generator = new Random(Calendar.getInstance().getTimeInMillis());
        
        // Select and set the random song that will be used for the alarm
        AlarmixApp app = (AlarmixApp) getApplicationContext();
        try
        {
            int ix = -1;
            String strPath = new String();
            File file = null;
            if (app.getModel().lstMediaPaths.size() > 0)
            {
                ix = generator.nextInt(app.getModel().lstMediaPaths.size());
                strPath = app.getModel().lstMediaPaths.get(ix);
                file = new File(strPath);
                
                // Set up the display to show the current song
                TextView tvMarqueeTitle = (TextView) findViewById(R.id.tvMarqueeTitle);
                tvMarqueeTitle.setText(new String(strPath));
                tvMarqueeTitle.setSelected(true);
            }
            
            if (file != null && file.exists())
            {
                m_mediaPlayer.setDataSource(strPath);
            }
            else
            {
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                if(alert == null){
                    // alert is null, using backup
                    alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    if(alert == null){  // I can't see this ever being null (as always have a default notification) but just incase
                        // alert backup is null, using 2nd backup
                        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);               
                    }
                }
                
                m_mediaPlayer.setDataSource(this, alert);
            }
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {
            // not much we can do here
            e.printStackTrace();
        }
        catch (IllegalStateException e)
        {
            // not much we can do here
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // not much we can do here
            e.printStackTrace();
        }
        
        try
        {
            m_mediaPlayer.prepare();
        }
        catch (IllegalStateException e)
        {
            // not much we can do here
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        m_mediaPlayer.start();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        clean();
        
        // Go to the home screen when this activity ends
        Intent homeIntent= new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }
    
    // Helpers
    private void handleClickSnooze()
    {
        // Set a new alarm 10 min from now
        
        clean();
        
        finish();
    }
    
    private void handleClickDismiss()
    {
        // do we need to set a new alarm? is this a one-shot alarm?
        
        clean();
        
        finish();
    }
    
    // Clean up: make sure everything that we acquired is properly released
    private void clean()
    {
        if (m_mediaPlayer != null) m_mediaPlayer.release();
        WakeLocker.release();
    }
}
