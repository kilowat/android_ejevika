package com.example.razor.ejevika;

import android.test.InstrumentationTestCase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Администратор on 02.02.2016.
 */
public class ParserTest   {

   @Test
    public void parserCategory(){
        assertEquals("test","Салаты","Салаты");
    }
}
