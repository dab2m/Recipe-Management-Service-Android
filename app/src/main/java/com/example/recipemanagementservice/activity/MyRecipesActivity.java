package com.example.recipemanagementservice.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.adapter.FoodAdapterForMyRecipes;
import com.example.recipemanagementservice.model.FoodModel;
import com.example.recipemanagementservice.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 06.07.2019
 */

public class MyRecipesActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<FoodModel> recipeArrayList = new ArrayList<>();
    JSONParser jsonParser;
    ProgressDialog progressDialog;
    FoodAdapterForMyRecipes foodAdapter;
    private static String username;
    private static String myRecipesURL = "http://recipemanagementservice495.herokuapp.com/get.php?tariflerim=";
    ListView tariflerimYemekListesi;
    Button bDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);


        bDelete = (Button) findViewById(R.id.bDelete);
        tariflerimYemekListesi = (ListView) findViewById(R.id.tariflerimYemekListesi);
        //bDelete.setOnClickListener(this);
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE); // LoginActivity sayfasindan username'i almak icin kullanildi!
        username = prefs.getString("username", "UNKNOWN");
        new getRecipe().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bDelete:
                String deleteUrl = "http://recipemanagementservice495.herokuapp.com/rest.php?tariflerim=";
                recipeArrayList.remove(0); // TODO remove islemi yapilacak deneme icin 0 verdim
                break;
        }
    }

    private class getRecipe extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MyRecipesActivity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            foodAdapter = new FoodAdapterForMyRecipes(MyRecipesActivity.this, recipeArrayList);
            if (progressDialog.isShowing()) {
                tariflerimYemekListesi.setAdapter(foodAdapter);
                progressDialog.dismiss();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            jsonParser = new JSONParser();
            String jsonString = null;
            try {
                jsonString = jsonParser.makeServiceCall(myRecipesURL + username);
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
                        JSONArray recipeTagsArray = recipe.getJSONArray("recipeTags");
                        StringBuilder recipeTags = new StringBuilder();
                        for (int j = 0; j < recipeTagsArray.length(); j++)
                            recipeTags.append(recipeTagsArray.getString(j)).append(", ");
                        recipeTags = new StringBuilder(recipeTags.substring(0, recipeTags.length() - 2));
                        String created = recipe.getString("created");
                        String recipeDate = recipe.getString("recipeDate");
                        int likes = recipe.getInt("likes");
                        FoodModel foodModel = new FoodModel(recipeId, recipeName, recipeImage, recipeDescription, recipeTags.toString(), created, recipeDate, likes);
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