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

public class CreateComment extends AppCompatActivity {

    //entry point URL
    //declare username string that we will use throughout activity
    public String username;
    //declare string for title of post we are viewing
    //volley queue to process requests
    public RequestQueue queue;
    //reference to comment content
    public EditText commentContent;
    private ForumPost forumPost;
    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);

        //get intent extras
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

        //set up references
        commentContent = findViewById(R.id.comment_content);

        //initialize volley queue
        queue = Volley.newRequestQueue(this);
    }

    //function that will take user back to the view post page on button click
    public void cancelComment(View view){
        //create intent with current user and the title of the post
        Intent intent = new Intent(CreateComment.this, ViewPost.class);
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

    //function that will save the comment to the database and
    //redirect the user to the view post page
    public void submitComment(View view){
        //make url
        String url = "";
        try {
            url = App.baseURL + "posts/comment/" + URLEncoder.encode(forumPost.getId(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            Log.e("Error", "Caught encoding exception: " + e);
        }

        //grab timestamp and comment content
        String commentContent = this.commentContent.getText().toString();
        //get timestamp since epoch
        long timeStamp = System.currentTimeMillis();
        String ts = Long.toString(timeStamp);

        //make JSON object with timestamp, author, and content
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("content", commentContent);
            jsonObject.put("author", username);
            jsonObject.put("time", ts);
        } catch(JSONException e) {
            Log.e("Error", "Caught JSON object exception: " + e);
        }

        //make request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //get response status
                try {
                    String responseStatus = response.getString("status");
                    Log.d("status responcse: ", responseStatus);
                    //check status
                    if(responseStatus.equals("200") || responseStatus.equals("OK")){
                        //request was good
                        //make success toast
                        Toast success = Toast.makeText(CreateComment.this, R.string.create_comment_success, Toast.LENGTH_SHORT);
                        success.setGravity(Gravity.BOTTOM, 0, 50);
                        success.show();
                        //create intent and redirect back to view post page
                        Intent intent = new Intent(CreateComment.this, ViewPost.class);
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
                } catch(JSONException e) {
                    Log.e("Error", "Caught JSON object exception: " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Volley error submitting comment POST: " + error);
            }
        });

        //submit request
        queue.add(request);
    }
}
