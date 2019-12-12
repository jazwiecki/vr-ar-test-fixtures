package edu.colostate.cs.cs567.artestautomationsubject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ARTest {
    private static final String TAG = "ARTest_" + MainActivity.class.getSimpleName();
    private static final String AR_TEST_PACKAGE
            = "edu.colostate.cs.cs567.artestautomationsubject";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertNotNull(launcherPackage);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch app under test
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(AR_TEST_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(AR_TEST_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertNotNull(device);
    }

    @Test
    public void checkLabelTextAtStartup() throws UiObjectNotFoundException {
        UiObject textView = device.findObject(new UiSelector()
                                                .className("android.widget.TextView"));
        assertNotNull(textView);
        if (textView.exists()) {
            assertEquals("The space in front of you hasn't been processed yet.",
                    textView.getText());
        }
    }

    @Test
    public void moveForward() throws UiObjectNotFoundException, InterruptedException {
        assertTrue(validateMessageAfterMacro("Close", "move_forward", 5000));
    }

    @Test
    public void moveToStop() throws UiObjectNotFoundException, InterruptedException {
        assertTrue(validateMessageAfterMacro("Stop!", "move_to_stop", 5000));
    }

    @Test
    public void moveToOtherRoom() throws UiObjectNotFoundException, InterruptedException {
        assertTrue("Did not get expected text: `Avoid the obstacle in 3.6 meters`",
                validateMessageAfterMacro("Avoid the obstacle in 3",
                "move_to_other_room", 7000));
    }

    private boolean validateMessageAfterMacro(String message, String macro, int sleep)
            throws UiObjectNotFoundException, InterruptedException {
        Boolean emulatorCommandWorked = emulatorCommand(macro);

        Thread.sleep(sleep);

        UiObject textLabel = device.findObject(new UiSelector()
                .className("android.widget.TextView"));
        if (textLabel.exists()) {
            // startsWith will allow us to pass in partial strings w/o making a more
            // complicated test helper, in order to avoid code duplication
            return emulatorCommandWorked && textLabel.getText().startsWith(message);
        } else {
            Log.d(TAG, "Expected `" + message + "`, got `" + textLabel.getText() + "'.");
            return false;
        }
    }

    private boolean validateMessageAfterMacro(String message, String macro)
            throws UiObjectNotFoundException, InterruptedException {
        return validateMessageAfterMacro(message, macro, 10000);
    }

    private static boolean emulatorCommand(String command) {
        int port = 5554;
        InetAddress hostLoopback = null;
        boolean emulatorOk = true;

        try {
            hostLoopback = InetAddress.getByName("10.0.2.2");
        } catch (Exception ex) {
            Log.e(TAG, "TK error getting iNetAddress: " + ex);
        }

        try {
            Socket socket = new Socket(hostLoopback, port);
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("auth adzBj8sJ9DJyt1OS");
            writer.println("automation play " + command);
            writer.println("quit");

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            reader.lines().forEach(response -> {
                if (response.startsWith("KO")) {
                    Log.e(TAG, "Emulator response not OK: " + response);
                } else {
                    Log.d(TAG, "emulator socket response: " + response);
                }
            });

            emulatorOk = reader.lines().noneMatch(
                    (s) -> s.startsWith("KO")
            );

            writer.close();
            reader.close();
            socket.close();
            return emulatorOk;
        } catch (UnknownHostException ex) {
            Log.e(TAG, "Emulator host loopback not found: " + ex.getMessage());
        } catch (SocketException ex) {
            Log.e(TAG, "Emulator loopback socket exception: " + ex);
        } catch (IOException ex) {
            Log.e(TAG, "Emulator loopback IOException " + ex);
        }
        return false;
    }

    private String getLauncherPackageName() {
        // Create launcher intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager packageManager = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
