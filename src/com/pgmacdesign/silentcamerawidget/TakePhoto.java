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
	    takeThePhotoPlease();
	}

	private void takeThePhotoPlease() {
		checkIfCameraExists();
		
	}

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
	
	
	//NEED TO MOVE THIS INTO AN ACTION THAT HAPPENS WHEN THE WIDGET IS CLICKED
	public void onClick(View view) {
	    camera.takePicture(null, null,
	        new PhotoHandler(getApplicationContext()));
	  }

	  private int findFrontFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
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
	
	
	
}
