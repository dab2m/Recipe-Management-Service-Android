package com.example.recipemanagementservice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.adapter.FoodHomeAdapter;
import com.example.recipemanagementservice.model.FoodModel;
import com.example.recipemanagementservice.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 10.06.2019
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    JSONParser jsonParser;
    ProgressDialog progressDialog;
    FoodHomeAdapter foodAdapter;

    private static String homepageURL = "http://recipemanagementservice495.herokuapp.com/get.php?list";

    Button bYeniYemekTarifi;
    Button bTariflerim;
    Button bAyarlar;
    Button bBildirim;
    SearchView yemekArama; // TODO search ozelligi eklenecek
    ListView yemekListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        yemekListesi = (ListView) findViewById(R.id.yemekListesi);
        new getRecipe().execute();
        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);
        bTariflerim = (Button) findViewById(R.id.bTariflerim);
        bAyarlar = (Button) findViewById(R.id.bAyarlar);
        bBildirim = (Button) findViewById(R.id.bBildirim);
        bYeniYemekTarifi.setOnClickListener(this);
        bTariflerim.setOnClickListener(this);
        bAyarlar.setOnClickListener(this);
        bBildirim.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bYeniYemekTarifi:
                startActivity(new Intent(this, RecipeAddingActivity.class));
                break;
            case R.id.bTariflerim:
                startActivity(new Intent(this, MyRecipesActivity.class));
                break;
            case R.id.bAyarlar:
                startActivity(new Intent(this, MySettingsActivity.class));
                break;
            case R.id.bBildirim:
                startActivity(new Intent(this, MyNotificationActivity.class));
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    private class getRecipe extends AsyncTask<Void, Void, Void> {

        ArrayList<FoodModel> recipeArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            foodAdapter = new FoodHomeAdapter(HomeActivity.this, recipeArrayList);
            if (progressDialog.isShowing()) {
                yemekListesi.setAdapter(foodAdapter);
                progressDialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParser = new JSONParser();
            String jsonString = null;
            try {
                jsonString = jsonParser.makeServiceCall(homepageURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("JSON_RESPONSE", jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    JSONArray recipes = jsonObject.getJSONArray("Recipes");
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject recipe = recipes.getJSONObject(i);
                        String recipeId = recipe.getString("recipeId");
                        String recipeName = recipe.getString("recipeName");
                        String recipeImage = recipe.getString("recipeImage");
                        String recipeDescription = recipe.getString("recipeDescription");
                        JSONArray foodTagsArray = recipe.getJSONArray("recipeTags");
                        String[] recipeTags = new String[foodTagsArray.length()];
                        for (int j = 0; j < recipeTags.length; j++)
                            recipeTags[j] = foodTagsArray.getString(j);
                            //recipeTags.append(recipeTagsArray.getString(j)).append(", ");
                        String created = recipe.getString("created");
                        String recipeDate = recipe.getString("recipeDate");
                        int likes = recipe.getInt("likes");
                        FoodModel foodModel = new FoodModel(recipeId, recipeName, recipeImage, recipeDescription, recipeTags, created, recipeDate, likes);
                        recipeArrayList.add(foodModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("JSON_RESPONSE", "Empty page resource!");
            }
            return null;
        }
    }
}