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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        int id = Integer.parseInt(sharedpreferences.getString("prefId","default"));
        thisUser.add(new User(id, sharedpreferences.getString("prefUsername", "default"),
                sharedpreferences.getString("prefIntro","default"), sharedpreferences.getString("prefEmail", "default")));

        // Get the parameter from the calling activity, and put it in the TextView
        Intent input = getIntent();
/*
        final TextView username = (TextView)findViewById(R.id.username);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        username.setText(sharedpreferences.getString("prefUsername", "username"));

        final TextView uid = (TextView)findViewById(R.id.uid);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uid.setText(sharedpreferences.getString("prefId", "id"));

        final TextView uemail = (TextView)findViewById(R.id.uemail);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uemail.setText(sharedpreferences.getString("prefEmail", "email"));

        final TextView uIntro = (TextView)findViewById(R.id.uintro);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uIntro.setText(sharedpreferences.getString("prefIntro", "intro"));*/
        RecyclerView rv = (RecyclerView) findViewById(R.id.profile_detail_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ProfileListAdapter adapter = new ProfileListAdapter(this, thisUser);
        rv.setAdapter(adapter);
    }
}
