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
import com.example.recipemanagementservice.adapter.FoodMyRecipesAdapter;
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
    FoodMyRecipesAdapter foodAdapter;
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
            foodAdapter = new FoodMyRecipesAdapter(MyRecipesActivity.this, recipeArrayList);
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
                        String foodId = recipe.getString("recipeId");
                        String foodName = recipe.getString("recipeName");
                        String foodImage = recipe.getString("recipeImage");
                        String foodDescription = recipe.getString("recipeDescription");
                        JSONArray foodTagsArray = recipe.getJSONArray("recipeTags");
                        StringBuilder foodTags = new StringBuilder();
                        for (int j = 0; j < foodTagsArray.length(); j++)
                            foodTags.append(foodTagsArray.getString(j)).append(", ");
                        foodTags = new StringBuilder(foodTags.substring(0, foodTags.length() - 2));
                        String foodCreated = recipe.getString("created");
                        String foodDate = recipe.getString("recipeDate");
                        int foodLikes = recipe.getInt("likes");
                        FoodModel foodModel = new FoodModel(foodId, foodName, foodImage, foodDescription, foodTags.toString(), foodCreated, foodDate, foodLikes);
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