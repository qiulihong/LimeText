package me.lihong.limedroid;

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
			   .setTitle(R.string.dialog_save_file)
			   .setPositiveButton(R.string.file_save, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
				   }
			   })
			   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
				   }
			   })
		;
		
		return builder.create();
	}
}