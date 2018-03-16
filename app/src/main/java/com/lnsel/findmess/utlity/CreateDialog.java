package com.lnsel.findmess.utlity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class CreateDialog {

	
	public static void showDialog(Context context, String alertText)
	{
		new AlertDialog.Builder(context)
	    .setTitle("Alert!")
	    .setMessage(alertText)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            // continue with delete
	        }
	     })
	    /*.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(android.R.drawable.ic_dialog_alert)*/
	     .show();
	}
}
