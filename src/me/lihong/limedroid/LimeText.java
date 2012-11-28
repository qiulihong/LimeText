package me.lihong.limedroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    
//    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()) {
	    	case R.id.save:
	    		save();
	    		return true;
	    	case R.id.save_as:
	    		saveAs();
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
    	}
    }
    
    private boolean save() {
    	return true;
    }
    
    private boolean saveAs() {
    	return true;
    }
    
}
