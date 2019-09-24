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

public class DetailActivity extends AppCompatActivity {
    ArrayList<Message> mData = new ArrayList<>();
    int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent =getIntent();
        int mId = intent.getIntExtra("message id", 000);
        String mTitle = intent.getStringExtra("message title");
        String mContent = intent.getStringExtra("message content");
        String url = "https://arcane-refuge-67249.herokuapp.com/messages/";
        final String urlDetail = url + mId;
        final int mLikes = intent.getIntExtra("message likes", 0000);
        final Message m = new Message(mId, mTitle, mContent,mLikes );
//        Message m = new Message(mId, mTitle, mContent);
        mData.add(new Message(mId, mTitle, mContent, mLikes));
//        mData.add(new Message(mId, mTitle, mContent));
        showDetail();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





//        //post msg id and new msg content = urlUpdate
//        StringRequest updateR = new StringRequest(Request.Method.PUT, urlDetail,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        //TODO: modify to post msg id, and new msg content
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("shy221", "Updating message content didn't work.");
//
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(updateR);


        // The Delete button returns to the caller without sending any data
        Button bDelete = (Button) findViewById(R.id.drop);
        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call delete request function
                deleteMessage(urlDetail);
                finish();
            }
        });


        // The Like button returns to the caller
        Button bLike = findViewById(R.id.like);
        bLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call like request function
                clickCount++;
                mData.get(0).mLikes++;
                Log.d("shy221 clickCount", ""+clickCount);
                postLike(urlDetail, clickCount);
                showDetail();
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


    }

    private void showDetail(){
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_detail_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DetailListAdapter adapter = new DetailListAdapter(this, mData);
        rv.setAdapter(adapter);


    }

    private void deleteMessage(String urlD){
        //post msg id = urlDelete
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

//    private void refreshMessage(String urlD){
//        //post msg id = urlDelete
//        StringRequest deleteR = new StringRequest(Request.Method.DELETE, urlD,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("shy221", "Deleting message didn't work.");
//
//            }
//        });
//        MySingleton.getInstance(this).addToRequestQueue(deleteR);
//
//    }












}
