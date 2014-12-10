package com.example.projetogps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class GPSActivity extends Activity implements LocationListener, GpsStatus.Listener {
  private TextView latituteField;
  private TextView longitudeField;
  TextView altitudeField;
  private LocationManager locationManager;
  private String provider;
  private Context context = this;
  private GPS gps;
  private List<String> listGroup;
  private HashMap<String, List<String>> listData;
  private GpsStatus gpsStatus;
  private Iterable<GpsSatellite> sats;
  private Button button;

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

    button = (Button) findViewById(R.id.button);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        buildList();
      }
    });

    buildList();
    ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    expandableListView.setAdapter(new ExpandableAdapter(GPSActivity.this, listGroup, listData));

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
    locationManager.addGpsStatusListener(this);
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
    locationManager.removeUpdates(this);
    locationManager.removeGpsStatusListener(this);
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

  public void buildList() {
    listGroup = new ArrayList<String>();
    listData = new HashMap<String, List<String>>();

    gpsStatus = locationManager.getGpsStatus(null);
    sats = gpsStatus.getSatellites();

    List<String> auxList = new ArrayList<String>();

    // Criando os Satelites - Um FOR/WHILE com todos os satelites conectados
    // O loop entra aqui
    //for()
    int i = 0;
    for (GpsSatellite sat : sats) {
      listGroup.add(String.valueOf(sat.getPrn()));
      auxList.add(String.valueOf(sat.getAzimuth()));
      auxList.add(String.valueOf(sat.getElevation()));
      auxList.add(String.valueOf(sat.getSnr()));

      listData.put(listGroup.get(i), auxList);

      Toast.makeText(this, "I =  " + i,
        Toast.LENGTH_SHORT).show();

      i++;
    }

    // Criando os dados do Satelite - Um FOR com os 4 dados de cada saletile
    // O loop entra aqui

    //auxList.add("Codigo PRN: "); //Concatena com o codigo
    //auxList.add("Qualidade do Sinal: "); //Concatena com o SRN
    //auxList.add("Posição Azimute: "); //Concatena com Azimute
    //auxList.add("Posição Elevação: "); //Concatena com elevação
    //listData.put(listGroup.get(N), auxList);
  }

  @Override
  public void onGpsStatusChanged(int event) {
  }
}