package com.pgmacdesign.silentcamerawidget;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class TakePhoto extends Activity {
	
	private SurfaceView preview=null;
	private SurfaceHolder previewHolder=null;
	private Camera camera=null;
	private boolean inPreview=false;
	private boolean cameraConfigured=false;
	
	private final static String DEBUG_TAG = "PhotoHandler Activity";

	Bitmap bmp, bmp2;
	


	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
	    
		setContentView(R.layout.main2);

		preview = (SurfaceView)findViewById(R.id.preview);
		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		camera = Camera.open();


	    
		//Hides the action bar
		//ActionBar actionBar = getActionBar();
		//actionBar.hide();


		//Popup a dialog after 6 seconds with option to start directions
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			 public void run() { 
				 
				 //
				 finish();
				 L.m("Finish called");
				 //
			 } 
		}, (1000*6));
		

	}

	private void callWithoutClick(){
		try {
			L.m("Test 0");
			
			camera.setPreviewDisplay(previewHolder);
			Camera.Parameters parameters=camera.getParameters();
			setCameraResolution();
			camera.setParameters(parameters);
			cameraConfigured=true;
			camera.startPreview();
			inPreview = true;
			preview.setVisibility(View.GONE);
			
			/*
			//Focus via the auto-focus and then take the photo
			if (getAutoFocusStatus()){
			    camera.autoFocus(new AutoFocusCallback() {
			        @Override
			        public void onAutoFocus(boolean success, Camera camera) {
			           if(success){
			        	   
			   			camera.takePicture(null, null, photoCallback);
						L.m("Test 1");
						inPreview=false;
			           }
			        }
			    }); 
			}else{
				camera.takePicture(null, null, photoCallback);
				L.m("Test 1");
				inPreview=false;
			}
			*/
			

			/*

			 */

			camera.takePicture(null, null, photoCallback);
			L.m("Test 1");
			inPreview=false;
			
		} catch (Exception e) {
			L.m("Exception Error", e.toString());
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.options, menu);

		return(super.onCreateOptionsMenu(menu));
	}

	//This method is here in case I want to add a button in place for activation
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.camera) {
			callWithoutClick();
		}
			
		return(super.onOptionsItemSelected(item));
	}

	//Sets up the preview
	private void initPreview() {
		if (camera != null && previewHolder.getSurface() != null) {
			try {
				camera.setPreviewDisplay(previewHolder);
			}
			catch (Throwable t) {
				Log.e("PreviewDemo-surfaceCallback", "Exception in setPreviewDisplay()", t);
				Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			if (!cameraConfigured) {
				Camera.Parameters parameters=camera.getParameters();
				setCameraResolution();
				
				camera.setParameters(parameters);
				cameraConfigured=true;
			}
		}
	}
	
	//Starts the preview
	private void startPreview() {
		if (cameraConfigured && camera != null) {
			camera.startPreview();
  			inPreview=true;
  		}
  	}

	//Surface holder to handle the surface management
	SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			callWithoutClick(); //Calls the camera to take action
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			initPreview();
			startPreview();
			preview.setVisibility(View.GONE);   //This hides the view from the user
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			
		}
	};

	//PhotoCallback to handle the picture callback
	Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			File pictureFileDir = getDir();

			//If there is a problem with the image...
			if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
	
				Log.d(DEBUG_TAG, "Can't create directory to save image.");
				Toast.makeText(getApplicationContext(), "Can't create directory to save image.",
						Toast.LENGTH_LONG).show();
				return;
			}
	    
			//Get the date and make a string out of it
			String date = new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(new Date());
			
			//Make a string with the file name and include the date
			String photoFile = "Silent Camera " + date + ".jpg";
			
			//Add the file name to the directory path 
			String filename = pictureFileDir.getPath() + File.separator + photoFile;
			
			//Input the filename into the actual file
			File pictureFile = new File(filename);
			
			//Convert the file into a bitmap for orientation 
			bmp  = BitmapFactory.decodeByteArray(data, 0, data.length);
				
			//Need to rotate the image. For whatever reason... 
			bmp = rotateImage(bmp, 90);
			
			//Write the data to external source
			try {
				//Create a new file output stream
				FileOutputStream fos = new FileOutputStream(pictureFile);
				
				/*
				 * Compress the bitmap into a jpeg and then write it via the fos object
				 * @Params: 
				 * 1) Format to compress to (IE JPEG)
				 * 2) Quality. 0 being VERY low quality and 100 being highest quality
				 * 3) The File output stream being used to write the data
				 */
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				//Flush the stream
				fos.flush();
				//Close the stream
				fos.close();		
	
				//Confirm it worked
				Toast.makeText(getApplicationContext(), "New Image saved: " + photoFile, Toast.LENGTH_LONG).show();
				
				L.m("New Image Saved: " + photoFile);
				
			} catch (Exception error) {
				
				Log.d(DEBUG_TAG, "File" + filename + "not saved: " + error.getMessage());
				Toast.makeText(getApplicationContext(), "Image could not be saved.", Toast.LENGTH_LONG).show();
		    }
		}
	};

	private File getDir() {
		//Locates the directory
		//File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File toDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		//Returns the file in the directory 
	    return new File(toDCIM, "Silent Camera");
	}
	
	//For rotating photo to the correct side
	public static Bitmap rotateImage(Bitmap b, int degrees) {

		if (degrees != 0 && b != null) {
			Matrix m = new Matrix();
		    m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
		    Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
		    if (b != b2) {
		    	b.recycle();
		        b = b2;
		    }
		}
		return b;
	}
	
	//Sets the resolution on the photo to be the highest possible resolution
   	private void setCameraResolution() {
   		
   		//Parameters object to get the parameters of the camera
   		Camera.Parameters params = camera.getParameters();
   		
   		
   		
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
	   	
	   	//Set auto focus
	   	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
	   	
	   	//Set the parameters in place
	   	camera.setParameters(params);   		
	   	
	   	//Log the Megapixels from the results of the loop
   		//Gets the resolution (Height x Width)
   		int aResolution = highest_height * highest_width;
   		//For the calculation of Megapixels. Not really needed, but fun to have anyway
   		int megaPixels = aResolution / 1024000;
   		//Log it
   		L.m("Resolution = " + aResolution);
   		L.m("Megapixels = " + megaPixels);	
   	}
   	

	@Override
	public void onResume() {
		super.onResume();

		if (camera == null) {
			//camera = Camera.open();
			L.m("Camera open in onResume");
		}
//		startPreview();
	}

	@Override
	public void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}

		camera.release();
		camera=null;
		inPreview=false;

		super.onPause();
	}

	
}