/*
        Copyright (C) <2014>  <Patrick Gray MacDowell>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.pgmacdesign.silentcamerawidget;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	
	//Shared Preferences
	public static final String PREFS_NAME = "SilentCameraWidget";	
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	
	//Main - When the activity starts
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Initialize Variables
		Initialize();
		
		
        
	}

	//Initialize Variables
	private void Initialize(){
		
		//Shared Preferences Stuff
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		
		//Developer.android.com recommends using a try catch for this
		try{
			Camera.open();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	//On Click Method
	public void onClick(View arg0) {
		/*
		switch (arg0.getId()){
		
		case R.id.button_ID_That_Was_Clicked:
			
			break;
			
		case R.id.button_ID_That_Was_Clicked:
			
			break;
			
		}
		*/
	}
	
	protected void onPause() {

		super.onPause();
		finish();
	}
	
	/** Check if this device has a camera 
	 * This is already taken care of via the manifest declaration, BUT, some people may 
	 * side-install it and this will help cause force-closing issues.  
	 * @param context
	 * @return
	 */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}

	//To be used for the menu bar
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater inflater = getMenuInflater();
	 
	  inflater.inflate(R.menu.action_bar_main, menu);
	  return super.onCreateOptionsMenu(menu);
	}
	
	//Temp purposes, to handle the button in the main activity
	public void openSecondActivity(View view) {
		  Intent intent = new Intent(this, SecondActivity.class);
		  startActivity(intent);
		}
	
	//If the options are clicked
	public boolean onOptionsItemSelected(MenuItem item) {
		  // Handle presses on the action bar items
		  switch (item.getItemId()) {
		    case R.id.action_search:
		      // Code you want run when activity is clicked
		      Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    case R.id.action_record:
		      Toast.makeText(this, "Record clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    case R.id.action_save:
		      Toast.makeText(this, "Save clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    case R.id.action_label:
		      Toast.makeText(this, "Label clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    case R.id.action_play:
		      Toast.makeText(this, "Play clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    case R.id.action_settings:
		      Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
		      return true;
		    default:
		      return super.onOptionsItemSelected(item);
		  }
		}
	
	//The following 2 classes (onTouchEvent and toggleActionBar are so that the menu will be hidden unless they click the screen (IE Gallery)
	public boolean onTouchEvent(MotionEvent event) {
	  if(event.getAction() == MotionEvent.ACTION_DOWN) {
	    toggleActionBar();
	  }
	  return true;
	}
	//Linked to above
	private void toggleActionBar() {
	  ActionBar actionBar = getActionBar();
	 
	  if(actionBar != null) {
	    if(actionBar.isShowing()) {
	      actionBar.hide();
	    }
	    else {
	      actionBar.show();
	    }
	  }
	}
}
