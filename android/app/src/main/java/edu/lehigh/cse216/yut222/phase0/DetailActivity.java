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
    ArrayList<Comment> mComments = new ArrayList<>();
    //clickCount is for later use in like button on click. SY
    int clickCount = 0;
    int deleteFlag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        int mId = intent.getIntExtra("message id", 404);
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
        //showDetail();



        //ArrayList<Comment> mComments = (ArrayList<Comment>)intent.getSerializableExtra("message comments");
        /*Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Comment> mComments = (ArrayList<Comment>) args.getSerializable("ARRAYLIST"); //Casting?
        */

        final String urlComments =  "https://arcane-refuge-67249.herokuapp.com/" + mId + "/listcomments";
        final String urlLikes =  url + mId + "/likes";
        Map<String, String> map = new HashMap<>();
        map.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        JSONObject c = new JSONObject(map);

        //get comments from route
        //routes incomplete
        JsonObjectRequest listC = new JsonObjectRequest(Request.Method.POST, urlComments, c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        populateCommentFromVolley(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zex220", "Listing all comments didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(listC);
        Log.e("zex220","outside request");

        //final Message m = new Message(mId, uId, mTitle, mLikes, mDislikes);
        //mData.add(new Message(mId, uId, mTitle, mLikes, mDislikes));
        //show detail message SY
        //showComment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*Map<String, String> map = new HashMap<>();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        map.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        JSONObject c = new JSONObject(map);

        //get comments from route
        JsonObjectRequest listR = new JsonObjectRequest(Request.Method.POST, urlComments, c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        populateCommentFromVolley(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zex220", "Listing all Comments didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(listR);
        Log.e("zex220","outside request");*/


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
                showDetail();
                //showComment();
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
                    deleteLike(urlLikes);
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
    /*private void showComment(){
        Log.e("showComment","got in");
        RecyclerView rv = findViewById(R.id.comment_list_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CommentListAdapter adapter = new CommentListAdapter(this, mComments);
        rv.setAdapter(adapter);
    }*/

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
        //map.put("uid", Integer.parseInt(sharedpreferences.getString("uId","default")));
        int uid = Integer.parseInt(sharedpreferences.getString("prefId","default"));
        map.put("uid", uid);
        map.put("sessionKey", sharedpreferences.getString("prefKey","default"));
        map.put("uEmail", sharedpreferences.getString("prefEmail","default"));
        Toast toast = Toast.makeText(DetailActivity.this, sharedpreferences.getString("prefKey","default"), Toast.LENGTH_LONG);
        toast.show();

        JSONObject m = new JSONObject(map);
        Log.e("postlike", "hash successfully");
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
    }
    private void deleteLike(String urlL){

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //one link to update likes
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
                Log.e("shy221", "post likes went wrong.");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(likeR);
    }

    private void populateCommentFromVolley(JSONObject response) {
        try {
            Log.e("comments", "try");
            mComments.clear();
            String status = response.getString("mStatus");
            if (status.equals("ok")) {
                JSONArray data = response.getJSONArray("mData");
                for (int i = 0; i < data.length(); i++) {
                    Log.e("comments", "enter for loop");
                    int mId = data.getJSONObject(i).getInt("mId");
                    int cId = data.getJSONObject(i).getInt("cId");
                    int uId = data.getJSONObject(i).getInt("uId");
                    String text = data.getJSONObject(i).getString("cText");
                    String username = data.getJSONObject(i).getString("cUsername");
                    mComments.add(new Comment(cId, uId, mId, text, username));
                    Log.e("comment", "exiting for loop");
                }
                Log.e("outside for loop", mComments.toString());
                //Log.e("outside for loop", mComments.get(0).cUsername);

            } else {
                Log.e("get comment", "mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("get comment", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("comments", "Successfully parsed JSON file.");
        //showComment();
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
        /*
        RecyclerView rv = findViewById(R.id.comment_list_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CommentListAdapter adapter = new CommentListAdapter(this, mComments);
        rv.setAdapter(adapter);*/

        /*adapter.setClickListener(new CommentListAdapter.ClickListener() {
            @Override
            public void onClick(Comment comment) {
                //Toast for fun effect SY
                //Toast.makeText(MainActivity.this, message.mTitle , Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("label_contents", "Post your message here.");
                i.putExtra("message id", comment.mId);
                i.putExtra("message title", comment.uId);
                i.putExtra("message content", comment.cId);
                i.putExtra("message likes", comment.cText);


                startActivityForResult(i, 123);
                //change this function
            }
        });*/


//might need refresh function depending on your design EY

}
