package com.example.projetogps;

import android.location.GpsSatellite;

public class Satellite {
  private float prn;
  private float azimuth;
  private float elevation;
  private float snr;
  private boolean glonass;
  private int satId;

  public Satellite(GpsSatellite sat, int satId) {
    this.prn = sat.getPrn();
    this.azimuth = sat.getAzimuth();
    this.elevation = sat.getElevation();
    this.satId = satId;
    this.snr = sat.getSnr();
    if (this.prn > 65 && this.prn < 88 && sat.usedInFix()) {
      this.glonass = true;
    }
  }

  public float getAzimuth() {
    return azimuth;
  }

  public float getPrn() {
    return prn;
  }

  public float getElevation() {
    return elevation;
  }

  public float getSnr() {
    return snr;
  }

  public boolean isGlonass() {
    return glonass;
  }

  @Override
  public String toString() {
    return String.format("Satellite nº " + getSatId() + ". Prn: " + getPrn() + ". Azimuth: " + getAzimuth() + " Elevação: " + getElevation() + ". Snr: " + getSnr() + ". Glonass: " + isGlonass());
  }

  public int getSatId() {
    return satId;
  }
}
