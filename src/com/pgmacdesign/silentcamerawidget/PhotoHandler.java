package com.pgmacdesign.silentcamerawidget;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

//This class saves data to the memory card, need to have specified folder created 
public class PhotoHandler implements PictureCallback {

	private final static String DEBUG_TAG = "PhotoHandler Activity";
	private final Context context;
	Bitmap bmp, bmp2;
	
	public PhotoHandler(Context context) {
		this.context = context;
	}
	
	public void onPictureTaken(byte[] data, Camera camera) {
	
		//Create a file via the pathway listed in the getDir() method
		File pictureFileDir = getDir();
	
		//If there is a problem with the image...
	    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
	
		    Log.d(DEBUG_TAG, "Can't create directory to save image.");
		    Toast.makeText(context, "Can't create directory to save image.",
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
			Toast.makeText(context, "New Image saved: " + photoFile, Toast.LENGTH_LONG).show();
			
			L.m("New Image Saved: " + photoFile);
			
			} catch (Exception error) {
				
				Log.d(DEBUG_TAG, "File" + filename + "not saved: " + error.getMessage());
				Toast.makeText(context, "Image could not be saved.", Toast.LENGTH_LONG).show();
		    }
	}
	
	private File getDir() {
		//Locates the directory
		//File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File toDCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		//Returns the file in the directory 
	    return new File(toDCIM, "Silent Camera");
	  }
	
	//For rotating 
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
	
	//Testing
	public void convertAndSave(){
		/*
		//
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/req_images");
		myDir.mkdirs();

		String fname = "Image-" + n + ".jpg"; //Rename here for 
		file = new File(myDir, fname);
		Log.i(TAG, "" + file);
		if (file.exists())
		file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		*/
	}
} 