//Shenyi Yu Phase 1
package edu.lehigh.cse216.yut222.phase0;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class DetailActivity extends AppCompatActivity {
    ArrayList<Message> mData = new ArrayList<>();
    //clickCount is for later use in like button on click. SY
    int clickCount = 0;
    int deleteFlag = 0;
    Button comment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final int mId = intent.getIntExtra("message id", 404);
        final String url = "https://arcane-refuge-67249.herokuapp.com/messages/" + mId;
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final Message m = new Message(0,0,0,0, "", "", "", "");
        /*int uId = intent.getIntExtra("user id", 404);
        String mTitle = intent.getStringExtra("message title");
        final int mLikes = intent.getIntExtra("message likes", 404);
        final int mDislikes = intent.getIntExtra("message dislikes", 404);*/

        Map<String, String> mapdetail = new HashMap<>();
        mapdetail.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        mapdetail.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        Log.e("email", sharedpreferences.getString("prefEmail", "default"));
        Log.e("key", sharedpreferences.getString("prefKey", "default"));
        JSONObject d = new JSONObject(mapdetail);
        JsonObjectRequest listR = new JsonObjectRequest(Request.Method.POST, url, d,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        populateDetailFromVolley(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zex220", "Listing all Details didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(listR);




        //ArrayList<Comment> mComments = (ArrayList<Comment>)intent.getSerializableExtra("message comments");
        /*Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Comment> mComments = (ArrayList<Comment>) args.getSerializable("ARRAYLIST"); //Casting?
        */


        final String urlLikes =  url + "/likes";
        Map<String, String> map = new HashMap<>();
        map.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        map.put("uId", sharedpreferences.getString("prefId","default"));
        JSONObject c = new JSONObject(map);

        //final Message m = new Message(mId, uId, mTitle, mLikes, mDislikes);
        //mData.add(new Message(mId, uId, mTitle, mLikes, mDislikes));
        //show detail message SY
        //showComment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Edit function not yet finished, can refer to like function, second activity
        //using jsonObjectRequest and put method
        //getting user's inout from editText and put into Intent
        // SY


        // The Delete button returns to the caller without sending any data SY
        Button bDelete = (Button) findViewById(R.id.drop);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call delete request function
                //deleteMessage(urlDetail);
                deleteFlag = 1;
                //refresh
                //showDetail();
                //showComment();
            }
        });
        Button comment = (Button)findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, CommentActivity.class);
                i.putExtra("message id", mId);
                startActivity(i);
            }
            });


        // The Like button returns updated number of likes to the caller
        //can be developed into image button with "liked" effect
        // using the two pngs I provide in res/drawable SY
        Button bLike = findViewById(R.id.like);
        bLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call like request function
                clickCount++;
                Log.e("clickcount",Integer.toString(clickCount));
                if((clickCount % 2) == 1) {
                    postLike(urlLikes);
                    Toast toast = Toast.makeText(DetailActivity.this, "liked", Toast.LENGTH_LONG);
                    toast.show();
                }
                else if((clickCount % 2) == 0){
                    postLike(urlLikes);
                    Toast toast = Toast.makeText(DetailActivity.this, "canceled like", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        // The Cancel button returns to the caller without sending any data
        Button bCancel = (Button) findViewById(R.id.detailCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        // The ok button returns to the caller without sending any data
        Button bOK = (Button) findViewById(R.id.detailOk);
        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //postLike(urlDetail);
                deleteMessage(url, deleteFlag);
                //setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void showDetail(){
        Log.e("showDetail", "im in show detail now");
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_detail_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DetailListAdapter adapter = new DetailListAdapter(this, mData);
        rv.setAdapter(adapter);
    }

    private void deleteMessage(String urlD, int deleteFlag) {
        //post msg id = urlDelete if deleteFlag is marked
        if (deleteFlag == 1) {
            StringRequest deleteR = new StringRequest(Request.Method.DELETE, urlD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("shy221", "Deleting message didn't work.");
                }
            });
            MySingleton.getInstance(this).addToRequestQueue(deleteR);
        }
    }

    private void postLike(String urlL){
        Log.e("postlike", "map");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //one link to update likes
        Map<String, Object> map = new HashMap<>();
        int uid = Integer.parseInt(sharedpreferences.getString("prefId","default"));
        map.put("uid", uid);
        map.put("sessionKey", sharedpreferences.getString("prefKey","default"));
        map.put("uEmail", sharedpreferences.getString("prefEmail","default"));
        //Toast toast = Toast.makeText(DetailActivity.this, sharedpreferences.getString("prefKey","default"), Toast.LENGTH_LONG);
        //toast.show();

        JSONObject m = new JSONObject(map);
        JsonObjectRequest likeR = new JsonObjectRequest(Request.Method.PUT,
                urlL, m, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                if(response.getString("mStatus").equals("ok")) {
                    Log.e("message", "Like the message here!");
                }else{
                    Log.e("like", "error status");
                }}catch (final JSONException e){
                    Log.e("x","exception");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "post likes went wrong.");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(likeR);
        showDetail();
        finish();
    }
    private void deleteLike(String urlL){

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //one link to deletelikes
        Map<String, Object> map = new HashMap<>();
        int uid = Integer.parseInt(sharedpreferences.getString("prefId","default"));
        map.put("uid", uid);
        map.put("sessionKey", sharedpreferences.getString("prefKey","default"));
        map.put("uEmail", sharedpreferences.getString("prefEmail","default"));

        JSONObject m = new JSONObject(map);
        JsonObjectRequest likeR = new JsonObjectRequest(Request.Method.DELETE,
                urlL, m, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("message", "UNLike the message here!");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "delete likes went wrong.");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(likeR);
    }

    private void populateDetailFromVolley(JSONObject response) {
        try {
            mData.clear();
            String status;
            status = response.getString("mStatus");
            //this is to check if status went wrong
            // Log.e("shy221", status);
            if (status.equals("ok")) {
                JSONObject data = response.getJSONObject("mData");
                int mId = data.getInt("mId");
                int uId = data.getInt("uId");
                String title = data.getString("mTitle");
                String content = data.getString("mContent");
                int likes = data.getInt("mLikes");
                int dislikes = data.getInt("mDislikes");
                String username = data.getString("cUsername");
                String time = data.getString("mCreated");
                mData.add(new Message(mId, uId, likes, dislikes, title, content, username, time));
                Log.e("populate detail", "got details");
            } else {
                Log.d("get detail", "mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("get detail", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("/messages/mid", "Successfully parsed JSON file.");
        showDetail();
    }


}
