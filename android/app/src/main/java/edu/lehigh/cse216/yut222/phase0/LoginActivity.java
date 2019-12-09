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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
    private static final String TAG = "lez221";
    private static final int RC_SIGN_IN = 9001;
    public static SharedPreferences sharedpreferences;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Beginning of onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.server_client_id))
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }

            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        Button scanBarcode = findViewById(R.id.btnScanBarcode);
        
        scanBarcode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ScannedBarcodeActivity.class));
            }

        });

    }

    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            updateUI(account.getIdToken());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.w(TAG, "Task = " + completedTask);
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String token = account.getIdToken();
            updateUI(token);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void updateUI(String token) {
        String urlLogin = "https://arcane-refuge-67249.herokuapp.com/login";
        Map<String, String> map = new HashMap<>();
        map.put("id_token", token);
        JSONObject m = new JSONObject(map);

        Log.d("json", m.toString());

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
                        String uKey = data.getString("sessionKey");
                        String uEmail = data.getString("uEmail");
                        String uIntro = data.getString("uIntro");

                        Log.e("Login page session key", uKey);
                        //Context context = LoginActivity.this;
                        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("prefId",uId);
                        editor.putString("prefName",uSername);
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

        // Signed in successfully, show authenticated UI.
    }
}

