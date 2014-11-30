package com.example.projetogps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConfigurationActivity extends Activity {
  Context context = this;
  Intent intent;
  RadioGroup radioConfigurationGroup;
  RadioButton radioLocationButton;
  Button configButton;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_configuration);
    radioConfigurationGroup = (RadioGroup) findViewById(R.id.radioConfiguration);

    configButton = (Button) findViewById(R.id.setConfigButton);

    addListenerOnButton(configButton);

    setCurrentConfigRadio();
  }

  private void setCurrentConfigRadio() {
    if (GPS.getLocationFormat() == Location.FORMAT_DEGREES)
      radioConfigurationGroup.check(R.id.radioDecimals);
    else if (GPS.getLocationFormat() == Location.FORMAT_MINUTES)
      radioConfigurationGroup.check(R.id.radioMinutes);
    else
      radioConfigurationGroup.check(R.id.radioSeconds);

  }
  private void addListenerOnButton(Button button) {
    // Método para linkar o botão com sua Activity! Utilizem ele!
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        int selectedId = radioConfigurationGroup.getCheckedRadioButtonId();

        radioLocationButton = (RadioButton) findViewById(selectedId);
        setLocationFormat(radioLocationButton);
        finish();
      }
    });
  }

  private void setLocationFormat(RadioButton radioLocationButton) {
    if(radioLocationButton == findViewById(R.id.radioMinutes)) {
      GPS.setLocationFormat(Location.FORMAT_MINUTES);
      Toast.makeText(context,
        "Posicionamento agora em Graus, Minutos decimais", Toast.LENGTH_SHORT).show();
    }
    else if(radioLocationButton == findViewById(R.id.radioSeconds)) {
      GPS.setLocationFormat(Location.FORMAT_SECONDS);
      Toast.makeText(context,
        "Posicionamento agora em Graus, Minutos, Segundos decimais", Toast.LENGTH_SHORT).show();
    }
    else {
      GPS.setLocationFormat(Location.FORMAT_DEGREES);
      Toast.makeText(context,
        "Posicionamento agora em Graus decimais", Toast.LENGTH_SHORT).show();
    }
  }
}