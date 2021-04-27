//package com.mysport.mysport_mobile.forum;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.mysport.mysport_mobile.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    //function that will log the user in
//    public void submitLogin(View view){
//        //get references to text inputs
//        TextView usernameInput = findViewById(R.id.username_input);
//        TextView passwordInput = findViewById(R.id.password_input);
//
//        //grab credentials from text inputs
//        String username = usernameInput.getText().toString();
//        String password = passwordInput.getText().toString();
//        Log.d("Debug", "U: " + username + "; P: " + password);
//
//        //URL of login request
//        final String loginURL = "http://www.tonyanziano.com:8080/login";
//
//        //make Volley queue
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        //construct JSON object
//        final JSONObject jsonBody = new JSONObject();
//        try{
//            jsonBody.put("username", username);
//            jsonBody.put("password", password);
//            Log.d("Debug", "Sending JSON: " + jsonBody.toString());
//        } catch (JSONException e) {
//            Log.e("Error", "JSON Exception: " + e);
//        }
//
//        //construct JSON request
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginURL, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    //int to hold login response status
//                    int responseStatus = response.getInt("status");
//                    Log.d("Debug", "Status: " + responseStatus);
//
//                    //check status
//                    if(responseStatus == 1){
//                        //log in to application
//                        Log.d("Debug", "Received success");
//                        //create an intent with the username of our user saved
//                        //so that we can make data user-specific
//                        Intent loginIntent = new Intent(MainActivity.this, Home.class);
//                        String ourUser = jsonBody.getString("example-email@mysport-community.com");
//                        loginIntent.putExtra("username", ourUser);
//                        //call home activity with intent
//                        startActivity(loginIntent);
//                    } else {
//                        //create and show error toast
//                        Toast failureToast = Toast.makeText(MainActivity.this, R.string.login_failure, Toast.LENGTH_LONG);
//                        failureToast.setGravity(Gravity.BOTTOM, 0, 50);
//                        failureToast.show();
//                    }
//
//                } catch (JSONException e) {
//                    Log.e("Error", "Error getting status from JSON response: " + e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", "Volley error sending login credentials: " + error);
//            }
//        });
//
//        //send JSON request by adding to queue
//        queue.add(jsonObjectRequest);
//    }
//
//    //function that loads the registration page
//    public void loadRegisterPage(View view){
//        //create an intent that will be delivered by the registration page
//        Intent intent = new Intent(this, Register.class);
//        //start the new activity with the intent
//        startActivity(intent);
//    }
//}
