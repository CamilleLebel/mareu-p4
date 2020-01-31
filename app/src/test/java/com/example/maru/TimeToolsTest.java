package com.example.maru;

import com.example.maru.utils.TimeTools;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TimeToolsTest {

    // METHODS -------------------------------------------------------------------------------------

    @Test
    public void timeTools_convertSecondToString() {
        assertEquals("00:00", TimeTools.convertSecondToString(0));
        assertEquals("15:00", TimeTools.convertSecondToString(54000));
        assertEquals("18:15", TimeTools.convertSecondToString(65700));
    }
}
