package com.example.projetogps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.TextView;

public final class GPS {
  private static Context context;
  private static boolean isGPSEnabled = false;
  private static boolean isNetworkEnabled = false;
  private static boolean canGetLocation = false;
  public static Location location;
  private static LocationManager locationManager;
  private static String provider;
  private static int locationFormat = Location.FORMAT_DEGREES;
  private static TextView latituteField;
  private static TextView longitudeField;
  private static final long serialVersionUID = 1L;
  private static TextView altitudeField;

  public GPS(Context context) {
    super();
    GPS.context = context;
  }

  public static int getLocationFormat() {
    return locationFormat;
  }

  public void setContext(Context context) {
    GPS.context = context;
  }

  public static void setFields(TextView latitudeField, TextView longitudeField, TextView altitudeField) {
    GPS.latituteField = latitudeField;
    GPS.longitudeField = longitudeField;
    GPS.altitudeField = altitudeField;
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
        canGetLocation = true;
      }
      // if GPS Enabled get lat/long using GPS Services
      if (isGPSEnabled) {
        if (location == null) {
          locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0, (LocationListener) context);

          if (locationManager != null) {
            location = locationManager
              .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
              double latitude = location.getLatitude();
              double longitude = location.getLongitude();
            }
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return location;
  }

  public static String getLatitude(Location location) {
    return String.valueOf(Location.convert(location.getLatitude(), locationFormat));
  }

  public static String getLongitude(Location location) {
    return String.valueOf(Location.convert(location.getLongitude(), locationFormat));
  }

  public static String getAltitude(Location location) {
    return String.valueOf(location.getAltitude());
  }

  public static void setLocationFormat(int locationFormat) {
    GPS.locationFormat = locationFormat;
  }

  public static void showSettingsAlert(LocationManager locationManager) {
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

  public static void setLocation(Location location) {
    GPS.location = location;
  }
}
