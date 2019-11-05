package edu.lehigh.cse216.yut222.phase0;

import android.os.Bundle;
import android.widget.Button;


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
import android.content.SharedPreferences;
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

public class CommentActivity extends AppCompatActivity {
    ArrayList<Comment> mComments = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        int mId = getIntent().getIntExtra("message id", 404);
        final String urlComments =  "https://arcane-refuge-67249.herokuapp.com/" + mId + "/listcomments";


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
                Log.e("outside for loop", "Comment");
                //Log.e("outside for loop", mComments.get(0).cUsername);

            } else {
                if(response.getString("mMessage").equals("session key not correct..")){
                    Toast toast = Toast.makeText(CommentActivity.this, "Session Time Out", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(CommentActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Log.e("get comment", "mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("get comment", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("comments", "Successfully parsed JSON file.");
        showComment();
    }
    private void showComment(){
        Log.e("showComment","got in");
        RecyclerView rv = findViewById(R.id.comment_list_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        CommentListAdapter adapter = new CommentListAdapter(this, mComments);
        rv.setAdapter(adapter);

        adapter.setClickListener(new CommentListAdapter.ClickListener() {

            @Override
            public void onClick(Comment c) {
                //Toast for fun effect SY
                //Toast.makeText(MainActivity.this, message.mTitle , Toast.LENGTH_LONG).show();
                Intent i = new Intent(CommentActivity.this, ProfileActivityActivity.class);
                i.putExtra("user id", c.uId);

                startActivity(i);
                //change this function
            }
        });
    }
}
