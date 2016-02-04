package com.example.razor.ejevika;


import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.json.Parser;
import com.example.razor.ejevika.json.Requestor;
import com.example.razor.ejevika.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.Keys.EndpointCategory.*;

/**
 * Created by Администратор on 04.02.2016.
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class RequestorTest extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void categoryRequestTest() throws JSONException {
        String url = "http://testing.aliens.pro/api?category=all";
        JSONArray responce = Requestor.categoryRequest(VolleySingleton.getInstance().getmRequestQueue(), url);
        assertNotNull(responce);

    }
}
