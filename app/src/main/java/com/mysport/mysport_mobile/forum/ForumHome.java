package com.mysport.mysport_mobile.forum;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mysport.mysport_mobile.App;
import com.mysport.mysport_mobile.MainActivity;
import com.mysport.mysport_mobile.R;
import com.mysport.mysport_mobile.adapters.ForumPostAdapter;
import com.mysport.mysport_mobile.models.ForumPost;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ForumHome extends AppCompatActivity implements ForumPostAdapter.OnRecyclerItemClick {

    //entry point URL
    //public String entryPointURL;
    //declare username string that we will use throughout activity
    public String username;
    //declare profile picture reference
    //public ImageView profilePicture;
    //declare upload picture prompt reference
    public RelativeLayout uploadPrompt;
    //declare volley queue that will carry out HTTP requests
    public RequestQueue queue;
    private RecyclerView recyclerView;
    private ForumPostAdapter forumPostAdapter;
    private ArrayList<ForumPost> forumPostList;
    //for taking a picture
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.forum_recyclerview);
        forumPostList = new ArrayList<>(15);
        //forumPostAdapter = new ForumPostAdapter(forumPostList, this);
        forumPostAdapter = new ForumPostAdapter();
        forumPostAdapter.setOnRecyclerItemClick(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(forumPostAdapter);

        //entryPointURL = getString(R.string.server_url);
        //initialize volley queue
        queue = Volley.newRequestQueue(this);
        //grab reference to profile picture and username
        //username = this.getIntent().getStringExtra("username");
        username = "Tom Nillson";
        //profilePicture = findViewById(R.id.profile_picture);
        //TextView profileName = findViewById(R.id.profile_name);
        //set username
        //profileName.setText("Hello " + username + "!");
        //get prompt
        uploadPrompt = findViewById(R.id.upload_prompt);
        uploadPrompt.setVisibility(View.INVISIBLE);

        //initialize profile picture
        //initializeProfilePicture();
        //initialize forum posts
        initializePosts();
    }

    //function will query API for all forum posts and populate the list
    public void initializePosts(){
        //make url
        String url = App.baseURL + "posts";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //make arrays to hold corresponding pairs of post titles and authors
//                String[] titles = new String[response.length()];
//                String[] authors = new String[response.length()];
                //String[] ids = new String[response.length()];
                //iterate through each item in the array
                for(int i = 0; i < response.length(); i++) {
                    try {
                        String id = response.getJSONObject(i).getString("_id");
                        String title = response.getJSONObject(i).getString("title");
                        String author = response.getJSONObject(i).getString("author");
                        String content = response.getJSONObject(i).getString("content");
                        String time = response.getJSONObject(i).getString("time");

                        String timeClean = "created at: " +
                                new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.UK)
                                        .format(new Date(Long.parseLong(time)));

                        ForumPost post = new ForumPost(id, "author: " + author, title, timeClean, content, time);
                        forumPostList.add(post);

                        //titles[i] = response.getJSONObject(i).getString("title");
                        //authors[i] = response.getJSONObject(i).getJSONObject("author").getString("username");
                        //authors[i] = response.getJSONObject(i).getString("author");
                        //posts.add(response.getJSONObject(i).getString("_id"));

                        //get references to vertical linear layouts
                        //ListView titleCol = findViewById(R.id.home_title_column);
                        //ListView authorCol = findViewById(R.id.home_author_column);
                        //now take the arrays and map them to our vertical linear layouts
                        //ArrayAdapter<String> titleAdapter = new ArrayAdapter<>(Home.this, R.layout.forum_post_list, titles);
                        //ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(Home.this, R.layout.forum_post_list, authors);
                        //connect adapters
                        //titleCol.setAdapter(titleAdapter);
                        //authorCol.setAdapter(authorAdapter);
                    } catch(JSONException e) {
                        Log.e("Error", "Caught JSON exception: " + e);
                    }
                }
                forumPostAdapter.setPostList(forumPostList);
                recyclerView.setAdapter(forumPostAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Volley error getting posts: " + error);
            }
        });
        //make request
        queue.add(request);
    }

    //function that will prompt the user to upload a picture
    public void promptUpload(View view){
        //show prompt window
        uploadPrompt.setVisibility(View.VISIBLE);
    }

    //function that will dismiss profile pic prompt
    public void cancelUpload(View view){
        //hide prompt window
        uploadPrompt.setVisibility(View.INVISIBLE);
    }

    //function that will log the user out
    public void logout(View view){
        //create intent and start main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //function that will take the user to the forum post they tapped on
//    public void viewPost(View view){
//        Log.d("Debug", "Post Clicked");
//        TextView myview = (TextView) view;
//        String postTitle = myview.getText().toString();
//        //create an intent with post title attached and username
//        Intent intent = new Intent(this, ViewPost.class);
//        intent.putExtra("username", username);
//        intent.putExtra("title", postTitle);
//        //call activity
//        startActivity(intent);
//    }

    //function that will create a forum post when button is clicked
    public void startCreatePost(View view){
        //create an intent with the username of the user stored
        Intent intent = new Intent(this, CreatePost.class);
        intent.putExtra("username", username);
        //call activity
        startActivity(intent);
    }

    //function that gets called when user clicks Upload button
    //after clicking on profile picture
    public void uploadPicture(View view){
        //hide prompt window
        uploadPrompt.setVisibility(View.INVISIBLE);

        //create and call the intent to take a picture
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
          startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public int getForumPostListSize() {
        return forumPostList.size();
    }

    //function that will show a preview of the new profile picture to the user
//    public void uploadProfilePic(Bitmap bm){
//        //get a byte output stream to send the image info through
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        //compress the bitmap into the output stream
//        bm.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        //get byte array from output stream
//        byte[] imageBytes = outputStream.toByteArray();
//        //encode the image as a base64 string
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        Log.d("Debug", "Encoded Data: " + encodedImage);
//        //make url to send request to
//        String url = entryPointURL + "media/" + username;
//        Log.d("Debug", "Url: " + url);
//        //now create a JSON object to send
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("imageData", encodedImage);
//        } catch(JSONException e) {
//            Log.e("Error", "Error storing encoded image data in JSON object: " + e);
//        }
//        //create a JSON object request
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                //get response status
//                try {
//                    int responseStatus = response.getInt("status");
//                    //check status of response
//                    if(responseStatus == 200) {
//                        //make success toast
//                        Toast success = Toast.makeText(Home.this, R.string.profile_pic_success, Toast.LENGTH_SHORT);
//                        success.setGravity(Gravity.BOTTOM, 0, 50);
//                        success.show();
//                    } else {
//                        Log.e("Error", "Upload was not successful.");
//                    }
//                } catch(JSONException e) {
//                    Log.e("Error", "Error getting OK from server after uploading picture: " + e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", "Volley error uploading new profile picture: " + error);
//            }
//        });
//        //submit the request by adding to queue
//        queue.add(request);
//    }

    //function that will be called once the camera finishes taking a picture
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //check to see that the result is from using the camera
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            //do stuff with result
//            Bundle extras = data.getExtras();
//            //grab bitmap from result
//            Bitmap newProfilePicture = (Bitmap) extras.get("data");
//            //set as new picture
//            //profilePicture.setImageBitmap(Bitmap.createScaledBitmap(newProfilePicture, 100, 100, false));
//            //send the image to the server to be saved
//            //uploadProfilePic(newProfilePicture);
//        }
//    }

    //function that will grab all the user's data from the database and populate the layout
//    public void initializeProfilePicture(){
//        //create an object request to get the user's information
//        JsonObjectRequest initRequest = new JsonObjectRequest(Request.Method.GET, initURL + username, new JSONObject(),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            //grab the URL of the image we are going to retrieve
//                            String imgURL = response.getString("profilePic");
//                            //now create an Image request to send to the URL
//                            ImageRequest imageRequest = new ImageRequest(imgURL, new Response.Listener<Bitmap>() {
//                                @Override
//                                public void onResponse(Bitmap response) {
//                                    //set the image
//                                    //profilePicture.setImageBitmap(response);
//                                }
//                            }, 100, 100, null, new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    Log.e("Error", "Volley error sending Image Request: " + error);
//                                }
//                            });
//                            //submit request by adding to queue
//                            queue.add(imageRequest);
//                        } catch(JSONException e) {
//                            Log.e("Error", "Error getting user information in initialization: " + e);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", "Volley error on initial request: " + error);
//            }
//        });
//
//        //submit request by adding to queue
//        queue.add(initRequest);
//    }

    @Override
    public void onClick(int position) {
        //Toast.makeText(this, "Clicked item index: " + position, Toast.LENGTH_SHORT).show();
        ForumPost post = forumPostList.get(position);
        Intent intent = new Intent(this, ViewPost.class);

        Bundle bundle = new Bundle(8);
        bundle.putString("username", username);
        bundle.putString("title", post.getTitle());
        bundle.putString("author", post.getAuthor());
        bundle.putString("id", post.getId());
        bundle.putString("time", post.getCreatedAt());
        bundle.putString("content", post.getContent());
        bundle.putString("lastEditTime", post.getLastModified());
        bundle.putString("timeRaw", post.getTimeRaw());

        intent.putExtras(bundle);
        Log.d("Before view: ", bundle.toString());
        startActivity(intent);
    }
}