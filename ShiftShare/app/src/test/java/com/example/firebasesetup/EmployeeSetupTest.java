package com.example.firebasesetup;

import android.support.v4.widget.TextViewCompat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmployeeSetupTest {
            private EmployeeSetup utils;

        @Before
        public void setup(){
            utils = new EmployeeSetup();
        }


    @Test
    public void aValidPhoneNumberPasses() throws Exception{

            assertTrue(utils.isValidPhoneNumber("2054352352"));
    }

    @Test
    public void anInValidPhoneNumberFails() throws Exception{

        assertTrue(!utils.isValidPhoneNumber("20543"));
    }


    @Test
    public void aValidNamePasses() throws Exception{

        assertTrue(utils.isValidName("Amber"));
    }

    @Test
    public void anInValidNameFails() throws Exception{

        assertTrue(!utils.isValidPhoneNumber(""));
    }








}