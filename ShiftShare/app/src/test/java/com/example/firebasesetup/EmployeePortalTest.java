package com.example.firebasesetup;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmployeePortalTest {
            private EPortal utils;

        @Before
        public void setup(){
            utils = new EPortal();
        }


    @Test
    public void aValidHoursEntry() throws Exception{

            assertTrue(utils.validHours(0, 1200));
    }

    @Test
    public void anInValidHoursEntry() throws Exception{

        assertTrue(!utils.validHours(200,0));
    }


    @Test
    public void aNewHour() throws Exception{

        assertTrue(utils.newHour(1260));
    }

    @Test
    public void notNewHour() throws Exception{

        assertTrue(!utils.newHour(1230));
    }








}