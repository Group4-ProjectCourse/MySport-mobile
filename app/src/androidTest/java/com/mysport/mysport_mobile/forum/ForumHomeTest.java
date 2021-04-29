package com.mysport.mysport_mobile.forum;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mysport.mysport_mobile.App;

import junit.framework.TestCase;

import org.json.JSONArray;


public class ForumHomeTest extends TestCase {

    int actualPostCount = 10;
    int expectedPostsCount = 0;

    void getForumPostListSize() {
        String url = App.baseURL + "posts";

        new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), response -> {
            actualPostCount = response.length();
        }, error -> Log.e("Error", "Volley error getting posts: " + error));

        assertEquals(expectedPostsCount, actualPostCount);
    }
}