package com.pgmacdesign.silentcamerawidget;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class L {

	public static void m(String message){
		Log.d("Log", message);
	}
	
	public static void m(String tag, String message){
		Log.d(tag, message);
	}
	
	public static void makeToast(Context context, String message){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
