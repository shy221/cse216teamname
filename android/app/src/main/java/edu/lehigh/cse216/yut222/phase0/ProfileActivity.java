//Zehui Xiao phase1
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
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.content.SharedPreferences;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<User> thisUser = new ArrayList<>();
    Button logout = null;
    Button activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final int id = Integer.parseInt(sharedpreferences.getString("prefId","default"));
        thisUser.add(new User(id, sharedpreferences.getString("prefName", "default"),
                sharedpreferences.getString("prefIntro","default"), sharedpreferences.getString("prefEmail", "default")));

        logout = (Button) findViewById(R.id.Logout);
        activity = (Button) findViewById(R.id.Activity);
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, ProfileActivityActivity.class);
                i.putExtra("user id", id);
                startActivity(i);
            }
        });

        /*final String urlComments =  "https://arcane-refuge-67249.herokuapp.com/" + mId + "/listcomments";
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
*/



        RecyclerView rv = (RecyclerView) findViewById(R.id.profile_detail_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ProfileListAdapter adapter = new ProfileListAdapter(this, thisUser);
        rv.setAdapter(adapter);
    }
    public void logout(View view){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        finish();
    }
}
