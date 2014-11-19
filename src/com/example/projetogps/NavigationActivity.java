package com.example.projetogps;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;


public class NavigationActivity extends Activity {
  private LatLng frameworkSystemLocation = new LatLng(-20.397833, -43.50906);
  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

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
}
