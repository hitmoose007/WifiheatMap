package com.example.heatmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HeatMapView extends View {
    private List<Point> points = new ArrayList<>(); // List to store points
    private Paint paint = new Paint();
    private int pointSize = 80; // Increase the size of the points
    private int centerColor = Color.RED; // Initial center color
    private int edgeColor = Color.GREEN; // Initial edge color
    private WifiSignalStrength wifiSignalStrength; // Add a WifiSignalStrength instance


    public HeatMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        wifiSignalStrength = new WifiSignalStrength(context); // Initialize WifiSignalStrength

        initializePaint();
    }

    private void initializePaint() {
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all the points in the list
        for (Point point : points) {
            Shader shader = getRadialGradient(point.x, point.y, point.color);
            paint.setShader(shader);
            canvas.drawCircle(point.x, point.y, pointSize, paint);
        }
    }

    private Shader getRadialGradient( float x , float y,int color) {
//        int red = Color.red(centerColor);
//        int green = (255 * intensity) / 100;
//        int blue = 0;
//        int alpha = Color.alpha(centerColor);

//        int startColor = Color.argb(alpha, red, green, blue);
        int endColor = Color.argb(0, 0, 0, 0); // Transparent color



        return new RadialGradient(x, y, pointSize, color, endColor, Shader.TileMode.CLAMP);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int wifiRssi = wifiSignalStrength.getSignalStrength();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                points.add(new Point(x, y, mapRssiToColor(wifiRssi)));
                invalidate(); // Redraw the View
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void setEdgeColor(int color) {
        edgeColor = color;
        invalidate(); // Redraw the View with the new edge color
    }
    public void removeLastPoint() {
        if (!points.isEmpty()) {
            points.remove(points.size() - 1);
            invalidate(); // Redraw the View after removing the point
        }
    }

    private int mapRssiToColor(int wifiRssi) {
        if (wifiRssi >= -50) {
            // Strong Signal: Green or Blue
            return Color.rgb(0, 255, 0); // You can use Color.BLUE if you prefer blue
        } else if (wifiRssi >= -60) {
            // Moderate Signal: Yellow or Yellow-Orange
            return Color.rgb(200, 220, 51)
                    ; // You can use Color.parseColor("#FFA500") for yellow-orange
        } else if (wifiRssi >= -70) {
            // Weak Signal: Red or Orange

            return Color. // You can use Color.parseColor("#FFA500") for orange
            rgb(255, 255, 0);
        } else if (wifiRssi >=-80){
            return Color.rgb(255, 102, 0);

        }else {

            // No Signal: Gray or Black
            return Color.rgb(255, 0, 0)
                    ; // You can use Color.BLACK for black
        }
    }



    // Custom Point class to store x, y coordinates, and intensity
    private static class Point {
        float x;
        float y;
        int color;

        Point(float x, float y, int color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }
    public void setupRemoveLastPointButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLastPoint();
                showToast("Last Point Removed");
            }
        });
    }

    // Existing code...

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
