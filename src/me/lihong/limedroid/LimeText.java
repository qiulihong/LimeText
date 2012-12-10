package me.lihong.limedroid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class LimeText extends FragmentActivity 
					  implements SaveFileDialogFragment.SaveFileDialogListener{

	private static final String TAG = "dialog";
	
	// some state variables
	private boolean autoComplete = true;

	private boolean creatingFile = false;
	private boolean savingFile = false;
	private boolean openingFile = false;
	private boolean openingError = false;
	private boolean openingRecent = false;
	private boolean sendingAttachment = false;
	private static CharSequence temp_filename = "";
	private boolean fromIntent = false;
	private boolean openingIntent = false;
	private Intent newIntent = null;
	
	private boolean fromSearch = false;
	private String queryString = "";
	
	private CharSequence errorFname = "File";
	private boolean errorSaving = false;

	private int fileformat;
	
	// some global variables
	protected static EditText text = null;
	protected static TextView title = null; 
	protected CharSequence filename = "";
	protected long lastModified = 0;
	protected boolean untitled = true;
	
	// file format ids
	private final static int FILEFORMAT_NL = 1;
	private final static int FILEFORMAT_CR = 2;
	private final static int FILEFORMAT_CRNL = 3;
	
	// dialog ids
	private final static int DIALOG_SAVE_FILE = 1;
	private final static int DIALOG_OPEN_FILE = 2;
	private final static int DIALOG_SHOULD_SAVE = 3;
	private final static int DIALOG_OVERWRITE = 4;
	private final static int DIALOG_SAVE_ERROR = 5;
	private final static int DIALOG_SAVE_ERROR_PERMISSIONS = 6;
	private final static int DIALOG_SAVE_ERROR_SDCARD = 7;
	private final static int DIALOG_READ_ERROR = 8;
	private final static int DIALOG_NOTFOUND_ERROR = 9;
	private final static int DIALOG_SHOULD_SAVE_INTENT = 13;
	private final static int DIALOG_MODIFIED = 14;
	
	private final static int DIALOG_SAVE_FILE_AUTOCOMPLETE = 10;
	private final static int DIALOG_OPEN_FILE_AUTOCOMPLETE = 11;

	private final static int DIALOG_RECENT_FILE_DIALOG = 12;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_lime_text_main);
        
        //update optioons
        updateOptions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_lime_text_main, menu);
        return true;
    }
    
