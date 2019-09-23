package edu.lehigh.cse216.yut222.phase0;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ArrayList<Message> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent =getIntent();
        int mId = intent.getIntExtra("message id", 000);
        String mTitle = intent.getStringExtra("message title");
        String mContent = intent.getStringExtra("message content");
        String url = "https://arcane-refuge-67249.herokuapp.com/messages/";
        String urlDetail = url + mId;
        Message m = new Message(mId, mTitle, mContent);
        mData.add(new Message(mId, mTitle, mContent));
        showDetail(m);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //post msg id and new msg content = urlUpdate
        StringRequest updateR = new StringRequest(Request.Method.PUT, urlDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO: modify to post msg id, and new msg content
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("shy221", "Updating message content didn't work.");

            }
        });
        MySingleton.getInstance(this).addToRequestQueue(updateR);

        //post msg id = urlDelete
        StringRequest deleteR = new StringRequest(Request.Method.DELETE, urlDetail,
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

    private void showDetail(Message m){
        RecyclerView rv = (RecyclerView) findViewById(R.id.message_detail_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DetailListAdapter adapter = new DetailListAdapter(this, mData);
        rv.setAdapter(adapter);

    }











}
