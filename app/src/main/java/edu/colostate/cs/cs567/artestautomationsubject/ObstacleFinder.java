package edu.colostate.cs.cs567.artestautomationsubject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;

import java.util.List;

public class ObstacleFinder {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;

    public ObstacleFinder(Context context) {
        this.context = context;
    }

    protected void processRayTracing(Frame frame) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Float center_dist = (float) (0.0);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                List<HitResult> results = frame.hitTest(
                        (float) (size.x * i * 0.25),
                        (float) (size.y * j * 0.25));
                for (HitResult hr : results) {
                    center_dist = center_dist / 2 + hr.getDistance() / 2;
                    break;
                }
                Log.d(TAG, "center_dist is " + center_dist.toString());
            }
        }

        updateText(R.id.feedback, distanceMessage(center_dist));
    }

    protected String distanceMessage(Float distance) {
        if (distance > 5) {
            return "Not close to anything";
        } else if (0.01 < distance && distance < 1.0) {
            return "Very close";
        } else if (distance <= 0.1) {
            return "Stop!";
        } else {
            return "Avoid the obstacle in " + String.format("%.1f", distance) + " meters";
        }
    }

    private void updateText(int textDestination, String textContent) {
        TextView textView = (TextView) ((Activity) context).findViewById(textDestination);
        textView.setText(textContent);
    }
}
