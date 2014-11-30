package com.example.projetogps;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;



public class NavigationActivity extends Activity implements LocationListener {
  private LatLng frameworkSystemLocation = new LatLng(-20.397833, -43.50906);
  private GoogleMap map;
  private LocationManager locationManager;
  private String provider;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    Criteria criteria = new Criteria();
    provider = locationManager.getBestProvider(criteria, false);
    Location location = locationManager.getLastKnownLocation(provider);

    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
      .getMap();
    if (map != null) {
      //Adiciona um ponto no mapa no local pré determinado
      map.setTrafficEnabled(true);  //waze
      //Marker frameworkSystem = map.addMarker(new MarkerOptions()
      //  .position(frameworkSystemLocation).title("Framework System"));
      // Move a câmera para Framework System com zoom 15.
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 15));
      map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
      //Marca sua posição no mapa
      map.getUiSettings().setMyLocationButtonEnabled(true);
      map.getUiSettings().setCompassEnabled(true);
      map.setMyLocationEnabled(true);

      if(location != null) {
        onLocationChanged(location);
      }

      map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {

          CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
          CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
          map.moveCamera(center);
          map.animateCamera(zoom);
        }
      });
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

  @Override
  public void onLocationChanged(Location location) {
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
    map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }
}
