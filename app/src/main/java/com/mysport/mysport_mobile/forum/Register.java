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
//import org.w3c.dom.Text;
//
//import java.util.concurrent.TimeUnit;
//
//import javax.xml.datatype.Duration;
//
//public class Register extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//    }
//
//    //function that will register the user (create a new account)
//    public void submitRegister(View view){
//        //get references to text inputs
//        TextView usernameInput = findViewById(R.id.username_input_register);
//        TextView passwordInput = findViewById(R.id.password_input_register);
//
//        //grab credentials from text inputs
//        String username = usernameInput.getText().toString();
//        String password = passwordInput.getText().toString();
//        Log.d("Debug", "U: " + username + "; P: " + password);
//
//        //URL of register request
//        final String registerURL = "http://www.tonyanziano.com:8080/register";
//
//        //make Volley queue
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        //construct JSON object
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("username", username);
//            jsonBody.put("password", password);
//            Log.d("Debug", "Sending JSON: " + jsonBody.toString());
//        } catch (JSONException e) {
//            Log.e("Error", "JSON Exception: " + e);
//        }
//
//        //construct JSON request
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, registerURL, jsonBody, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    //int to hold registration response status
//                    int responseStatus = response.getInt("status");
//                    Log.d("Debug", "Status Received: " + responseStatus);
//
//                    //check status
//                    if(responseStatus == 1){
//                        //show registration successful toast
//                        Toast success = Toast.makeText(Register.this, R.string.register_success, Toast.LENGTH_LONG);
//                        success.setGravity(Gravity.BOTTOM, 0, 50);
//                        success.show();
//                        //create a thread that waits for as long as the toast
//                        Thread thread = new Thread(() -> {
//                            try{
//                                Thread.sleep(3500);
//                                //then redirect to login page
//                                Intent intent = new Intent(Register.this, MainActivity.class);
//                                startActivity(intent);
//                            } catch (InterruptedException e) {
//                                Log.e("Error", "Error sleeping inside delay thread: " + e);
//                            }
//                        });
//                        //start the thread
//                        thread.start();
//                    } else {
//                        //show registration failure toast
//                        Toast failure = Toast.makeText(Register.this, R.string.register_failure, Toast.LENGTH_LONG);
//                        failure.setGravity(Gravity.BOTTOM, 0, 50);
//                        failure.show();
//                    }
//                } catch (JSONException e) {
//                    Log.e("Error", "Error getting status from JSON response: " + e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", "Volley error registering user: " + error);
//            }
//        });
//
//        //submit request by adding to queue
//        queue.add(jsonObjectRequest);
//    }
//}
