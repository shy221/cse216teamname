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
import android.widget.Toast;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<User> thisUser = new ArrayList<>();
    Button logout = null;
    Button activity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("user id", 404);
        final String urlProfile =  "https://arcane-refuge-67249.herokuapp.com/" + id + "/userprofile";
        Map<String, String> map = new HashMap<>();
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        map.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        JSONObject c = new JSONObject(map);

        //get comments from route
        //routes incomplete
        JsonObjectRequest listP = new JsonObjectRequest(Request.Method.POST, urlProfile, c,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        populateProfileFromVolley(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("zex220", "Listing all comments didn't work!");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(listP);

        logout = (Button) findViewById(R.id.Logout);
        if (id == Integer.parseInt(sharedpreferences.getString("prefId","404"))) {
            logout.setVisibility(View.VISIBLE);
        }
        else {
            logout.setVisibility(View.GONE);
        }
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

    }
    public void logout(View view){
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void populateProfileFromVolley(JSONObject response) {
        try {
            thisUser.clear();
            String status;
            status = response.getString("mStatus");
            //this is to check if status went wrong
            String message = "";
            if (status.equals("error")) {
                message = response.getString("mMessage");
            }
            Log.e("lez221", status + " " + message);
            if (status.equals("ok")) {
                JSONObject data = response.getJSONObject("mData");
                int id = data.getInt("uId");
                String username = data.getString("uSername");
                String email = data.getString("uEmail");
                String intro = data.getString("uIntro");
                thisUser.add(new User(id, username, intro, email));
                Log.e("populate detail", id + username + email + intro);
                RecyclerView rv = (RecyclerView) findViewById(R.id.profile_detail_view);
                rv.setLayoutManager(new LinearLayoutManager(this));
                ProfileListAdapter adapter = new ProfileListAdapter(this, thisUser);
                rv.setAdapter(adapter);
                Log.e("populate detail", "got details");
            } else {
                if(response.getString("mMessage").equals("session key not correct..")){
                    Toast toast = Toast.makeText(ProfileActivity.this, "Session Time Out", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Log.d("get profile", "mStatus is not ok.");
            }
        } catch (final JSONException e) {
            Log.d("get detail", "Error parsing JSON file: " + e.getMessage());
            return;
        }
        Log.d("/messages/mid", "Successfully parsed JSON file.");
    }
}
