package com.example.firebasesetup;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2 {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Test
    public void useAppContext() {
        // This was in the Example file to i kept it
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.firebasesetup", appContext.getPackageName());
    }


    public void setUp() throws Exception {
        super.setUp();
    }


    /* START OF TESTS */

    //Assures that the email text field exists on startup
    @SmallTest
    public void testEditText() {
        EditText text = (EditText)getActivity().findViewById(R.id.editTextEmail);
        assertNotNull(text);
    }

    //Assures that the Login button exists on startup
    @SmallTest
    public void testButton(){
        Button btn = (Button)getActivity().findViewById(R.id.login);
        assertNotNull(btn);
    }


    /* END OF TESTS */

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
