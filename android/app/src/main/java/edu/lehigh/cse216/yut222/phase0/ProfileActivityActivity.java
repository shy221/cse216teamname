package edu.lehigh.cse216.yut222.phase0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class ProfileActivityActivity extends AppCompatActivity {

    ArrayList<Message> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final int uid = intent.getIntExtra("user id", 404);

        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        //one to show all message
        String urlList = "https://arcane-refuge-67249.herokuapp.com/listmessages";
        Map<String, String> map = new HashMap<>();
        map.put("uEmail", sharedpreferences.getString("prefEmail","default"));
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        JSONObject m = new JSONObject(map);

        JsonObjectRequest listR = new JsonObjectRequest(Request.Method.POST, urlList, m,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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

    private void populateListFromVolley(JSONObject response){
        try {
            mData.clear();
            String status;
            status = response.getString("mStatus");
            //this is to check if status went wrong
            // Log.e("shy221", status);
            if(status.equals("ok")){
                JSONArray data = response.getJSONArray("mData");
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
}
