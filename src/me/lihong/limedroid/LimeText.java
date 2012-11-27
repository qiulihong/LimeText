package me.lihong.limedroid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LimeText extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lime_text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_lime_text, menu);
        return true;
    }
    
}
