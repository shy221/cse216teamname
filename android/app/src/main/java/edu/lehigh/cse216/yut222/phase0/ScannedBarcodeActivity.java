package edu.lehigh.cse216.yut222.phase0;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class ScannedBarcodeActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);

    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {
                            intentData = barcodes.valueAt(0).displayValue;
                            txtBarcodeValue.setText("QR Code Detected");
                            checkUKey();
                        }
                    });

                }
            }
        });
    }

    private void checkUKey() {
        if (intentData.length() != 0) {
            int uidToUsername = intentData.indexOf("uId:");
            int usernameToSessionkey = intentData.indexOf(";uSername:");
            int sessionkeyToUemail = intentData.indexOf(";sessionKey:");
            int uemailToUintro = intentData.indexOf(";uEmail:");
            int uintroToEnd = intentData.indexOf(";uIntro:");
            // all of these fields should exist
            if (uidToUsername != -1 && usernameToSessionkey != -1 && sessionkeyToUemail != -1 && uemailToUintro != -1 && uintroToEnd != -1) {
                // parse intentData
                Map<String, String> map = new HashMap<>();
                map.put("uId", intentData.substring(uidToUsername + 4, usernameToSessionkey));
                map.put("uSername", intentData.substring(usernameToSessionkey + 10, sessionkeyToUemail));
                map.put("sessionKey", intentData.substring(sessionkeyToUemail + 12, uemailToUintro));
                map.put("uEmail", intentData.substring(uemailToUintro + 8, uintroToEnd));
                map.put("uIntro", intentData.substring(uintroToEnd + 8));

                sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("prefId",map.get("uId"));
                editor.putString("prefName",map.get("uSername"));
                editor.putString("prefEmail",map.get("uEmail"));
                editor.putString("prefIntro",map.get("uIntro"));
                editor.putString("prefKey",map.get("sessionKey"));
                editor.commit();

                String tmp = "uId: '" + sharedpreferences.getString("prefId", "default") + "' stored in shared preferences";
                txtBarcodeValue.setText(tmp);

                JSONObject d = new JSONObject(map);
                JsonObjectRequest postR = new JsonObjectRequest(Request.Method.POST, "https://arcane-refuge-67249.herokuapp.com/listmessages", d,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("mStatus").equals("ok")) {
                                        // valid sessionKey, go to welcome activity
                                        Intent in = new Intent(ScannedBarcodeActivity.this, WelcomeActivity.class);
                                        startActivity(in);
                                    } else {
                                        Log.e("login", "Invalid session key");
                                    }
                                } catch (final JSONException e) {
                                    Log.e("login", "Error parsing JSON file: onPostResponse/listmessages");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("zex220", "Listing all Messages didn't work!");
                    }
                });
                MySingleton.getInstance(this).addToRequestQueue(postR);
            } else {
                txtBarcodeValue.setText("Cannot parse IntentData");
            }
        } else {
            txtBarcodeValue.setText("Cannot get IntentData");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}