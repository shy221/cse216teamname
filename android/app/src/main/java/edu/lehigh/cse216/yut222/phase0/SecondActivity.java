//Shenyi Yu Phase 1
package edu.lehigh.cse216.yut222.phase0;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.FileUtils;
import android.os.Bundle;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

import static edu.lehigh.cse216.yut222.phase0.LoginActivity.sharedpreferences;

public class SecondActivity extends AppCompatActivity {

    String image;
    ImageView imageView;
    String currentPhotoPath;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;

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

        Button bGG = (Button)findViewById(R.id.buttonGoGallery);
        bGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent with action as ACTION_PICK
                Intent intent=new Intent(Intent.ACTION_PICK);
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.setType("image/*");
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                String[] mimeTypes = {"image/jpg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                // Launching the Intent
                startActivityForResult(intent,0);
            }
        });

        Button bTP = (Button) findViewById(R.id.buttonTakePicture);
        //imageView = (ImageView) findViewById(R.id.imageView);
        if (!hasCamera()) {
            bTP.setEnabled(false);
        }
        bTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                /*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                */
            }
        });

        Button bOk = (Button) findViewById(R.id.buttonOk);
        bOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                if (!etTitle.getText().toString().equals("")&& !etContent.getText().toString().equals("") ) {
                    File file = new File (image);
                    //byte[] fileContent = FileUtils.readFileToByteArray(file)); version too new
                    //String encodedString = java.util.Base64.getEncoder().encodeToString(fileContent);
                    String encodedString = "";
                    try {
                        byte[] fileContent = new byte[(int) file.length() + 100];
                        @SuppressWarnings("resource")
                        int length = new FileInputStream(file).read(fileContent);
                        encodedString = Base64.encodeToString(fileContent, 0, length, Base64.DEFAULT);
                        //int flags = Base64.NO_WRAP | Base64.URL_SAFE;
                        //byte[] a = Base64.encode(fileContent, Base64.DEFAULT);
                        //Log.e("byte", "byte" + a);
                        //String encodedString = new String(a);
                        //String encodedString = Base64.encodeToString(fileContent, flags);
                        Log.e("encoded String", "encoded String" + encodedString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    i.putExtra("title", etTitle.getText().toString());
                    i.putExtra("content", etContent.getText().toString());
                    setResult(Activity.RESULT_OK, i);
                    String title = etTitle.getText().toString();
                    String content = etContent.getText().toString();
                    postMessage(title, content, encodedString, sharedpreferences.getString("prefId","default"));
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

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private static byte[] loadFile(File file){
        try {
            java.io.InputStream is = new FileInputStream(file);

            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }
            byte[] bytes = new byte[(int) length];

            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }

            is.close();
            return bytes;
        } catch (IOException e) {
            System.out.println("convertion from file to 64 bits error failed");
            return new byte[0]; //not certain of this return type
        }
    }

    /*
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        //the youtube video only use 0 instead of GALLERY_REQUEST_CODE
        startActivityForResult(intent,GALLERY_REQUEST_CODE);

    }
    */


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            File imgFile = new  File(currentPhotoPath);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = (ImageView) findViewById(R.id.imageView);
                myImage.setImageBitmap(myBitmap);
            }
            image = currentPhotoPath;
            Log.e("requestCode", "REQUEST_TAKE_PHOTO");
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);
            //dispatchTakePictureIntent();
            Log.e("requestCode", "REQUEST_IMAGE_CAPTURE");
        }
        //super.onActivityResult(requestCode, resultCode, data);
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == RESULT_OK && requestCode == 0) {
            if (data == null) {
                Toast.makeText(this, "Unable to choose image", Toast.LENGTH_SHORT).show();
                return;
            }
        Uri imageUri = data.getData();
        Log.e("image uri", "uri" + imageUri);
        image = getRealPathFromUri(imageUri);
        Log.e("image string", "uri" + image);
            Bitmap myBitmap = BitmapFactory.decodeFile(image);
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            myImage.setImageBitmap(myBitmap);
        }

    }

    private String getRealPathFromUri (Uri uri){
        String[] projection = {MediaStore.Images.Media.DATA};
        androidx.loader.content.CursorLoader loader = new androidx.loader.content.CursorLoader (getApplicationContext(), uri, projection, null, null, null);
        android.database.Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }

/*
//take pic without saving it
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
 */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                galleryAddPic();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("CreateImageFile() wrong", "No idea how to fix");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "edu.lehigh.cse216.yut222.phase0.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Log.e(" galleryAddPic()", "called");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //Environment.getExternalStorageDirectory().getPath()
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.e(" createImageFile()", "called");
        Log.e(" createImageFile()", "dir " + getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //t -> title, c -> content, e -> encodedString
    private void postMessage(final String t, final String c, final String e, final String uid){
        //map is hashMap, m is jsonObject
        //one link to post
        String urlPost = "https://arcane-refuge-67249.herokuapp.com/messages";
        Map<String, String> map = new HashMap<>();
        map.put("uid", uid);
        map.put("mTitle", t);
        map.put("mMessage", c);
        map.put("fileData", e);
        map.put("mLink", "");
        map.put("mime", "image/jpg");
        map.put("sessionKey", sharedpreferences.getString("prefKey", "default"));
        map.put("uEmail", sharedpreferences.getString("prefEmail", "default"));
        JSONObject m = new JSONObject(map);
        JsonObjectRequest postR = new JsonObjectRequest(Request.Method.POST,
                urlPost, m, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("mStatus");
                    if(!status.equals("ok")){
                        if(response.getString("mMessage").equals("session key not correct..")){
                            Toast toast = Toast.makeText(SecondActivity.this, "Session Time Out", Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(SecondActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        Log.d("get detail", "mStatus is not ok: " + response.getString("mMessage"));
                    }
                } catch (final JSONException e) {
                    Log.d("shy221", "Error parsing JSON file: " + e.getMessage());
                    return;
                }
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
