package me.lihong.limedroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class OpenRecentFilesDialogFragment extends DialogFragment{
	protected ListView recentFilesList;
	
	private ArrayAdapter recentFilesAdapter;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		recentFilesList = (ListView)inflater.inflate(R.layout.layout_dialog_recent_files,null);
		//recentFilesList.setAdapter(recentFielsAdapter);
		
		builder.setView(recentFilesList)
			   .setTitle(R.string.open_recent_files)
			   .setIcon(android.R.drawable.ic_dialog_info)
		;
		
		return builder.create();
		
	}
}
