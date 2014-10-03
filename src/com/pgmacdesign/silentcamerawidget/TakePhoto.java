package com.pgmacdesign.silentcamerawidget;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;

public class TakePhoto extends Activity {

	  private int cameraId = 0;	
	  private Camera cameraObject;
	  private ShowCamera showCamera;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		L.m("Testing Line 23");
	    takeThePhotoPlease(); 
	}

	//This method calls other respective methods to take the photo via the startActivityForResult()
	private void takeThePhotoPlease() {
	    //Intent intent1 = new Intent();
	    //startActivityForResult(intent1, 1);
		L.m("Testing Line 31");
		cameraObject = isCameraAvailiable(); //Initialize camera
		L.m("Testing Line 33");
		setCameraResolution(); //Sets resolution to max on pictures
		L.m("Testing Line 35");
		silenceTheWorld(); //Turn media sounds on silent so that the camera will make no noise
		L.m("Testing Line 37");
		actuallyActivateCamera(); //Actually take the photo
		L.m("Testing Line 39");
		restoreSound(); //Turns volume back up
		L.m("Testing Line 41");
		
		
	}
	
	//Activates everything
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	}


	//Actually take the photo
	private void actuallyActivateCamera() {
	    
		//Preview window
	    showCamera = new ShowCamera(this, cameraObject);
	    FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    preview.addView(showCamera);
	    
   		//Take the photo!
   		cameraObject.takePicture(null, null, new PhotoHandler(getApplicationContext()));
   		

   		//Popup a dialog after 3 seconds to release the camera
   		Handler handler = new Handler(); 
   		handler.postDelayed(new Runnable() { 
 	        public void run() { 
	 	   		finish();
 	        } 
 	    }, (1000*5));
   		
   		
	}
  	
	
  	//Turn media sounds on silent so that the camera will make no noise
	private void silenceTheWorld() {
		
		//This silences the shutter sound
		//enableShutterSound(boolean enabled);
	}

	//Turns volume back up
	private void restoreSound() {
		
	}
	
	//Initializes the camera object
   	public static Camera isCameraAvailiable(){
   		Camera object = null;
      	try {
    	  object = Camera.open();
         	L.m("Camera Open");
      	} catch (Exception e) {
    	  L.m(e.toString());
      	} return object; 
   	}
	
	//Sets the resolution on the photo to be the highest possible resolution
   	private void setCameraResolution() {
   		
   		//Parameters object to get the parameters of the camera
   		Camera.Parameters params = cameraObject.getParameters();
   		
   		
   		
   		//Check what resolutions are supported by your camera
	   	List<Size> resolution_size_choices = params.getSupportedPictureSizes();
   		
	   	//Make a list type variable (of type size). The temp one is for the for loop
   		Size sizeTemp;
   		
   		//These variables will help to set the highest resolution possible
   		int highest_height = 0;
   		int highest_width = 0;
   		int height1 = 0;
		int width1 = 0;
   		
   		//Run a for loop that cycles through the resolution choices and lists them 
   		for (int i = 0; i < resolution_size_choices.size(); i++){
   			
   			//Read the resolution from the size list
   			sizeTemp = resolution_size_choices.get(i);
   			//Determine the height at the current list position
   			height1 = sizeTemp.height;
   			//Determine the width at the current list position
   			width1 = sizeTemp.width;
   			
   			//Set the height to the highest one thus far
   			if (height1 > highest_height){
   				highest_height = height1;
   			}
   	
   			//Set the width to the highest one thus far
   			if (width1 > highest_width){
   				highest_width = width1;
   			}   			
   		}
	   	
	   	//Set the parameters to match the highest Megapixel available
	   	params.setPictureSize(highest_width, highest_height);
	   	cameraObject.setParameters(params);   		
	   	
	   	//Log the Megapixels from the results of the loop
   		//Gets the resolution (Height x Width)
   		int aResolution = highest_height * highest_width;
   		//For the calculation of Megapixels. Not really needed, but fun to have anyway
   		int megaPixels = aResolution / 1024000;
   		//Log it
   		L.m("Resolution = " + aResolution);
   		L.m("Megapixels = " + megaPixels);
	   	
   		
   		
   	}

   	//onPause()
	protected void onPause() {
		if (cameraObject != null) {
			cameraObject.release();
			cameraObject = null;
			L.m("Camera has been released");
		}
		super.onPause();
	}
	
	//onDestroy()
	protected void onDestroy(){
		if (cameraObject != null) {
			cameraObject.release();
			cameraObject = null;
			L.m("Camera has been released");
		}
		super.onDestroy();
	}
	
}
