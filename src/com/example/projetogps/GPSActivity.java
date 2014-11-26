package com.example.projetogps;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends Activity implements LocationListener {
  private TextView latituteField;
  private TextView longitudeField;
  TextView altitudeField;
  private LocationManager locationManager;
  private String provider;
  private Context context = this;
  private GPS gps;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    setContentView(R.layout.activity_gps);
    latituteField = (TextView) findViewById(R.id.latitudeTextField);
    longitudeField = (TextView) findViewById(R.id.longitudeTextField);
    altitudeField = (TextView) findViewById(R.id.altitudeTextField);
    Criteria criteria = new Criteria();
    provider = locationManager.getBestProvider(criteria, false);
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    Location location = locationManager.getLastKnownLocation(provider);

    gps = new GPS(this);
    gps.setContext(this);
    GPS.setLocation(location);
    GPS.setFields(latituteField, longitudeField, altitudeField);
    GPS.showSettingsAlert(locationManager);

    // Initialize the location fields
    if (location != null) {
      System.out.println("Provider " + provider + " has been selected.");
      onLocationChanged(location);
    } else {
      latituteField.setText("Location not available");
      longitudeField.setText("Location not available");
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    locationManager.requestLocationUpdates(provider, 0, 0, this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
  }

  @Override
  public void onLocationChanged(Location location) {
    latituteField.setText(GPS.getLatitude(location));
    longitudeField.setText(GPS.getLongitude(location));
    altitudeField.setText(GPS.getAltitude(location));
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "Enabled new provider " + provider,
      Toast.LENGTH_SHORT).show();

  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "Disabled provider " + provider,
      Toast.LENGTH_SHORT).show();
  }
}