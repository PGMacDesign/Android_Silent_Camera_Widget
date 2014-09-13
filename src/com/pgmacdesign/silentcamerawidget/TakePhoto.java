package com.pgmacdesign.silentcamerawidget;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TakePhoto extends Activity {

	  private final static String DEBUG_TAG = "TakePhotoActivity";
	  private Camera camera;
	  private int cameraId = 0;	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		L.m("Testing Line 21");
	    takeThePhotoPlease();
	}

	//This method calls other respective methods to take the photo
	private void takeThePhotoPlease() {
		checkIfCameraExists(); //Check first
		L.m("Testing Line 28");
		silenceTheWorld(); //Turn media sounds on silent so that the camera will make no noise
		L.m("Testing Line 30");
		actuallyActivateCamera(); //Actually take the photo
		L.m("Testing Line 32");
		restoreSound(); //Turns volume back up
		L.m("Testing Line 34");
		
	}

	//Turn media sounds on silent so that the camera will make no noise
	private void restoreSound() {
		
	}

	//Actually take the photo
	private void actuallyActivateCamera() {
	    camera.takePicture(null, null,
		        new PhotoHandler(getApplicationContext())); //Calls the PhotoHandler class here
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
  				Log.d(DEBUG_TAG, "Camera found");
  				cameraId = i;
  				break;
  			}
  		}
  		return cameraId;
  	}
  	
	//Turns volume back up
	private void silenceTheWorld() {
		
	}

	//Checks to see if the camera exists on the phone
	private void checkIfCameraExists() {

		// do we have a camera?
		if (!getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
					.show();
		} else {
			cameraId = findFrontFacingCamera();
			if (cameraId < 0) {
				Toast.makeText(this, "No front facing camera found.",
						Toast.LENGTH_LONG).show();
			} else {
				camera = Camera.open(cameraId);
			}
		}
	}
	

	
	
}
