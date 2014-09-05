package com.pgmacdesign.silentcamerawidget;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

//This class saves data to the memory card, need to have specified folder created 
public class PhotoHandler implements PictureCallback {

	private final static String DEBUG_TAG = "PhotoHandler Activity";
	private final Context context;
	
	public PhotoHandler(Context context) {
		this.context = context;
	}
	
	public void onPictureTaken(byte[] data, Camera camera) {
	
		File pictureFileDir = getDir();
	
	    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
	
	    Log.d(DEBUG_TAG, "Can't create directory to save image.");
	    Toast.makeText(context, "Can't create directory to save image.",
	    Toast.LENGTH_LONG).show();
	    return;
	}
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	String date = dateFormat.format(new Date());
	String photoFile = "Silent_Camera_" + date + ".jpg";
	
	String filename = pictureFileDir.getPath() + File.separator + photoFile;
	
	File pictureFile = new File(filename);
	
	try {
		FileOutputStream fos = new FileOutputStream(pictureFile);
		fos.write(data);
		fos.close();
		Toast.makeText(context, "New Image saved:" + photoFile,
				Toast.LENGTH_LONG).show();
		} catch (Exception error) {
			Log.d(DEBUG_TAG, "File" + filename + "not saved: "
					+ error.getMessage());
			Toast.makeText(context, "Image could not be saved.",
					Toast.LENGTH_LONG).show();
	    	}
	  	}
	
	private File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    return new File(sdDir, "CameraAPIDemo");
	  }
	} 