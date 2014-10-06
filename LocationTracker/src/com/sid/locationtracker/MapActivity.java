package com.sid.locationtracker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends Activity implements LocationListener {
	
	//LocationManager handle and string object to store the provider
	LocationManager locationManager;
	String provider;
	
	//To reference the Google Map Fragment in the layout file
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		//Hiding the ActionBar to show the map on Full Screen
		getActionBar().hide();
		
		//Getting reference to the Google Map Fragment in the layout file
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		//Setting Hybrid Map Type
		//map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		
		//Enabling the compass on the map
		map.getUiSettings().setCompassEnabled(true);
		
		//Show MyLocation button
		map.getUiSettings().setMyLocationButtonEnabled(true);
		
		// Getting LocationManager object
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		// Creating criteria object
        Criteria criteria = new Criteria();
        
        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);
		
        //Check if a valid provider is present
        if(provider != null && !provider.equals("")) {
        	
        	// Get the last known location from the given provider
            Location location = locationManager.getLastKnownLocation(provider);
            
            //Check if a valid location is returned
            if(location!=null)
            	onLocationChanged(location);
            //Toast an error message if no valid location is found
            else
            	Toast.makeText(this, "Problem encountered while retrieving the location.", Toast.LENGTH_SHORT).show();
        }
        
        //Toast an error message if no valid provider is found
        else {
            Toast.makeText(this, "No Provider Found.", Toast.LENGTH_SHORT).show();
        }
	}
	
	//Enable automatic updates to location when Activity resumes
	@Override
	protected void onResume() {
	    super.onResume();
	    //Enable automatic updates to location every 5 seconds
	    locationManager.requestLocationUpdates(provider, 5000, 1, this);
	}
	
	//Remove automatic updates to location when Activity paused
	@Override
	protected void onPause() {
	    super.onPause();
	    //Remove automatic updates to location
	    locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		// Store the new location in LatLng object
		LatLng map_location = new LatLng(location.getLatitude(), location.getLongitude());
		
		// Place a marker on map at the new location 
		Marker marker = map.addMarker(new MarkerOptions().position(map_location));
		
		//Changing the marker color
		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		
		// Move the Camera to the new location with a zoom of 15
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(map_location, 15));
		
		// Zoom in, animating the camera
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
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
}
