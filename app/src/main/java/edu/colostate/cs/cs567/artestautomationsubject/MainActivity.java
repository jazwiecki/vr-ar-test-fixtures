package edu.colostate.cs.cs567.artestautomationsubject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.HitResult;
import com.google.ar.core.TrackingState;
import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private FrameTime frameTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }
        setContentView(R.layout.activity_main);

        // get main arFragment
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);
        //disable hand manipulation animation
        arFragment.getPlaneDiscoveryController().hide();
        arFragment.getPlaneDiscoveryController().hide();

        ObstacleFinder obstacleFinder = new ObstacleFinder(arFragment.getContext());

        // add listener for scene updates
        final ArSceneView arSceneView = arFragment.getArSceneView();
        arSceneView.getScene().addOnUpdateListener(
                frameTime -> {
                    Frame frame = arSceneView.getArFrame();
                    if (frame == null) {
                        return;
                    }
                    if (frame.getCamera().getTrackingState() != TrackingState.TRACKING) {
                        return;
                    }

                    for (Plane plane : frame.getUpdatedTrackables(Plane.class)) {
                        if (plane.getTrackingState() == TrackingState.TRACKING) {
                            obstacleFinder.processRayTracing(frame);
                        }
                    }
                }
        );
    }


    private static boolean checkIsSupportedDeviceOrFinish(final MainActivity mainActivity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(mainActivity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                    .show();
            mainActivity.finish();
            return false;
        }
        String openGLVersionString =
                ((ActivityManager) mainActivity.getSystemService(Context.ACTIVITY_SERVICE))
                    .getDeviceConfigurationInfo()
                .getGlEsVersion();
        if (Double.parseDouble(openGLVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 or later");
            Toast.makeText(mainActivity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            mainActivity.finish();
            return false;
        }
        return true;
    }
}
