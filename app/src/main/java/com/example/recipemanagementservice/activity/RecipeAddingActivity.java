package com.example.recipemanagementservice.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recipemanagementservice.R;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Berk on 10.06.2019.
 */
public class RecipeAddingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etYemekIsmi;
    ImageView imgYemekResmi;
    EditText etYemekAciklamasi;
    EditText etYemekEtiketleri;
    Button btnKaydet;
    Button btnIptalet;
    Button btnResimEkle;
    Uri imageUri;

    private static String homepageURL = "http://recipemanagementservice495.herokuapp.com/post.php?list";

    String foodName;
    String foodDescription;
    String tags[];
    String foodTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_adding);
        etYemekIsmi = (EditText) findViewById(R.id.etyemekIsmi);
        etYemekAciklamasi = (EditText) findViewById(R.id.etyemekAciklamasi);
        etYemekEtiketleri = (EditText) findViewById(R.id.etyemekEtiketleri);
        imgYemekResmi = (ImageView) findViewById(R.id.imgYemekResmi);
        imgYemekResmi.setImageResource(R.drawable.no);
        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnIptalet = (Button) findViewById(R.id.btnIptalEt);
        btnResimEkle = (Button) findViewById(R.id.btnResimEkle);
        btnKaydet.setOnClickListener(this);
        btnIptalet.setOnClickListener(this);
        btnResimEkle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnResimEkle:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
                break;
            case R.id.btnKaydet:
                foodName = etYemekIsmi.getText().toString();
                foodDescription = etYemekAciklamasi.getText().toString();
                tags = new String[1];
                foodTags = etYemekEtiketleri.getText().toString();
                tags[0] = foodTags;
                if (foodName.isEmpty() || foodDescription.isEmpty() || foodTags.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Adding is successful", Toast.LENGTH_LONG).show();
                    etYemekIsmi.setText("");
                    imgYemekResmi.setImageResource(R.drawable.no);
                    etYemekAciklamasi.setText("");
                    etYemekEtiketleri.setText("");
                    try{
                        sendPost(homepageURL);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btnIptalEt:
                etYemekIsmi.setText("");
                imgYemekResmi.setImageResource(R.drawable.no);
                etYemekAciklamasi.setText("");
                etYemekEtiketleri.setText("");
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data.getData();
            imgYemekResmi.setImageURI(imageUri);
        }
    }

    public void sendPost(final String requestUrl) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(requestUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("recipeName", foodName);
                    jsonParam.put("recipeDescription", foodDescription);
                    jsonParam.put("recipeTags", tags);
                    jsonParam.put("created", "BERK");
                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream  os = new DataOutputStream (conn.getOutputStream());
                    os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.flush();
                    os.close();
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG", conn.getResponseMessage());
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}