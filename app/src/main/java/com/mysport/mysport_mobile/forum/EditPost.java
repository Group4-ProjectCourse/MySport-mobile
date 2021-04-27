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
import com.mysport.mysport_mobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EditPost extends AppCompatActivity {

    //entry point URL
    public String entryPointURL;
    public String username, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        entryPointURL = getString(R.string.server_url);
        //get information from intent
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        //initialize screen (fetch post data and populate)
        initializeScreen();
    }

    //cancels post edit process
    public void cancelEditPost(View view){
        //bring user back to view post screen
        Intent intent = new Intent(this, ViewPost.class);
        intent.putExtra("title", title);
        intent.putExtra("username", username);
        //call activity
        startActivity(intent);
    }

    //submits the edit to the database
    public void submitEditPost(View view){
        //grab references to views
        EditText titleText = findViewById(R.id.edit_post_title);
        EditText contentText = findViewById(R.id.edit_post_content);
        final String newTitle = titleText.getText().toString();
        String newContent = contentText.getText().toString();

        //make url with original title so we can look up the original post
        String url = "";
        try {
            url = entryPointURL + "posts/" + URLEncoder.encode(title, "UTF-8");
        } catch(UnsupportedEncodingException e){
            Log.e("Error", "Caught encoding exception: " + e);
        }

        //initialize volley queue
        RequestQueue queue = Volley.newRequestQueue(this);

        //create json object
        JSONObject jsonObject = new JSONObject();
        try {
            //send new title
            jsonObject.put("title", newTitle);
            jsonObject.put("content", newContent);
        } catch(JSONException e) {
            Log.e("Error", "Caught JSON object exception: " + e);
        }

        //create request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //check response status
                try {
                    int responseStatus = response.getInt("status");
                    if(responseStatus == 1){
                        //request was good
                        //make toast then redirect back to view post
                        Toast success = Toast.makeText(EditPost.this, R.string.edit_post_success, Toast.LENGTH_SHORT);
                        success.setGravity(Gravity.BOTTOM, 0, 50);
                        success.show();
                        //make intent with new title and username
                        Intent intent = new Intent(EditPost.this, ViewPost.class);
                        intent.putExtra("username", username);
                        intent.putExtra("title", newTitle);
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
                Log.e("Error", "Volley error submiting post request: " + error);
            }
        });

        //submit request
        queue.add(request);
    }

    //grabs original post data from intent extras
    //and fills out views
    public void initializeScreen(){
        //get references to views
        EditText oldTitle = (EditText) findViewById(R.id.edit_post_title);
        EditText oldContent = (EditText) findViewById(R.id.edit_post_content);
        //populate views
        oldTitle.setText(title);
        oldContent.setText(content);
    }
}
