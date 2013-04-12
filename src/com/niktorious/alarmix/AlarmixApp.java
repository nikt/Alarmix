package com.niktorious.alarmix;

import android.app.Application;


public class AlarmixApp extends Application {
    private Model model;
    
    public AlarmixApp() {
        model = new Model();
    }
    
    public Model getModel() {
        return model;
    }
}
