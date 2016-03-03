package com.kentsong.gcm.tool.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogHelper {


	public static ProgressDialog.Builder createProgressDialog(Context context, String message){
		ProgressDialog.Builder dialog = new ProgressDialog.Builder(context)
		.setTitle("")
		.setMessage(message);

		return dialog;
	}
}
