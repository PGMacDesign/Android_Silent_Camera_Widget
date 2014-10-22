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
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

public class Splash extends Activity {

MediaPlayer ourIntroSong, doorClose; //Doorclose not used at this point, will setup for transitional sound at some point
	
	@Override
	protected void onCreate(Bundle inputVariableToSendToSuperClass) {

		super.onCreate(inputVariableToSendToSuperClass);
		setContentView(R.layout.splash);
		
		ourIntroSong = MediaPlayer.create(Splash.this, R.raw.cinematic_impact);
		ourIntroSong.start();
		
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					//In Milliseconds, set to 3 seconds at this point
					sleep(3000);
				} catch (InterruptedException e01) {
					String error_in_splash = e01.toString(); //For Debugging purposes
					e01.printStackTrace();
				} finally {

					
					//Testing this instead of a popup window
					//Intent openMain = new Intent(Splash.this, SecondActivity.class);
					//startActivity(openMain);
				}
			}
		};

		timer.start();
		
		callFinishAfter(3);
	}
	
	public void callFinishAfter(int seconds){
		L.m("Seconds total: " + 1000*seconds);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() { 
			 public void run() { 
				 
				 //
				 finish();
				 L.m("Finish called");
				 //
			 } 
		}, (1000*seconds));
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		//This kills the music so it isn't carried over between splash screens
		ourIntroSong.release();
		
		//Destroys the class when it goes on pause. Not ideal for most programs, but, for a splash screen, this works fine as we don't want it to show up again. 
		finish(); 
	}	
}