//    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()) {
	    	case R.id.save:
	    		showSaveDialog();
	    		return true;
	    	case R.id.save_as:
	    		saveAs();
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
    	}
    }
    
    private void showSaveDialog(){
		DialogFragment df = new SaveFileDialogFragment();
		df.show(getSupportFragmentManager(), TAG);
    }
    /****************************************************************
	 * save(fname)
	 * 		What to do when saving note */
	public void save(CharSequence fname)
	{
		errorSaving = false;
		
		// actually save the file here
		try {
			File f = new File(fname.toString());
			
			if ( (f.exists() && !f.canWrite()) || (!f.exists() && !f.getParentFile().canWrite()))
			{
				creatingFile = false;
				openingFile = false;
				errorSaving = true;
				
				if (fname.toString().indexOf("/sdcard/") == 0) {
    				showDialog(DIALOG_SAVE_ERROR_SDCARD);
				}else{
					showDialog(DIALOG_SAVE_ERROR_PERMISSIONS);
				}

				text.requestFocus();
			
				f = null;
				///return;
			}
			f = null; // hopefully this gets garbage collected
			
			// Create file 
			FileWriter fstream = new FileWriter(fname.toString());
			BufferedWriter out = new BufferedWriter(fstream);
			
			if (fileformat == FILEFORMAT_CR)
			{
				out.write(text.getText().toString().replace("\n", "\r"));
			} else if (fileformat == FILEFORMAT_CRNL) {
				out.write(text.getText().toString().replace("\n", "\r\n"));
			} else {
				out.write(text.getText().toString());
			}
			
			out.close();
			
			// give a nice little message
			Toast.makeText(this, R.string.onSaveMessage, Toast.LENGTH_SHORT).show();
			
			// the filename is the new title
			title.setText(fname);
			filename = fname;
			untitled = false;
		
			lastModified = (new File(filename.toString())).lastModified();
			
			temp_filename = "";
			
			//addRecentFile(fname);
		} catch (Exception e) { //Catch exception if any
			creatingFile = false;
			openingFile = false;
			
			if (fname.toString().indexOf("/sdcard/") == 0) {
				System.out.println( e.getMessage());
			} else {
			}
			
			errorSaving = true;
		}
		
		text.requestFocus();
	} // end save()
    
    private boolean saveAs() {
    	return true;
    }
    
	/****************************************************************
	 * updateOptions()
	 * start options app
    **/
	protected void updateOptions()
	{
		boolean value;

		// load the preferences
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		autoComplete = sharedPref.getBoolean("autocomplete", false);
		
		/********************************
		 * Auto correct and auto case */
		boolean autocorrect = sharedPref.getBoolean("autocorrect", false);
		boolean autocase = sharedPref.getBoolean("autocase", false);
		
		/*
		//TODO: need to seperate each layout, now just use edit instead
		if (autocorrect && autocase)
		{
			setContentView(R.layout.edit_autotext_autocase);
		} else if (autocorrect) {
			setContentView(R.layout.edit_autotext);
		} else if (autocase) {
			setContentView(R.layout.edit_autocase);
		} else {
			setContentView(R.layout.edit);
		}
		*/
		setContentView(R.layout.layout_activity_lime_text_main);
		
		text = (EditText) findViewById(R.id.file_content);
		title = (TextView) findViewById(R.id.file_title);
	
		text.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence one, int a, int b, int c) {

				// put a little star in the title if the file is changed
				if (!isTextChanged()) // if it's first time be modified
				{
					CharSequence temp = title.getText();
					title.setText("* " + temp);
				}
			}

			// complete the interface
			public void afterTextChanged(Editable s) { }
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
		});
	
		/********************************
		 * links clickable */
		boolean linksclickable = sharedPref.getBoolean("linksclickable", false);
		
		if (linksclickable)
			text.setAutoLinkMask(Linkify.ALL);
		else
			text.setAutoLinkMask(0);
 
		/********************************
		 * show/hide filename */
		value = sharedPref.getBoolean("hidefilename", false);
		if (value)
			title.setVisibility(View.GONE);
		else
			title.setVisibility(View.VISIBLE);
		
		/********************************
		 * line wrap */
		value = sharedPref.getBoolean("linewrap", true);
		text.setHorizontallyScrolling(!value);

		// setup the scroll view correctly
		ScrollView scroll = (ScrollView) findViewById(R.id.scroll);	
		if (scroll != null)
		{
			scroll.setFillViewport(true);
			scroll.setHorizontalScrollBarEnabled(!value);
		}
					
		/********************************
		 * font face */
		String font = sharedPref.getString("font", "Monospace");

		if (font.equals("Serif"))
			text.setTypeface(Typeface.SERIF);
		else if (font.equals("Sans Serif"))
			text.setTypeface(Typeface.SANS_SERIF);
		else  
       		text.setTypeface(Typeface.MONOSPACE);

		/********************************
		 * font size */
		String fontsize = sharedPref.getString("fontsize", "Medium");
		
		if (fontsize.equals("Extra Small"))
			text.setTextSize(12.0f);
		else if (fontsize.equals("Small"))
			text.setTextSize(16.0f);
		else if (fontsize.equals("Medium"))
			text.setTextSize(20.0f);
		else if (fontsize.equals("Large"))
			text.setTextSize(24.0f);
		else if (fontsize.equals("Huge"))
			text.setTextSize(28.0f);
		else
			text.setTextSize(20.0f);
		
		/********************************
		 * Colors */
		/*
		 * all these should be set in xml
		int bgcolor = sharedPref.getInt("bgcolor", 0xFF000000);
		text.setBackgroundColor(bgcolor);
		
		int fontcolor = sharedPref.getInt("fontcolor", 0xFFCCCCCC);
		text.setTextColor(fontcolor);
		
		title.setTextColor(bgcolor);
		title.setBackgroundColor(fontcolor);
		
		text.setLinksClickable(true);
		*/
	} // updateOptions()
    
	public static boolean isTextChanged()	// checks if the text has been changed
	{
		CharSequence temp = title.getText();
		
		try {	// was getting error on the developer site, so added this to "catch" it
		
			if (temp.charAt(0) == '*')
			{
				return true;
			}
		} catch (Exception e) {
			return false;
		} 

		return false;
	} // end isTextChanged()
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		EditText v = (EditText)dialog.getDialog().findViewById(R.id.filename_edit);
		save(v.getText().toString());
	}
	
	@Override
	public void onDialogNegtiveClick(DialogFragment dialog) {
		
	}
}
