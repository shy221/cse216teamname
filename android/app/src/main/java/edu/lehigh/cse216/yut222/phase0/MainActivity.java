package edu.lehigh.cse216.yut222.phase0;


import android.app.Activity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import shy221.cse216.lehigh.edu.phase0.R;

public class MainActivity extends AppCompatActivity {
    /**
     * mData holds the data we get from Volley
     */
    ArrayList<Message> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        Log.d("shy221", "Debug Message from onCreate");

        // Instantiate the RequestQueue.
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        String url = "https://arcane-refuge-67249.herokuapp.com/messages/";

        //one to show a single detailed message with likes
        String urlShow = "https://arcane-refuge-67249.herokuapp.com/messages/2";
        //one to show all message
        String urlList = "https://arcane-refuge-67249.herokuapp.com/messages";
        //only functional, nothing to display,
        //one link to update message content
        String urlUpdate = "https://arcane-refuge-67249.herokuapp.com/messages/:id";
        //one link to delete message
        String urlDelete = "https://arcane-refuge-67249.herokuapp.com/messages/:id";


        //POST request method

        //take the message id to include the buttom information
        //put the message in the database
        //method to get input from input box


//        //Button update
////        Button bUpdate = (Button) findViewById(R.id.update);
////        bUpdate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                Intent i = new Intent();
//////                    i.putExtra("title", etTitle.getText().toString());
//////                    i.putExtra("content", etContent.getText().toString());
//////                    setResult(Activity.RESULT_OK, i);
//////                    String title = etTitle.getText().toString();
//////                    String content = etContent.getText().toString();
//////                    postMessage(title, content);
////
////                finish();
////
////            }
////
////        });

//        //button Drop
//        Button bDelete = (Button) findViewById(R.id.drop);
//        bDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent();
////
////
////                if (!etTitle.getText().toString().equals("")&& !etContent.getText().toString().equals("") ) {
////                    i.putExtra("title", etTitle.getText().toString());
////                    i.putExtra("content", etContent.getText().toString());
////                    setResult(Activity.RESULT_OK, i);
////                    String title = etTitle.getText().toString();
////                    String content = etContent.getText().toString();
////                    postMessage(title, content);
////
////                }
//
//            }
//
//        });
//
//        //button like
//        Button bLike = (Button) findViewById(R.id.like);
//        bLike.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent();
////
////
////                if (!etTitle.getText().toString().equals("")&& !etContent.getText().toString().equals("") ) {
////                    i.putExtra("title", etTitle.getText().toString());
////                    i.putExtra("content", etContent.getText().toString());
////                    setResult(Activity.RESULT_OK, i);
////                    String title = etTitle.getText().toString();
////                    String content = etContent.getText().toString();
////                    postMessage(title, content);
////
////                }
//
//            }
//
//        });





        //RESPONSE request method

        //get json file, obj array, show all with all msg id, msg title = urlList
        //get json file, only one obj with msg id, msg title and msg content and # of likes = urlShow
        //post msg-id in order to get show all, but don't display msg-id

        //post msg id and get message  = urlShow
        //show a single detailed message with likes, but don't display msg-id

        StringRequest showR = new StringRequest(Request.Method.GET, urlShow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO: modify to get message title and content and likes

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "Showing details of the message didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(showR);




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





    //check mStatus first, mStatus == "ok" or "error", then fetch mData
    //{"mStatus":"ok",
    // "mData":
    // {"mId":3,"mTitle":"Movie Time","mContent":"","mLikes":0,"mCreated":"Sep 21, 2019 8:26:37 PM"}
    // }


    private void populateListFromVolley(String response){
        try {
            mData.clear();
            JSONObject obj = new JSONObject(response);
            String status;
            status = obj.getString("mStatus");
//            Log.e("shy221", status);
                if(status.equals("ok")){
                    JSONArray data = obj.getJSONArray("mData");
                    for (int i = 0; i < data.length(); i++){
                        int id = data.getJSONObject(i).getInt("mId");
                        String title = data.getJSONObject(i).getString("mTitle");
//                        String content = data.getJSONObject(i).getString("mContent");
//                        int likes = data.getJSONObject(i).getInt("mLikes");
                        String content = "";
                        mData.add(new Message(id, title, content));
                        Log.d("hi", mData.toString());
                    }

                }else{
                    Log.d("shy221","mStatus is not ok.");
                }
        } catch (final JSONException e) {
            Log.d("shy221", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("shy221", "Successfully parsed JSON file.");



//        ListView mListView = (ListView) findViewById(R.id.datum_list_view);
//        ItemListAdapter adapter = new ItemListAdapter(this, mData);
//        mListView.setAdapter(adapter);
        RecyclerView rv = findViewById(R.id.message_list_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ItemListAdapter adapter = new ItemListAdapter(this, mData);
        rv.setAdapter(adapter);

        adapter.setClickListener(new ItemListAdapter.ClickListener() {
            @Override
            public void onClick(Message message) {
                Toast.makeText(MainActivity.this, message.mTitle , Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("label_contents", "Post your message here.");
                i.putExtra("message id", message.mId);
                i.putExtra("message title", message.mTitle);
                i.putExtra("message content", message.mContent);
                startActivityForResult(i, 123); // 123 is the number that will come back to us


            }
        });
    }

    private int getMessageId(int position) {
        return mData.get(position).mId;
    }



    private void postLike(final String likes, final int id){
        //one link to update likes
        String urlLike = "https://arcane-refuge-67249.herokuapp.com/messages/" + id +"/likes";
        Map<String, String> map = new HashMap<>();
        map.put("mLikes", likes);
        JSONObject m = new JSONObject(map);
        JsonObjectRequest likeR = new JsonObjectRequest(Request.Method.POST,
                urlLike, m, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("message", "Like the message here!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "post likes went wrong.");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(likeR);
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
                // Get the "extra" string of data
//                Log.e("shy221 result", data.getStringExtra("result"));
//                Toast.makeText(MainActivity.this, data.getStringExtra("title") + data.getStringExtra("content"), Toast.LENGTH_LONG).show();


            }
        }
    }


}
