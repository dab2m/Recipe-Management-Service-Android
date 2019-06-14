package com.example.recipemanagementservice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class RecipeAdding extends AppCompatActivity implements View.OnClickListener {

    EditText etYemekIsmi;
    EditText etYemekAciklamasi;
    EditText etYemekEtiketleri;
    ImageView imgYemekResmi;

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


        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnIptalet = (Button) findViewById(R.id.btnIptalEt);
        btnResimEkle = (Button) findViewById(R.id.btnResimEkle);


        btnKaydet.setOnClickListener(this);
        btnIptalet.setOnClickListener(this);
        btnResimEkle.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnResimEkle:
                Intent galeri_int = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(galeri_int, 100);

                break;
            case R.id.btnKaydet:
                break;
            case R.id.btnIptalEt:
                etYemekEtiketleri.setText("");
                etYemekAciklamasi.setText("");
                etYemekIsmi.setText("");
               imgYemekResmi.setImageDrawable(Drawable.createFromPath(""));
                break;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK && requestCode == 100){
            imageUri = data.getData();
            imgYemekResmi.setImageURI(imageUri);
        }
    }

}
