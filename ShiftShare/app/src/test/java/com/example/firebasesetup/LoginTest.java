package com.example.firebasesetup;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTest {
            private Login utils;

        @Before
        public void setup(){
            utils = new Login();
        }

    @Test
    public void aValidEmailAddressPasses() throws Exception{

        assertTrue(utils.isValidEmailAddress("email@email.com"));
    }

    @Test
    public void anInValidEmailAddressFails() throws Exception{

        assertTrue(!utils.isValidEmailAddress("emailmissingdot"));
    }

    @Test
    public void aValidPasswordPasses() throws Exception{

        assertTrue(utils.isValidPassword("123456"));
    }

    @Test
    public void anInvalidPasswordFails() throws Exception{

            assertTrue(!utils.isValidPassword("12345"));
    }

    @Test
    public void anEmptyEmailAddressFails() throws Exception{

            assertTrue(!utils.isValidEmailAddress(""));
    }

    @Test
    public void anEmptyPasswordFails() throws Exception{

        assertTrue(!utils.isValidPassword(""));
    }







}