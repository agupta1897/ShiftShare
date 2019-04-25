package com.example.firebasesetup;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class EPortalTest extends ActivityInstrumentationTestCase2 {
    public EPortalTest() {
        super(EPortal.class);
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

    //Assures that the spinners exist on startup
    //Testing all three buttons in one test is "bad testing form", but i decided against padding with three separate functions for now, probably should change it to just 1 test
    @SmallTest
    public void testSpinner() {
        Spinner spin = (Spinner)getActivity().findViewById(R.id.spinner);
        Spinner spin2 = (Spinner)getActivity().findViewById(R.id.spinner2);
        Spinner spin3 = (Spinner)getActivity().findViewById(R.id.spinner3);
        assertNotNull(spin);
        assertNotNull(spin2);
        assertNotNull(spin3);
    }

    //Assures that the availability fields exist on startup
    @SmallTest
    public void testTextView() {
        TextView text = (TextView) getActivity().findViewById(R.id.mondayAvailability);
        assertNotNull(text);
    }

    //Assures that the Submit button exists on startup
    @SmallTest
    public void testButton(){
        Button btn = (Button)getActivity().findViewById(R.id.btn_submit);
        assertNotNull(btn);
    }


    /* END OF TESTS */

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
