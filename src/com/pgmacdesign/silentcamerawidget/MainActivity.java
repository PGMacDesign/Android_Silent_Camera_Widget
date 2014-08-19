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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
	
	//Shared Preferences
	public static final String PREFS_NAME = "RSRToolboxData";	
	SharedPrefs sp = new SharedPrefs();
	SharedPreferences settings;
	SharedPreferences.Editor editor;
	String yahooWidgetTutorial = "https://www.yahoo.com/tech/how-to-add-android-widgets-to-your-phones-home-screen-85049692289.html";
	
	//Main - When the activity starts
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Initialize Variables
		Initialize();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		builder.setTitle("Thanks For Downloading!");
		builder.setMessage("This is a widget that will open up a voice toggle and record what you say to a clipboard. IE, if you say, "
				+ "'Hello! How are You?' it will copy that to the clipboard and you can paste it elsewhere. Need more information on"
				+ " how to use widgets?");
		builder.setInverseBackgroundForced(true);
		builder.setPositiveButton("Nope",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
		builder.setNegativeButton("Sure", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int id) {
			        dialog.cancel();
			        introToWidgets();		
			   }
			});
        AlertDialog alert = builder.create();
        alert.show();	
		
        
	}

	//Initialize Variables
	private void Initialize(){
		
		//Shared Preferences Stuff
		settings = getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
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
	
	public void introToWidgets(){
		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
		builder2.setCancelable(true);
		builder2.setTitle("More Data:");
		builder2.setMessage("Widgets are added to an empty space on your main screens of the device. Depending on the maker of your device, "
				+ "some require you to long-press an empty spot on one of your screens and choose to add a widget while others want you to"
				+ " go into all of your apps and click on the widget tab. Still need more help?");
		builder2.setInverseBackgroundForced(true);
		builder2.setPositiveButton("Nope",
                new DialogInterface.OnClickListener() {
            @Override
	            public void onClick(DialogInterface dialog,
	                    int which) {
	                dialog.dismiss();
	                finish();
            }
        });
		builder2.setNegativeButton("I'm Lost...", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int id) {
			        dialog.cancel();
                	//
        			try{
        				
        				//Opens a link directly to my play store download
        				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(yahooWidgetTutorial));
        				startActivity(browserIntent);
        				
        			} catch(Exception e){ 
        				  //e.toString();
        			}  
                	//  		
			   }
			});
        AlertDialog alert = builder2.create();
        alert.show();
	}

}
