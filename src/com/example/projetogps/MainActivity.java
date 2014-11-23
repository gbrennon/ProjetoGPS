package com.example.projetogps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button gpsButton, navigationButton, configurationButton, creditsButton;
	Intent intent;
	final Context context = this;
	private LocationManager locManager;
	private LocationProvider locProvider;
  private GPS gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
    //gps = new GPS(this);
    //gps.setContext(context);
    //GPS.showSettingsAlert();
		//locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//locProvider = locManager.getProvider(LocationManager.GPS_PROVIDER);
		addListenerOnButton(navigationButton, R.id.navigationButton, NavigationActivity.class);
    addListenerOnButton(gpsButton, R.id.gpsButton, GPSActivity.class);
    //showSettingsAlert(locManager);
  }

  private void addListenerOnButton(Button button, int viewId, final Class<?> cls) {
    // Método para linkar o botão com sua Activity! Utilizem ele!
    button = (Button) findViewById(viewId);
    button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent(context, cls);
				startActivity(intent);
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
