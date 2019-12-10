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
import com.google.ar.sceneform.HitTestResult;

import java.util.List;

public class ObstacleFinder {
    private static final String TAG = ObstacleFinder.class.getSimpleName();
    private Context context;

    public ObstacleFinder(Context context) {
        this.context = context;
    }

    protected void processRayTracing(Frame frame) {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Float centerDistance;

        List<HitResult> hitResults = frame.hitTest(
                (float) (size.x / 2),
                (float) (size.y / 2));

        if (!hitResults.isEmpty()) {
            centerDistance = hitResults.get(0).getDistance();
            Log.d(TAG, String.format("%d", hitResults.size()) + " hitResult(s), "
                    + centerDistance.toString() + " " + distanceMessage(centerDistance));
        } else {
            // safe to assume nothing is being hit so...nothing is there?
            Log.d(TAG, "No hit results");
            centerDistance = 6.0f;
        }

        updateText(R.id.feedback, distanceMessage(centerDistance));

    }

    protected String distanceMessage(Float distance) {
        if (distance > 5) {
            return "Not close to anything";
        } else if (1.0 < distance && distance < 3.0) {
            return "Close";
        } else if (distance <= 1.0) {
            return "Stop!";
        } else {
            return "Avoid the obstacle in " + String.format("%.1f", distance) + " meters";
        }
    }

    private void updateText(int textDestination, String textContent) {
        TextView textView = (TextView) ((Activity) context).findViewById(textDestination);
        textView.setText(textContent);
    }

    protected void lostTracking() {
        updateText(R.id.feedback, "Device isn't tracking, move phone around to re-enable.");
    }
}
