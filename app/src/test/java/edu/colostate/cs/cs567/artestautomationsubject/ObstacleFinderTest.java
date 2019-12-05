package edu.colostate.cs.cs567.artestautomationsubject;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ObstacleFinderTest {
    Context context;
    ObstacleFinder obstacleFinder;

    @Before
    public void mockContext() {
        context = mock(Context.class);
        obstacleFinder = new ObstacleFinder(context);
    }

    @Test
    public void distanceMessageIsCorrect() {
        // TODO move navigation-related functions into own class for unit testing etc
//        assertEquals(true, true);
        assertEquals("Incorrect distance message",
                "Stop!",
                obstacleFinder.distanceMessage((float) 0.01));
    }
}