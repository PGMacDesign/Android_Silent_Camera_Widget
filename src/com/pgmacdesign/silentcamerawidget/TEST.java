package com.pgmacdesign.silentcamerawidget;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class TEST extends Activity {


	  class SavePhotoTask extends AsyncTask<byte[], String, String> {
		    @Override
		    protected String doInBackground(byte[]... jpeg) {
		      File photo=
		          new File(Environment.getExternalStorageDirectory(),
		                   "photo.jpg");

		      if (photo.exists()) {
		        photo.delete();
		      }

		      try {
		        FileOutputStream fos=new FileOutputStream(photo.getPath());

		        fos.write(jpeg[0]);
		        fos.close();
		      }
		      catch (java.io.IOException e) {
		        Log.e("PictureDemo", "Exception in photoCallback", e);
		      }

		      return(null);
		    }
	  }
	  
		//Confirm front or back facing camera
	  	private int findFrontFacingCamera() {
	  		int cameraId = -1;
	  		
	  		//Search for the front facing camera
	  		int numberOfCameras = Camera.getNumberOfCameras();
	  		for (int i = 0; i < numberOfCameras; i++) {
	  			CameraInfo info = new CameraInfo();
	  			Camera.getCameraInfo(i, info);
	  			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	  				Log.d("stuff", "Camera found");
	  				cameraId = i;
	  				break;
	  			}
	  		}
	  		return cameraId;
	  	}
}
