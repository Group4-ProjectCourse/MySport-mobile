package com.mysport.mysport_mobile.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.ForumPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EditPost extends AppCompatActivity {

    //entry point URL
    public String username;
    private Bundle bundle;
    private ForumPost forumPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        //get information from intent
        Intent intent = getIntent();
        bundle = intent.getExtras();

        String id = bundle.getString("id");
        String title = bundle.getString("title");
        String username = bundle.getString("username");
        String author = bundle.getString("author");
        String createdAt = bundle.getString("time");
        String content = bundle.getString("content");
        String lastEditTime = bundle.getString("lastEditTime");
        String timeRaw = bundle.getString("timeRaw");

        this.username = username;
        forumPost = new ForumPost(id, author, title, createdAt, content, timeRaw);

        //initialize screen (fetch post data and populate)
        initializeScreen();
    }

    //cancels post edit process
    public void cancelEditPost(View view){
        //bring user back to view post screen
        Intent intent = new Intent(this, ViewPost.class);
        Bundle bundle = new Bundle(8);
        bundle.putString("username", username);
        bundle.putString("title", forumPost.getTitle());
        bundle.putString("author", forumPost.getAuthor());
        bundle.putString("id", forumPost.getId());
        bundle.putString("time", forumPost.getCreatedAt());
        bundle.putString("content", forumPost.getContent());
        bundle.putString("lastEditTime", forumPost.getLastModified());
        bundle.putString("timeRaw", forumPost.getTimeRaw());

        intent.putExtras(bundle);
        startActivity(intent);
    }

    //submits the edit to the database
    public void submitEditPost(View view){
        String lastEdit = Long.toString(System.currentTimeMillis());
        forumPost.setLastModified(lastEdit);
        //grab references to views
        EditText titleText = findViewById(R.id.edit_post_title);
        EditText contentText = findViewById(R.id.edit_post_content);
        String newTitle = titleText.getText().toString();
        String newContent = contentText.getText().toString();
        forumPost.setTitle(newTitle)
                .setContent(newContent);

        //make url with original title so we can look up the original post
        String url = "";
        try {
            url = App.baseURL + "posts/" + URLEncoder.encode(forumPost.getId(), "UTF-8");
        } catch(UnsupportedEncodingException e){
            Log.e("Error", "Caught encoding exception: " + e);
        }

        //initialize volley queue
        RequestQueue queue = Volley.newRequestQueue(this);

        //create json object
        JSONObject jsonObject = new JSONObject();
        try {
            //send new title
            jsonObject.put("newTitle", newTitle);
            jsonObject.put("newContent", newContent);
            jsonObject.put("lastEdit", lastEdit);
        } catch(JSONException e) {
            Log.e("Error", "Caught JSON object exception: " + e);
        }

        //create request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //check response status
                try {
                    String responseStatus = response.getString("status");
                    if(responseStatus.equals("200") || responseStatus.equals("OK")){
                        //request was good
                        //make toast then redirect back to view post
                        Toast success = Toast.makeText(EditPost.this, R.string.edit_post_success, Toast.LENGTH_SHORT);
                        success.setGravity(Gravity.BOTTOM, 0, 50);
                        success.show();
                        //make intent with new title and username
                        Intent intent = new Intent(EditPost.this, ViewPost.class);
                        Bundle bundle = new Bundle(8);

                        bundle.putString("username", username);
                        bundle.putString("title", forumPost.getTitle());
                        bundle.putString("author", forumPost.getAuthor());
                        bundle.putString("id", forumPost.getId());
                        bundle.putString("time", forumPost.getCreatedAt());
                        bundle.putString("content", forumPost.getContent());
                        bundle.putString("lastEditTime", forumPost.getLastModified());
                        bundle.putString("timeRaw", forumPost.getTimeRaw());

                        intent.putExtras(bundle);
                        //call activity
                        startActivity(intent);
                    }
                } catch(JSONException e) {
                    Log.e("Error", "Caught JSON object exception: " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Volley error submitting post request: " + error);
            }
        });

        //submit request
        queue.add(request);
    }

    //grabs original post data from intent extras
    //and fills out views
    public void initializeScreen(){
        //get references to views
        EditText oldTitle = findViewById(R.id.edit_post_title);
        EditText oldContent = findViewById(R.id.edit_post_content);
        //populate views
        oldTitle.setText(forumPost.getTitle());
        oldContent.setText(forumPost.getContent());
    }
}
