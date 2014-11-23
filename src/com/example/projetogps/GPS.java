package com.example.projetogps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.TextView;

public final class GPS extends Service implements LocationListener {
  private static Context context;
  private static boolean isGPSEnabled = false;
  private static boolean isNetworkEnabled = false;
  private static boolean canGetLocation = false;
  public static Location location;
  private static LocationManager locationManager;
  private static double latitude;
  private static double longitude;
  private static int locationFormat = Location.FORMAT_DEGREES;
  private static TextView latituteField;
  private static TextView longitudeField;
  private static final long serialVersionUID = 1L;

  public GPS(Context context) {
    super();
    GPS.context = context;
    getLocation();
  }

  public static int getLocationFormat() {
    return GPS.locationFormat;
  }
  public static void setContext(Context context) {
    GPS.context = context;
  }
  public static void setLatitudeAndLongitudeFields(TextView latitudeField, TextView longitudeField) {
    GPS.latituteField = latitudeField;
    GPS.longitudeField = longitudeField;
  }

  public Location getLocation() {
    try {
      locationManager = (LocationManager) context
        .getSystemService(Context.LOCATION_SERVICE);

      // getting GPS statusn
      isGPSEnabled = locationManager
        .isProviderEnabled(LocationManager.GPS_PROVIDER);

      // getting network status
      isNetworkEnabled = locationManager
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

      if (!isGPSEnabled && !isNetworkEnabled) {
        // no network provider is enabled
      } else {
        GPS.canGetLocation = true;
      }
      // if GPS Enabled get lat/long using GPS Services
      if (isGPSEnabled) {
        if (location == null) {
          locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0, this);

          if (locationManager != null) {
            location = locationManager
              .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
              latitude = location.getLatitude();
              longitude = location.getLongitude();
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return location;
  }

  public static String getLatitude() {
    return Location.convert(location.getLatitude(), locationFormat);
  }

  public static String getLongitude() {
    return Location.convert(location.getLongitude(), locationFormat);
  }

  public static void setLocationFormat(int locationFormat) {
    GPS.locationFormat = locationFormat;
  }

  public static void showSettingsAlert() {
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
      alertDialog.setTitle("Opções do GP");
      alertDialog.setMessage("GPS não está habilitado! Deseja habilitá-lo?");
      alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {

        public void onClick(DialogInterface dialog, int which) {
          Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
          context.startActivity(intent);
        }

      });

      alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
        }
      });

      alertDialog.show();
    }
  }

  @Override
  public void onLocationChanged(Location location) {
    int lat = (int) (location.getLatitude());
    int lng = (int) (location.getLongitude());
    latituteField.setText(String.valueOf(lat));
    longitudeField.setText(String.valueOf(lng));
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

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
