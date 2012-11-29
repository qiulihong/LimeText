package me.lihong.limedroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SaveFileDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_save_file)
			   .setPositiveButton(R.string.file_save, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
				   }
			   })
			   .setPositiveButton(R.string.file_save, new DialogInterface.OnClickListener(){
				   public void onClick(DialogInterface dialog, int id){
					  // Do something here 
				   }
			   })
		;
		
		return builder.create();
	}
}