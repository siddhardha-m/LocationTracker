package com.sid.locationtracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


//Creating custom BroadcastReceiver class
public class MyBroadcastReceiver extends BroadcastReceiver {

	//overriding the onReceive method of BroadcastReceiver
	@Override
	public void onReceive(Context context, Intent intent) {
		//To get extras from the location intent 
		Bundle extras = intent.getExtras();
		
		//Get the latitude and longitude values from the intent
		String latitude_val = extras.getString("LATITUDE_MESSAGE_BC");
		String longitude_val = extras.getString("LONGITUDE_MESSAGE_BC");
		
		//Forming the Toast string
		String location_text = "Sid's Location:\n" + "Latitude: " + latitude_val + "\n" + "Longitude: " + longitude_val;
		
		Toast.makeText(context, location_text, Toast.LENGTH_LONG).show();
	}

}
