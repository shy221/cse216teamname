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

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Get the parameter from the calling activity, and put it in the TextView
        Intent input = getIntent();
        String label_contents = input.getStringExtra("label_contents");
        TextView tv = (TextView) findViewById(R.id.specialMessage);
        tv.setText(label_contents);

        // The OK button gets the text from the input box and returns it to the calling activity
        final EditText etTitle = (EditText) findViewById(R.id.editText);
        final EditText etContent = (EditText) findViewById(R.id.editText4);


        Button bOk = (Button) findViewById(R.id.buttonOk);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();


                if (!etTitle.getText().toString().equals("")&& !etContent.getText().toString().equals("") ) {
                    i.putExtra("title", etTitle.getText().toString());
                    i.putExtra("content", etContent.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    String title = etTitle.getText().toString();
                    String content = etContent.getText().toString();
                    postMessage(title, content);
                }
                finish();

            }
        });

        // The Cancel button returns to the caller without sending any data
        Button bCancel = (Button) findViewById(R.id.buttonCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    private void postMessage(final String t, final String c){
        //one link to post
        String urlPost = "https://arcane-refuge-67249.herokuapp.com/messages";
        Map<String, String> map = new HashMap<>();
        map.put("mTitle", t);
        map.put("mMessage", c);
        JSONObject m = new JSONObject(map);
        JsonObjectRequest postR = new JsonObjectRequest(Request.Method.POST,
                urlPost, m, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("message", "enter your message with title and content here.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Something wrong", "later to figure out why.");
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(postR);
    }

}
