package com.example.recipemanagementservice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by Berk on 10.06.2019.
 */
public class RecipeAdding extends AppCompatActivity implements View.OnClickListener {

    EditText etYemekIsmi;
    ImageView imgYemekResmi;
    EditText etYemekAciklamasi;
    EditText etYemekEtiketleri;
    Button btnKaydet;
    Button btnIptalet;
    Button btnResimEkle;
    Uri imageUri;

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
                String foodName = etYemekIsmi.getText().toString();
                String foodDescription = etYemekAciklamasi.getText().toString();
                String tags[] = new String[1];
                String foodTags = etYemekEtiketleri.getText().toString();
                tags[0] = foodTags;
                int foodImage = imageViewToInt(imgYemekResmi);
                if (foodName.isEmpty() || foodDescription.isEmpty() || foodTags.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                } else {
                    //Food food = new Food(foodName, foodImage, foodDescription, foodTags);
                    //DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    //db.insertFoodData(food);

                    Toast.makeText(getApplicationContext(), "Adding is successful", Toast.LENGTH_LONG).show();
                    etYemekIsmi.setText("");
                    imgYemekResmi.setImageResource(R.drawable.no);
                    etYemekAciklamasi.setText("");
                    etYemekEtiketleri.setText("");
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

    public static int imageViewToInt(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray[0];
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            imageUri = data.getData();
            imgYemekResmi.setImageURI(imageUri);
        }
    }


}