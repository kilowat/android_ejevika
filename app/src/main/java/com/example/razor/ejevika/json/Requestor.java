package com.example.razor.ejevika.json;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.razor.ejevika.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.transform.ErrorListener;

/**
 * Created by Администратор on 04.02.2016.
 */
public class Requestor {
    public static String REQ_LOG_TAG = "req_log_tag";

    public static JSONArray categoryRequest(RequestQueue requestQueue, String url){
        JSONArray responce = null;
        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, requestFuture,requestFuture);
        requestQueue.add(request);

        try {
            responce = requestFuture.get(30000, TimeUnit.MILLISECONDS);
            Log.d(REQ_LOG_TAG,responce.toString()+"responce category complite");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return responce;
    }

    public static JSONArray productRequset(RequestQueue requestQueue, String url){
        JSONArray responce = null;
        RequestFuture<JSONArray> requestFuture = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, requestFuture, requestFuture);
        requestQueue.add(request);

        try {
            responce = requestFuture.get(10000, TimeUnit.MILLISECONDS);
            Log.d(REQ_LOG_TAG,responce.toString()+"responce product complite");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return responce;
    }
}
