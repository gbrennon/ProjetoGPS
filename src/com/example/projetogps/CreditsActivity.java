package com.example.projetogps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreditsActivity extends Activity {
  Button glauberButton, fabinhoButton, matheusButton;
  Intent intent;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_credits);

    glauberButton = (Button) findViewById(R.id.glauberButton);
    addListenerOnButton(glauberButton, R.id.glauberButton, Uri.parse("http://github.com/gbrennon"));
    addListenerOnButton(fabinhoButton, R.id.fabinhoButton, Uri.parse("http://github.com/santanafabio"));
    addListenerOnButton(matheusButton, R.id.matheusButton, Uri.parse("http://github.com/mathmix"));
  }

  private void addListenerOnButton(Button button, int viewId, final Uri uri) {
    // Método para linkar o botão com sua Activity! Utilizem ele!
    button = (Button) findViewById(viewId);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
      }
    });
  }
}