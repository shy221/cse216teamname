//Zehui Xiao Phase 2
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
import android.content.Context;

public class WelcomeActivity extends AppCompatActivity {
    Button bu=null;
    Button bu2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        bu=(Button)findViewById(R.id.button2);
        bu2=(Button)findViewById(R.id.button3);
    }

    public void logout(View view){
        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        finish();
    }

    public void close(View view){
        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(i);
    }

}
