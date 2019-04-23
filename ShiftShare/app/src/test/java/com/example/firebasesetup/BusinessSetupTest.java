package com.example.firebasesetup;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BusinessSetupTest {
            private BusinessSetup utils;

        @Before
        public void setup(){
            utils = new BusinessSetup();
        }


    @Test
    public void aValidFiveDigitZipCodePasses() throws Exception{

            assertTrue(utils.isValidZip("35401"));
    }


    @Test
    public void aValidNineDigitZipCodePasses() throws Exception{

        assertTrue(utils.isValidZip("35401-1245"));
    }



    @Test
    public void anInValidZipCodeFails() throws Exception{

        assertTrue(!utils.isValidZip("123"));
    }


    @Test
    public void aValidStoreNumberPasses() throws Exception{

        assertTrue(utils.isValidStoreNumber("13245"));
    }

    @Test
    public void anInValidStoreNumberFails() throws Exception{

        assertTrue(!utils.isValidStoreNumber("HasLettersInIt"));
    }







}