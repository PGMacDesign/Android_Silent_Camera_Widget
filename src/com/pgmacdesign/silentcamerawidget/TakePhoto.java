package com.pgmacdesign.silentcamerawidget;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.TextureView.SurfaceTextureListener;
import android.widget.FrameLayout;

public class TakePhoto extends Activity implements SurfaceTextureListener {

	private int cameraId = 0;	
	private static final int IMAGE_CAPTURE = 102;
	private Camera cameraObject, myCamera;
	private ShowCamera showCamera;
    	
	private TextureView mTextureView;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		///setContentView(R.layout.second_activity);
		
		//Creating the texture view and setting it equal to the content view to handle the overlay
		mTextureView = new TextureView(this);
        mTextureView.setSurfaceTextureListener(this);
        mTextureView.setVisibility(View.GONE); 
        setContentView(mTextureView);
        L.m("Line 41");
        
        cameraObject = isCameraAvailiable();
        
        L.m("Line 45");
		
        takeThePhotoPlease();
		
        
        
        
        //L.m("Testing Line 27");
		//sendRecognizeIntent();
		
		//cameraObject = isCameraAvailiable(); //Initialize camera
		//L.m("Testing Line 31");
		//setCameraResolution(); //Sets resolution to max on pictures
		//L.m("Testing Line 36");
		
		//takePictureNoPreview(this);
		//L.m("Testing line 39");
		

	}
	
	//Testing on 2014-10-11
	private void sendRecognizeIntent() {

		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		L.m("Testing Line 45");
		startActivityForResult(intent, IMAGE_CAPTURE);
		L.m("Testing Line 47");
		
	}

	//This method calls other respective methods to take the photo via the startActivityForResult()
	private void takeThePhotoPlease() {

		//Shut off the shutter sound
		silenceTheWorld();
		L.m("Line 81");
		//Set the camera parameters (IE resolution)
		setCameraResolution();
		L.m("Line 84");
		//Take the picture
		actuallyActivateCamera();
		L.m("Line 87");
		//Restore the sound
		restoreSound();
		L.m("Line 90");
	}
	
	public void takePictureNoPreview(Context context){
		  // open back facing camera by default
		myCamera = Camera.open();
		
		L.m("Line 65 working");
		setCameraResolution();
		L.m("Line 67 working");

		if(myCamera!=null){
			try{
				//set camera parameters if you want to
				//...

				//here, the unused surface view and holder
				SurfaceView dummy = new SurfaceView(context);
				myCamera.setPreviewDisplay(dummy.getHolder());    
				//myCamera.startPreview(); 
		      
				//myCamera.takePicture(null, null, pictureCallbackStuff());
				myCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
		      
		      
			} catch (IOException e) {
				//Error yo
				e.printStackTrace();
			}finally{
		      myCamera.release();
		    }      

		}else{
		    //booo, failed!
		}
	}

	
    //This is to handle the photos passed in
	PictureCallback pictureCallbackStuff = new PictureCallback() {   
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("testPatricktest.jpeg");
				fos.write(data);
				fos.close();
			}  catch (IOException e) {
				//do something about it
			}
		}	
	};
	
	
	//Activates everything
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		L.m("Testing Line 60");
		if (requestCode == IMAGE_CAPTURE)
			{
				if (resultCode == RESULT_OK) {
					L.m("result ok. Ok? Ok.");
		            
		            
		          
        		} else {
		        	L.m("Result is not ok!");
		        }
		    } else {
		    	L.m("Request Code is NOT OK!");
		    }
	}


	//Actually take the photo
	private void actuallyActivateCamera() {
	    
		//Preview window
	    //showCamera = new ShowCamera(this, cameraObject);
	    //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	    //preview.addView(showCamera);
	    
   		//Take the photo!
		L.m("Camera - Line 171");
   		cameraObject.takePicture(null, null, new PhotoHandler(getApplicationContext()));
   		L.m("Camera - Line 173");
   		

   		//Popup a dialog after 3 seconds to release the camera
   		Handler handler = new Handler(); 
   		handler.postDelayed(new Runnable() { 
 	        public void run() { 

	 	   		//finish(); //Removed for now
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

	//onResume
	public void onResume() {
		super.onResume();

		if (cameraObject == null) {
			cameraObject=Camera.open();
			L.m("Camera open in onResume");
		}

		//startPreview(); Need to add this in 
	}
	
   	//onPause()
	protected void onPause() {
		if (cameraObject != null) {
			cameraObject.release();
			cameraObject = null;
			L.m("Camera has been released (onPause)");
			//cameraObject.stopPreview();
			//L.m("Preview has been released (onPause)");
		}
		super.onPause();
	}
	
	//onDestroy()
	protected void onDestroy(){
		if (cameraObject != null) {
			cameraObject.release();
			cameraObject = null;
			L.m("Camera has been released (onDestroy)");
			//cameraObject.stopPreview();
			//L.m("Preview has been released (onDestroy)");
		}
		super.onDestroy();
	}
	
	public void notWorkingCode(){
		
	    //Intent intent1 = new Intent();
	    //startActivityForResult(intent1, 1);
		L.m("Testing Line 45");
		
		L.m("Testing Line 47");
		silenceTheWorld(); //Turn media sounds on silent so that the camera will make no noise
		L.m("Testing Line 49");
		actuallyActivateCamera(); //Actually take the photo
		L.m("Testing Line 51");
		restoreSound(); //Turns volume back up
		L.m("Testing Line 53");
		
		showCamera = new ShowCamera(this, cameraObject);
		L.m("Testing Line 33");
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		L.m("Testing Line 35");
		preview.addView(showCamera); ///////////////ERROR IS HERE
		L.m("Testing Line 37");		
		//takeThePhotoPlease(); 
		
	}

	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			// no-op -- wait until surfaceChanged()
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			initPreview(width, height);
			startPreview();
			preview.setVisibility(View.GONE);   //THIS IS WORKING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// no-op
		}
	};
	
	//Setup the Camera
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		
		//cameraObject = Camera.open();

        Camera.Size previewSize = cameraObject.getParameters().getPreviewSize();
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(
                previewSize.width, previewSize.height, Gravity.CENTER));

        try {
        	cameraObject.setPreviewTexture(surface);
        } catch (IOException t) {
        }

        cameraObject.startPreview();
	}

	//Android camera will handle this method
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
		//IGNORE THIS METHOD, must be included as it is implemented, but can be ignored as the camera handles it
	}

	//Destroy camera objects
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		//cameraObject.stopPreview();
		//cameraObject.release();
        return true;
	}

	//Update texture here when there is something to change
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		//Update the view if need be here
	}
}
