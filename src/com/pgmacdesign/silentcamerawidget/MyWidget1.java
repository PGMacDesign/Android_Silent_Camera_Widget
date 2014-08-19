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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidget1 extends AppWidgetProvider{
    
	//When the widget is deleted, this will run, pop up window 
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Toast.makeText(context, "Widget Removed /Sad Panda", Toast.LENGTH_SHORT).show();
		//Or do whatever here
	}

	
	/*
	 * When the widget updates (Every 1/2 hour in this example) @Params:
	 *1) Context - Package name to refer to intents/ layouts
	 *2) Appwidgetmanager - Refer to for update
	 *3) AppwidgetIDs - Reference multiple IDs (IE in xml, 2 textViews or EditTexts) 
	 */
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		//Amount of IDs entered
		final int N = appWidgetIds.length;

		for (int i = 0; i < N; i++){

			int app_widget_ID = appWidgetIds[i];
			//
			RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			//Calls the method to update the widget. Affects this specific view
			appWidgetManager.updateAppWidget(app_widget_ID, v);
	
		}	
	}
	
	
}