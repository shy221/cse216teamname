package edu.lehigh.cse216.yut222.phase0;

import android.os.Bundle;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("yut222", "Debug Message from onCreate");

        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.cse.lehigh.edu/~spear/courses.json";

        // Request a string response from the provided URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final ArrayList<String> myList = new ArrayList<>();
                        try {
                            JSONArray jStringArray = new JSONArray(response);
                            for (int i = 0; i < jStringArray.length(); i++) {
                                myList.add(jStringArray.getString(i));
                            }
                        } catch (final JSONException e) {
                            Log.d("yut222", "Error parsing JSON file..." + e.getMessage());
                        }
                        ListView mListView = (ListView) findViewById(R.id.datum_list_view);
                        ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                myList);
                        mListView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("yut222", "That didn't work!");
                    }
        });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
