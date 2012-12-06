package me.lihong.limedroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class SaveFileDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.dialog_save_file, null))
			   .setIcon(android.R.drawable.ic_dialog_info)
			   .setTitle(R.string.dialog_save_file)
			   .setPositiveButton(R.string.file_save, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
					   buttonListener.onDialogPositiveClick(SaveFileDialogFragment.this);
				   }
			   })
			   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
					   buttonListener.onDialogNegtiveClick(SaveFileDialogFragment.this);
				   }
			   })
		;
		
		return builder.create();
	}
	
	// public interface, all activities need to show the dialog have to implement this interface
	public interface SaveFileDialogListener {
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegtiveClick(DialogFragment dialog);
	}
	
	SaveFileDialogListener buttonListener;
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		
		try{
			buttonListener = (SaveFileDialogListener) activity;
		}catch(ClassCastException e){
			throw new ClassCastException( activity.toString() +
					" Must implement SaveFileDialog.SaveFileDialogListener" );
		}
	}
	
}