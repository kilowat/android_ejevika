package com.example.razor.ejevika;

import com.example.razor.ejevika.dummy.Category;
import com.example.razor.ejevika.json.Parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.example.razor.ejevika.extras.Keys.EndpointCategory.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by razor on 02.02.2016.
 */
public class ParserTest {

    @Test
    public void parserCategory() throws JSONException {
        int id = 1;
        String name = "Салаты";
        String picture = "/upload/iblock/5f7/5f729baedbe28701b63ff88f0e020dd4.jpg";

        ArrayList<Category> expected = new ArrayList<>();
        expected.add(new Category(id,picture,name));

        JSONObject param = new JSONObject();
        param.put(KEY_NAME,name);
        param.put(KEY_ID,id);
        param.put(KEY_PICTURE, picture);

        JSONArray paramArray = new JSONArray();
        paramArray.put(param);

        ArrayList<Category> result = Parser.parseCategoryJSON(paramArray);

        assertEquals("test complite ok", expected, result);
    }
}
