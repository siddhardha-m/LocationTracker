package com.sid.locationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//The activity to send SMS 
public class SendLocationSMSActivity extends ActionBarActivity {
	
	//To store the text of the SMS
	String location_text;
	
	//To reference the send button and phone no EditText
	Button sendBtn;
	EditText phoneNo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Linking SendLocationSMSActivity to activity_send_location_sms layout
		setContentView(R.layout.activity_send_location_sms);
		
		//Store the intent sent to SendLocationSMSActivity
		Intent location_intent = getIntent();
		
		//To get extras from the location intent 
		Bundle extras = location_intent.getExtras();
		
		//Get the latitude and longitude values from the intent
		String latitude_val = extras.getString("LATITUDE_MESSAGE");
		String longitude_val = extras.getString("LONGITUDE_MESSAGE");

		// Getting reference to the TextView display_location
		TextView tvlocation = (TextView)findViewById(R.id.display_location);
		
		//Forming the text of SMS
		location_text = "Sid's Location:\n" + "Latitude: " + latitude_val + "\n" + "Longitude: " + longitude_val;
		
		//Setting location to the TextView
		tvlocation.setText(location_text);
		
		//References to Send button and phone_no EditText
		sendBtn = (Button) findViewById(R.id.send_btn);
	    phoneNo = (EditText) findViewById(R.id.phone_no);
	
	    //Register OnClickListener event on send button
	    sendBtn.setOnClickListener(new View.OnClickListener() {
	         public void onClick(View view) {
	            //sendSMS function is called when the button is clicked
	        	 sendSMS();
	         }
	      });
	}
	
	//sendSMS function is called when the button is clicked
	protected void sendSMS() {
		
		//Storing the phone number in a variable
	    String phone_no = phoneNo.getText().toString();

	    
	    try {
	    	 //Reference to SMS manager
	         SmsManager smsManager = SmsManager.getDefault();
	         //Set the phone no and text of SMS and send
	         smsManager.sendTextMessage(phone_no, null, location_text, null, null);
	         //Toast the success message
	         Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_LONG).show();
	    } 
	    //If SMS sending failed
	    catch (Exception e) {
	         //Toast the error message 
	    	 Toast.makeText(getApplicationContext(),"SMS sending failed, please try again.", Toast.LENGTH_LONG).show();
	    }
	}
}
