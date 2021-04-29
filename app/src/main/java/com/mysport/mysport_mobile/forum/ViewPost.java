package com.mysport.mysport_mobile.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.models.ForumPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ViewPost extends AppCompatActivity {

    //entry point URL
    //declare username string that we will use throughout activity
    public String username;
    public Bundle bundle;
    //volley queue to process requests
    public RequestQueue queue;
    //references for view comments page
    public TextView originalContent, originalTitle, originalAuthor, lastModifiedView;
    //references to Edit and Delete Post buttons so that we can hide them
    public Button editBtn, deleteBtn;

    private ForumPost forumPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        //get username and post title from intent
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

        Log.d("After view: ", bundle.toString());

        this.username = username;
        forumPost = new ForumPost(id, author, title, createdAt, content, timeRaw);

        //get views
        originalContent = findViewById(R.id.view_post_content);
        originalAuthor = findViewById(R.id.view_post_author);
        originalTitle = findViewById(R.id.view_post_title);
        lastModifiedView = findViewById(R.id.view_post_lastmodified);
        editBtn = findViewById(R.id.button_edit_post);
        deleteBtn = findViewById(R.id.button_delete_post);
        //hide buttons
        editBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setVisibility(View.INVISIBLE);

        //initialize the queue
        queue = Volley.newRequestQueue(this);

        //fetch post data
        //fetchPostData();
        //fetch comments

        String authorFull = "Author: " + author;
        originalAuthor.setText(authorFull);
        originalTitle.setText(title);
        originalContent.setText(content);
//        long millis = Long.parseLong(lastEditTime);
//        if(millis != 0)
//            lastModifiedView.setText(("last modified: " +
//                    new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.UK)
//                            .format(new Date(millis)))
//            );

        fetchComments();
    }

    //function that will fetch comments and populate the screen view
    public void fetchComments(){
        //make url
        String url = "";
        try {
            url = App.baseURL + "posts/comments/" + URLEncoder.encode(forumPost.getId(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            Log.e("Error", "Caught encoding exception: " + e);
        }

        //make request
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //iterate through the response and create a view for each comment
                commentsToViews(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Volley error with GET request for all comments: " + error);
            }
        });

        //send request
        queue.add(request);
    }

    //function that will take array of comment objects
    //and convert them to views to be added to screen
    public void commentsToViews(JSONArray array){
        //grab comments section view
        LinearLayout commentsSection = findViewById(R.id.comments_section);
        //iterate through each comment object and create a view
        for(int i = 0; i < array.length(); i++){
            try {
                String author = array.getJSONObject(i).getString("author");
                String content = array.getJSONObject(i).getString("content");
                String date = array.getJSONObject(i).getString("time");
                Log.d("Debug", "Element: " + author + " " + content + " " + date);
                //now create a view to house each element
                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView authorView = new TextView(this);
                TextView contentView = new TextView(this);
                authorView.setText("Author: " + author);
                authorView.setTextSize(18);
                contentView.setText(content);
                contentView.setTextSize(15);
                //add to parents
                linearLayout.addView(authorView);
                linearLayout.addView(contentView);
                commentsSection.addView(linearLayout);

            } catch(JSONException e) {
                Log.e("Error", "Caught JSON object exception: " + e);
            }
        }
    }

    //function that will fetch post data from the API
    //deprecated
//    public void fetchPostData(){
//        //make URL
//        String url = "";
//        try {
//            url = App.baseURL + "posts/" + URLEncoder.encode(forumPost.getId(), "UTF-8");
//        } catch(UnsupportedEncodingException e){
//            Log.e("Debug", "Caught encoding exception: " + e);
//        }
//
//        //construct JSON object
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id", forumPost.getId());
//        } catch (JSONException e) {
//            Log.e("Error", "Caught JSON exception: " + e);
//        }
//        //create request
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    //get data from the post
//                    String author = response.getString("author");
//                    String title = response.getString("title");
//                    String content = response.getString("content");
//                    String lastModified = response.getString("lastEditTime");
//                    long millis = Long.parseLong(lastModified);
//                    //now populate our views with it
//                    String authorFull = "Author: " + author;
//                    originalAuthor.setText(authorFull);
//                    originalTitle.setText(title);
//                    originalContent.setText(content);
//                    if(millis != 0)
//                        lastModifiedView.setText(("last modfified: " +
//                            new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.UK)
//                                    .format(new Date(millis)))
//                        );
//
//                    postContent = content;
//                    Log.d("Debug", "Current user: " + username + "\nAuthor: " + author);
//                    //check if current user is author
//                    if(username.equals(author)){
//                        Log.d("Debug", "Current user is author.");
//                        //show edit / delete buttons
//                        showButtons(editBtn, deleteBtn);
//                    }
//                } catch (JSONException e) {
//                    Log.e("Error", "Caught JSON exception: " + e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", "Volley error: " + error);
//            }
//        });
//        //submit request
//        queue.add(request);
//    }

    //function to show delete and edit buttons
    public void showButtons(Button btn1, Button btn2){
        btn1.setVisibility(View.VISIBLE);
        btn2.setVisibility(View.VISIBLE);
    }

    //prompts the user when pressing the delete button
    public void promptDeletePost(View view){
        RelativeLayout promptContainer = findViewById(R.id.delete_post_prompt);
        promptContainer.setVisibility(View.VISIBLE);
    }

    //cancels the delete post prompt
    public void cancelDeletePost(View view){
        RelativeLayout promptContainer = findViewById(R.id.delete_post_prompt);
        promptContainer.setVisibility(View.INVISIBLE);
    }

    //function to delete post if user is author
    public void deletePost(View view){
        //delete post (and all comments) and take back to home
        //make url
        String url = "";
        try {
            url = App.baseURL + "posts/" + URLEncoder.encode(forumPost.getId(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            Log.e("Error", "Caught encoding exception: " + e);
        }

        //make JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", forumPost.getId());
        } catch(JSONException e) {
            Log.e("Error", "Caught JSON object exception: " + e);
        }

        //make request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //get status
                try {
                    int responseStatus = response.getInt("status");
                    if(responseStatus == 200){
                        //success, post has been deleted
                        //create toast
                        Toast success = Toast.makeText(ViewPost.this, R.string.delete_post_success, Toast.LENGTH_SHORT);
                        success.setGravity(Gravity.BOTTOM, 0, 50);
                        success.show();
                        //take us back to home screen
                        //make dummy view so that we can use goBack()
                        View dummyView = new View(ViewPost.this);
                        goBack(dummyView);
                    }
                } catch(JSONException e) {
                    Log.e("Error", "Caught JSON object exception: " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Volley error sending delete request: " + error);
            }
        });

        //submit request
        queue.add(request);
    }

    //function to edit post if user is author
    public void editPost(View view){
        //create intent with username and post title
        Intent intent = new Intent(this, EditPost.class);
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
        //call new activity
        startActivity(intent);
    }

    //function to leave comment
    public void createComment(View view){
        //create intent with username and post title
        Intent intent = new Intent(ViewPost.this, CreateComment.class);
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

    //function to go back to home screen
    public void goBack(View view){
        //create intent with the user's name
        Intent intent = new Intent(this, ForumHome.class);
        intent.putExtra("username", username);
        //call home activity
        startActivity(intent);
    }

    //TODO (optional)
    //function to edit comment if user is author
}
