package me.lihong.limedroid;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecentFilesArrayAdapter extends ArrayAdapter<String>{

  private final LayoutInflater inflater = LayoutInflater.from(getContext());
  private final Spannable.Factory spannableFactory = new Spannable.Factory();
  
	public RecentFilesArrayAdapter(Context context, List<String> recentItems) {
		super(context, 0, recentItems);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Overridden method
	 * @return View
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		File f = new File(getItem(position));
		int imageid;
		
		String fileExt = getFileExtension(getItem(position));
		if (fileExt.equals("c"))
			imageid = R.drawable.fileicon_c;
		else if (fileExt.equals("cpp"))
			imageid = R.drawable.fileicon_cpp;
		else if (fileExt.equals("f"))
			imageid = R.drawable.fileicon_f;
		else if (fileExt.equals("h"))
			imageid = R.drawable.fileicon_h;
		else if (fileExt.equals("htm"))
			imageid = R.drawable.fileicon_htm;
		else if (fileExt.equals("html"))
			imageid = R.drawable.fileicon_html;
		else if (fileExt.equals("java"))
			imageid = R.drawable.fileicon_java;
		else if (fileExt.equals("pl"))
			imageid = R.drawable.fileicon_pl;
		else if (fileExt.equals("py"))
			imageid = R.drawable.fileicon_py;
		else if (fileExt.equals("tex"))
			imageid = R.drawable.fileicon_tex;
		else if (fileExt.equals("txt"))
			imageid = R.drawable.fileicon_txt;
		else
			imageid = R.drawable.fileicon_default;
		
		// This really speeds things up. Because only the number of views displayed at one time
		// are actually inflated
		View textEntryView;
		if (convertView != null)
		  textEntryView = convertView;
		else
		  textEntryView = inflater.inflate(R.layout.layout_filelist_item, null);
		
		TextView fileTitle = (TextView)textEntryView.findViewById(R.id.itemtext);
		fileTitle.setText(getItem(position));
		
		ImageView fileIcon = (ImageView)textEntryView.findViewById(R.id.itemimage);
		fileIcon.setImageResource(imageid);
		
		//TODO: why here reset text?
		 Spannable text;
		 
		 if(f.getParent().toString().equals('/')){
		   text = spannableFactory.newSpannable(f.getName() + "\n");
		 }else{
		   text = spannableFactory.newSpannable(f.getName() + "\n" + f.getParent());
		 }
		 text.setSpan(new AbsoluteSizeSpan(20), 0, f.getName().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		 fileTitle.setText(text);
		 
		return textEntryView;
	}

	/*
	 * getFileExtension 
	 * 
	 */
	public static String getFileExtension(String f)
	{
		String ext = "";
		int i = f.lastIndexOf('.');
		if (i > 0 &&  i < f.length() - 1)
			ext = f.substring(i + 1).toLowerCase();
		
		return ext;
	}
}
