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
import android.widget.Toast;
import android.preference.PreferenceManager;

public class LoginActivity  extends AppCompatActivity{
    public static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Get the parameter from the calling activity, and put it in the TextView
        Intent input = getIntent();
        String label_contents = input.getStringExtra("label_contents");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText etEmail = (EditText) findViewById(R.id.editText);
        final EditText etPassword = (EditText) findViewById(R.id.editText4);

        Button bOk = (Button) findViewById(R.id.buttonOk);
        bOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("login", "ok button");
                Intent i = new Intent();
                if (!etEmail.getText().toString().equals("")&& !etPassword.getText().toString().equals("") ) {
                    i.putExtra("title", etEmail.getText().toString());
                    i.putExtra("content", etPassword.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();
                    login(email, password);//uid is 7 for now
                    Log.e("login", "login method called");
                }else {
                    setResult(Activity.RESULT_OK, i);
                    Context context = LoginActivity.this;
                    Toast toast = Toast.makeText(context, "Please enter your email and password", Toast.LENGTH_LONG);
                    toast.show();
                    Log.e("where am i", "No input?");
                    //finish();
                }
            }
        });
        Button bCancel = (Button) findViewById(R.id.buttonCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("login", "cancel button");
                Intent i = new Intent();
                setResult(Activity.RESULT_CANCELED, i);
                finish();
            }
        });

        /*if(!(sharedpreferences.getString("prefKey", "default" ).equals("default"))){
            finish();
        }*/

    }
    private void login(final String e, final String p){
        //map is hashMap, m is jsonObject
        //one link to post

        String urlLogin = "https://arcane-refuge-67249.herokuapp.com/login";
        Map<String, String> map = new HashMap<>();
        map.put("uEmail", e);
        map.put("uPassword", p);
        JSONObject m = new JSONObject(map);



        //Test Code When Backend's not working
        /*if (e.equals("123") && p.equals("321")){
            Context context = LoginActivity.this;
            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("prefKey","I made this up");
            editor.commit();
            String PrefKey = sharedpreferences.getString("prefKey", "default");
            Log.e("Login page session key", PrefKey);
            Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(i);
        }else{
            Context context = LoginActivity.this;
            Toast toast = Toast.makeText(context, "WRONG PASSWORD", Toast.LENGTH_LONG);
            toast.show();
            Log.e("login", "onResponse mStatus error(password incorrect)");
        }*/
        JsonObjectRequest postR = new JsonObjectRequest(Request.Method.POST,
                urlLogin, m, new Response.Listener<JSONObject>() {
                @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("mStatus");
                    if (status.equals("ok")) {
                        //store user profile into a user object
                        //shared preference

                        JSONObject data = response.getJSONObject("mData");
                            String uId = data.getString("uId");
                            String uSername = data.getString("uSername");
                            String uPassword = data.getString("uPassword");
                            String uSalt = data.getString("uSalt");
                            String uKey = data.getString("sessionKey");
                            String uEmail = data.getString("uEmail");
                            String uIntro = data.getString("uIntro");

                        Log.e("Login page session key", uKey);
                        //Context context = LoginActivity.this;
                        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("prefId",uId);
                        editor.putString("prefName",uSername);
                        editor.putString("prefSalt",uSalt);
                        editor.putString("prefEmail",uEmail);
                        editor.putString("prefIntro",uIntro);
                        editor.putString("prefKey",uKey);
                        editor.commit();
                        String PrefKey = sharedpreferences.getString("prefKey", "default");
                        Log.e("Login page session key", PrefKey);
                        Intent in = new Intent(LoginActivity.this, WelcomeActivity.class);
                        startActivity(in);
                    } else {
                        Context context = LoginActivity.this;
                        Toast toast = Toast.makeText(context, "WRONG PASSWORD", Toast.LENGTH_LONG);
                        toast.show();
                        Log.e("login", "onResponse mStatus error(password incorrect)");
                    }
                } catch (final JSONException e) {
                        Log.e("login", "Error parsing JSON file: onPostResponse/login" );
                        return;
                    }
                Log.d("login", "successfully get string response session key");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("login", "Volley error");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(postR);
    }
}

