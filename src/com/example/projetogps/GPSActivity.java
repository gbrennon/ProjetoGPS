package com.example.projetogps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class GPSActivity extends Activity {
  private TextView latituteField;
  private TextView longitudeField;
  private LocationManager locationManager;
  private String provider;
  private Context context = this;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gps);
    latituteField = (TextView) findViewById(R.id.latitudeTextField);
    longitudeField = (TextView) findViewById(R.id.longitudeTextField);

    GPS gps = new GPS(this);
    GPS.setContext(this);
    GPS.setLatitudeAndLongitudeFields(latituteField, longitudeField);
    GPS.setLocationFormat(Location.FORMAT_MINUTES);
    // Get the location manager
    //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    // Define the criteria how to select the locatioin provider -> use
    // default
    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    //Criteria criteria = new Criteria();
    //provider = locationManager.getBestProvider(criteria, false);
    //Location location = locationManager.getLastKnownLocation(provider);

    // Initialize the location fields
    if (gps.getLocation() != null) {
      System.out.println("Provider " + provider + " has been selected.");
      gps.onLocationChanged(gps.getLocation());
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
    }
  }
}