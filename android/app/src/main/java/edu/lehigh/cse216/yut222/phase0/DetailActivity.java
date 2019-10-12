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
        int uId = intent.getIntExtra("user id", 404);
        String mTitle = intent.getStringExtra("message title");
        final int mLikes = intent.getIntExtra("message likes", 404);
        final int mDislikes = intent.getIntExtra("message dislikes", 404);

        //ArrayList<Comment> mComments = (ArrayList<Comment>)intent.getSerializableExtra("message comments");
        /*Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Comment> mComments = (ArrayList<Comment>) args.getSerializable("ARRAYLIST"); //Casting?
        */

        String url = "https://arcane-refuge-67249.herokuapp.com/messages/";
        //urlDetail leads to route of a specific message
        final String urlDetail = url + mId;
        final String urlComments =  "https://arcane-refuge-67249.herokuapp.com/" + mId + "/listcomments";
        final String urlLikes =  "https://arcane-refuge-67249.herokuapp.com/" + mId + "/likes";
        Map<String, String> map = new HashMap<>();
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
        Log.e("zex220","outside request");

        //final Message m = new Message(mId, uId, mTitle, mLikes, mDislikes);
        mData.add(new Message(mId, uId, mTitle, mLikes, mDislikes));
        //show detail message SY
        showDetail();
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
        /*Button bLike = findViewById(R.id.like);
        bLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest listR = new StringRequest(Request.Method.PUT, urlList,
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

                //call like request function
                /*clickCount++;
                if((clickCount % 2) == 1) {
                    mData.get(0).mLikes++;
                }
                else if((clickCount % 2) == 0){
                    mData.get(0).mLikes--;
                }*/


                //Log.d("shy221 clickCount", "" + clickCount);
                //postLike(urlDetail, clickCount);
                /*showDetail();
            }
        });
*/

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
                postLike(urlDetail, clickCount);
                deleteMessage(urlDetail, deleteFlag);
                //setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });


    }

    private void showDetail(){
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

    private void deleteMessage(String urlD, int deleteFlag){
        //post msg id = urlDelete if deleteFlag is marked
        if(deleteFlag == 1) {
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

    private void postLike(String urlD, int likes){
        //one link to update likes
        Map<String, Integer> map = new HashMap<>();
        map.put("mLikes", likes);
        JSONObject m = new JSONObject(map);
        JsonObjectRequest likeR = new JsonObjectRequest(Request.Method.PUT,
                urlD+"/likes", m, new Response.Listener<JSONObject>() {
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


    private void populateCommentFromVolley(JSONObject response){
        try {
            mComments.clear();
            String status;
            status = response.getString("mStatus");
            //this is to check if status went wrong
            // Log.e("shy221", status);
            if(status.equals("ok")){
                JSONArray data = response.getJSONArray("mData");
                for (int i = 0; i < data.length(); i++){
                    int mId = data.getJSONObject(i).getInt("mId");
                    int cId = data.getJSONObject(i).getInt("cId");
                    int uId = data.getJSONObject(i).getInt("uId");
                    String text = data.getJSONObject(i).getString("cText");
                    String username = data.getJSONObject(i).getString("cUsername");
                    mComments.add(new Comment(cId, uId, mId, text, username));
                    Log.e("populate comments", "got comments");
                }
            }else{
                Log.d("get comment","mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("get comment", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("zex220", "Successfully parsed JSON file.");
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
    }

//might need refresh function depending on your design EY

}
