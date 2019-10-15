//Shenyi Yu Phase 1
package edu.lehigh.cse216.yut222.phase0;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

//import shy221.cse216.lehigh.edu.phase0.R;

public class MainActivity extends AppCompatActivity {
    /**
     * mData holds the data we get from Volley
     */
    ArrayList<Message> mData = new ArrayList<>();
    /*public static final String MyPREFERENCES = "MyPrefs";
    public static final String PrefId = "prefId";
    public static final String PrefName = "prefName";
    public static final String PrefKey = "prefKey";
    public static final String PrefEmail = "prefEmail";
    public static final String PrefSalt = "prefSalt";
    public static final String PrefIntro = "prefIntro";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Context context = MainActivity.this;
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //String PrefKey = sharedpreferences.getString("prefKey", "default");
        String PrefKey = sharedpreferences.getString("prefKey", "default");
        Log.e("main page session key", PrefKey);
        Toast toast = Toast.makeText(context, PrefKey, Toast.LENGTH_LONG);
        toast.show();

        //SY
        // Instantiate the RequestQueue.
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //one to show all message
        String urlList = "https://arcane-refuge-67249.herokuapp.com/messages";
        //only functional, nothing to display,
        //SEE DETAIL ACTIVITY
        //one link to update message content
        //one link to delete message
        //one link to like message


        //SY
        //POST request method
        //take the message id to include information
        //put the message in the database
        //method to get input from input box

        //RESPONSE request method
        //get json file, obj array, show all with all msg id, msg title = urlList
        //get json file, only one obj with msg id, msg title and msg content and # of likes = urlShow
        //post msg-id in order to get show all, but don't display msg-id






        //SY
        //post msg id and get message  = urlList
        StringRequest listR = new StringRequest(Request.Method.GET, urlList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        populateListFromVolley(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "Listing all messages didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(listR);

    }

    //SY
    //check mStatus first, mStatus == "ok" or "error", then fetch mData
    //{"mStatus":"ok",
    // "mData":
    // {"mId":3,"mTitle":"Movie Time","mContent":"","mLikes":0,"mCreated":"Sep 21, 2019 8:26:37 PM"}
    // }


    //modified method to list all messages
    private void populateListFromVolley(String response){
        try {
            mData.clear();
            JSONObject obj = new JSONObject(response);
            String status;
            status = obj.getString("mStatus");
            //this is to check if status went wrong
            // Log.e("shy221", status);
            if(status.equals("ok")){
                JSONArray data = obj.getJSONArray("mData");
                for (int i = 0; i < data.length(); i++){
                    int mid = data.getJSONObject(i).getInt("mId");
                    int uid = data.getJSONObject(i).getInt("uId");
                    String title = data.getJSONObject(i).getString("mTitle");
                    //String content = data.getJSONObject(i).getString("message");
                    int likes = data.getJSONObject(i).getInt("mLikes");
                    int dislikes = data.getJSONObject(i).getInt("mDislikes");
                    //int likes = 0;
                    //ArrayList<Comment> comments = new ArrayList<>();
                    //JSONArray comments = data.getJSONObject(i).getJSONArray("mComments");
                    //ArrayList<String> comments = data.getJSONObject(i).get("mComments");  //unsafe casting
                    mData.add(new Message(mid, uid, likes, dislikes, title, "unknown", "unknown", "unknown"));
                }
            }else{
                Log.d("shy221","mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("shy221", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("shy221", "Successfully parsed JSON file.");

        RecyclerView rv = findViewById(R.id.message_list_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ItemListAdapter adapter = new ItemListAdapter(this, mData);
        rv.setAdapter(adapter);

        adapter.setClickListener(new ItemListAdapter.ClickListener() {
            @Override
            public void onClick(Message message) {
                //Toast for fun effect SY
                //Toast.makeText(MainActivity.this, message.mTitle , Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("label_contents", "Post your message here.");
                i.putExtra("message id", message.mId);
                i.putExtra("user id", message.uId);
                i.putExtra("message title", message.mTitle);
                i.putExtra("message dislikes", message.mDislikes);
                i.putExtra("message likes", message.mLikes);
                //i.putExtra("message comments", message.mComments);

                /*Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", message.mComments);*/
                //i.putExtra("message comments",args);
                //get mComments as an arraylist using bundle

                startActivityForResult(i, 123);
                //change this function
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
            i.putExtra("label_contents", "Post your message here.");
            startActivityForResult(i, 789); // 789 is the number that will come back to us
            return true;
        }
        if (id == R.id.action_profile) {
            Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
            i.putExtra("label_contents", "Post your message here.");
            startActivityForResult(i, 2);
            return true;
        }
        if (id == R.id.action_login) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.putExtra("label_contents", "Login here");
            startActivityForResult(i, 3);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 789) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the "extra" string of data
//                Log.e("shy221 result", data.getStringExtra("result"));
//                Toast.makeText(MainActivity.this, data.getStringExtra("title") + data.getStringExtra("content"), Toast.LENGTH_LONG).show();

                //refresh
                StringRequest listR = new StringRequest(Request.Method.GET,"https://arcane-refuge-67249.herokuapp.com/messages" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                populateListFromVolley(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("shy221", "Listing all messages didn't work!");
                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(listR);
            }
        }
        if (requestCode == 3) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the "extra" string of data
//                Log.e("shy221 result", data.getStringExtra("result"));
//                Toast.makeText(MainActivity.this, data.getStringExtra("title") + data.getStringExtra("content"), Toast.LENGTH_LONG).show();

                //refresh
                StringRequest listR = new StringRequest(Request.Method.GET,"https://arcane-refuge-67249.herokuapp.com/messages" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                populateListFromVolley(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("shy221", "Listing all messages didn't work!");
                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(listR);
            }
        }
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the "extra" string of data
//                Log.e("shy221 result", data.getStringExtra("result"));
//                Toast.makeText(MainActivity.this, data.getStringExtra("title") + data.getStringExtra("content"), Toast.LENGTH_LONG).show();

                //refresh
                StringRequest listR = new StringRequest(Request.Method.GET,"https://arcane-refuge-67249.herokuapp.com/messages" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                populateListFromVolley(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("shy221", "Listing all messages didn't work!");
                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(listR);

            }
        }
        if (requestCode == 123) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //refresh when detail activity is working fine
                StringRequest listR = new StringRequest(Request.Method.GET,"https://arcane-refuge-67249.herokuapp.com/messages" ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                populateListFromVolley(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("shy221", "Listing all messages didn't work!");
                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(listR);
            }
        }
    }


}