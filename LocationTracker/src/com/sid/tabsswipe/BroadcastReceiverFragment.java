package com.sid.tabsswipe;

import com.sid.locationtracker.R;
import com.sid.locationtracker.SendLocationSMSActivity;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

//Fragments are used as Activity for corresponding tab
//LocationListener interface is inherited to access GPS location data
public class BroadcastReceiverFragment extends Fragment implements LocationListener, OnClickListener {
	
    //To store the View of the fragment
    View rootView;
    
    //LocationManager handle, Location handle and string object to store the provider
  	LocationManager locationManager ;
    String provider;
    Location location;
      
    //Strings to store the latitude and longitude values
    String latitude_location;
    String longitude_location;
    
    //To reference the Send via SMS and Broadcast Location buttons
    Button sendBtn;
    Button bcLocationBtn;
    
	//To store the latitude and longitude values Text views
    TextView tvLatitude;
    TextView tvLongitude;
    
    //To store the latitude and longitude values in variables
    String latitude_val;
    String longitude_val;
    
	//Strings to store the latitude and longitude values
    public final static String LATITUDE_MESSAGE = "";
    public final static String LONGITUDE_MESSAGE = "";

    //To set context of BroadcastReceiverFragment
    private Context ctx;
    
    //Constructor for BroadcastReceiverFragment
    public BroadcastReceiverFragment(Context context) {
    	ctx = context;
    }
    
    //To store the MainActivity
    Activity parentActivity;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		//BroadcastReceiverFragment view is linked to fragment_broadcast_receiver layout
        rootView = inflater.inflate(R.layout.fragment_broadcast_receiver, container, false);
         
        // Getting LocationManager object
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        
        // Creating criteria object
        Criteria criteria = new Criteria();
        
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
        
      //Check if a valid provider is present
        if(provider != null && !provider.equals("")) {
        	 
            // Get the last known location from the given provider
            location = locationManager.getLastKnownLocation(provider);
 
            //Enable automatic updates to location every 5 seconds
            locationManager.requestLocationUpdates(provider, 5000, 1, this);
 
            //Check if a valid location is returned
            if(location!=null)
                onLocationChanged(location);
            //Toast an error message if no valid location is found
            else
                Toast.makeText(ctx, "Problem encountered while retrieving the location.", Toast.LENGTH_SHORT).show();
 
        }
        
        //Toast an error message if no valid provider is found
        else {
            Toast.makeText(ctx, "No Provider Found.", Toast.LENGTH_SHORT).show();
        }

        //Reference to Send via SMS button
        sendBtn = (Button) rootView.findViewById(R.id.send_sms);
        
        //Reference to Broadcast Location button
        bcLocationBtn = (Button) rootView.findViewById(R.id.bc_location);
        
        //OnClickListener is enrolled
        sendBtn.setOnClickListener(this);
        bcLocationBtn.setOnClickListener(this);
        
        return rootView;
    }

	@Override
	public void onLocationChanged(Location location) {
		
		// Getting reference to the TextView latitude_value
        tvLatitude = (TextView)rootView.findViewById(R.id.latitude_value);
 
		// Getting reference to the TextView longitude_value
        tvLongitude = (TextView)rootView.findViewById(R.id.longitude_value);
 
        latitude_val = "" + location.getLatitude();
        longitude_val = "" + location.getLongitude();
        

		if (Float.parseFloat(latitude_val) > 0)
		{
			latitude_val = "N " + Float.parseFloat(latitude_val);
		}
		else if (Float.parseFloat(latitude_val) < 0)
		{
			latitude_val = "S " + Float.parseFloat(latitude_val) * -1;
		}
		
		if (Float.parseFloat(longitude_val) > 0)
		{
			longitude_val = "E " + Float.parseFloat(longitude_val);
		}
		else if (Float.parseFloat(longitude_val) < 0)
		{
			longitude_val = "W " + Float.parseFloat(longitude_val) * -1;
		}
		
        
        // Setting Current Latitude in the TextView latitude_value
        tvLatitude.setText("	" + latitude_val );

        // Setting Current Longitude in the TextView longitude_value
        tvLongitude.setText("	" + longitude_val );

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	//Custom method to broadcast location
	public void broadcastCustomLocationIntent()
    {
		//Create custom intent to store the location and broadcast
		Intent intent = new Intent("MyCustomLocationIntent");
		
		//For sending multiple extras as bundle
		Bundle extras = new Bundle();
		
		//Populating extras string with latitude and longitude values
		extras.putString("LATITUDE_MESSAGE_BC",latitude_val);
		extras.putString("LONGITUDE_MESSAGE_BC",longitude_val);
		
		//put the location in the intent
		intent.putExtras(extras);
		intent.setAction("com.sid.locationtracker.Broadcast");
		
		//Broadcast the intent
		ctx.sendBroadcast(intent);
    }
	
	//Setting parentActivity to MainActivity 
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }
	
	//Called on clicking the 'Send via SMS' button
	@Override
	public void onClick(View v) {
		//If Send via SMS button is clicked
		if (v.getId() == sendBtn.getId())
		{
		//Creating a new intent for Send BC location SMS Activity
		Intent location_intent = new Intent(getActivity(), SendLocationSMSActivity.class);
		
		//For sending multiple extras as bundle
		Bundle extras = new Bundle();
		
		//Populating extras string with latitude and longitude values
		extras.putString("LATITUDE_MESSAGE",latitude_val);
		extras.putString("LONGITUDE_MESSAGE",longitude_val);
		
		//Putting the extras in the intent
		location_intent.putExtras(extras);
		
		//Starting the activity SendBCLocationSMSActivity
		startActivity(location_intent);
		}
		
		//If Broadcast Location button is clicked
		else if (v.getId() == bcLocationBtn.getId())
		{
			//Broadcast the location
			broadcastCustomLocationIntent();
		}
	}
}