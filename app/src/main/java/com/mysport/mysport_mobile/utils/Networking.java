package com.mysport.mysport_mobile.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysport.mysport_mobile.models.Member;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.err;
import static java.lang.System.out;

public class Networking {

    public static void volleyPost(Context context, String url, JSONObject postData){
        volleyPost(context, url, postData, null);
    }

    public static void volleyPost(Context context, String url, JSONObject postData, final VolleyCallBack callBack){
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                int statusCode = 0;
                String message = "";
                try {
                    statusCode = res.getInt("status");
                    message = res.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(callBack == null)
                    return;

                if(statusCode > 199 & statusCode < 300){
                    try {
                        callBack.onSuccess(new Member(
                                res.getInt("user_type"),
                                res.getString("firstname"),
                                res.getString("lastname"),
                                res.getString("email"),
                                null
                                )
                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(statusCode > 399 & statusCode < 500)
                    callBack.onError(statusCode, message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                out.println("message: " + error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public static void volleyGet(Context context, String url){
        List<String> jsonResponses = new ArrayList<>(5);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String email = jsonObject.getString("email");

                        jsonResponses.add(email);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public interface VolleyCallBack {
        void onSuccess(Member member);

        void onError(int statusCode, String message);
    }
}
