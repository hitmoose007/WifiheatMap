package com.example.heatmap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        HeatMapView heatMapView = findViewById(R.id.heatMapView);
        Button removeLastPointButton = findViewById(R.id.undoButton);

        // Set up the click listener for the "Remove Last Point" button
        heatMapView.setupRemoveLastPointButton(removeLastPointButton);
    }
}
