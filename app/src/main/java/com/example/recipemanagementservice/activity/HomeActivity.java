package com.example.recipemanagementservice.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.recipemanagementservice.R;
import com.example.recipemanagementservice.adapter.FoodHomeAdapter;
import com.example.recipemanagementservice.firebase.MySingleton;
import com.example.recipemanagementservice.model.FoodModel;
import com.example.recipemanagementservice.network.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mustafatozluoglu on 10.06.2019
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    JSONParser jsonParser;
    ProgressDialog progressDialog;
    FoodHomeAdapter foodAdapter;

    private static String homepageURL = "http://recipemanagementservice495.herokuapp.com/get.php?list";

    Button bYeniYemekTarifi;
    Button bTariflerim;
    Button bAyarlar;
    String app_server_url = "http://";
    Button bBildirim;
    ListView yemekListesi;
    EditText editText;
    private String[] list;
    private ArrayAdapter adapter;
    ArrayList<String> aList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        yemekListesi = (ListView) findViewById(R.id.yemekListesi);
        new getRecipe().execute();
        editText = (EditText) findViewById(R.id.search_filter);
        bYeniYemekTarifi = (Button) findViewById(R.id.bYeniYemekTarifi);
        bTariflerim = (Button) findViewById(R.id.bTariflerim);
        bAyarlar = (Button) findViewById(R.id.bAyarlar);
        /*bAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("fcm_token", token);
                        return params;
                    }
                };
                MySingleton.getmInstance(HomeActivity.this).addToRequestQueue(stringRequest);
            }
        });*/
        bBildirim = (Button) findViewById(R.id.bCikis);
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
            case R.id.bCikis:
                System.exit(0);
                break;
        }
    }

    private class getRecipe extends AsyncTask<Void, Void, Void> {

        ArrayList<FoodModel> recipeArrayList = new ArrayList<>();
        ArrayList<FoodModel> recipeArrayList2 = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setMessage("Processing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            foodAdapter = new FoodHomeAdapter(HomeActivity.this, recipeArrayList);
            if (progressDialog.isShowing()) {
                yemekListesi.setAdapter(foodAdapter);
                /*String s = yemekListesi.getItemAtPosition(1).toString();
                recipeArrayList2 = convert(s);*/
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        yemekListesi.setAdapter(new FoodHomeAdapter(HomeActivity.this, recipeArrayList2));
                        aList.clear();
                        for (int i = 0; i < list.length; i++)
                            if (list[i].toLowerCase().contains(s.toString().toLowerCase()))
                                aList.add(list[i]);
                        if (aList != null && aList.size() > 0){
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                                    (HomeActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
                            yemekListesi.setAdapter(dataAdapter);
                        }
                        else if(aList.size() == 0) {
                            yemekListesi.setAdapter(new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, aList));
                            yemekListesi.setAdapter(foodAdapter);
                        }
                        else if(s.length() == 0 ) {
                            yemekListesi.setAdapter(new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, list));
                            yemekListesi.setAdapter(foodAdapter);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                /*adapter = new ArrayAdapter(HomeActivity.this, R.layout.item_recipe_with_like, list);
                yemekListesi2.setAdapter(adapter);
                theFilter.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        (HomeActivity.this).adapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });*/
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
                    //list = new ArrayList<>();
                    list = new String[recipes.length()];
                    for (int i = 0; i < recipes.length(); i++) {
                        JSONObject recipe = recipes.getJSONObject(i);
                        String foodId = recipe.getString("recipeId");
                        String foodName = recipe.getString("recipeName");
                        //list.add(recipeName);
                        list[i] = foodName;
                        String foodImage = recipe.getString("recipeImage");
                        String foodDescription = recipe.getString("recipeDescription");
                        //list.add(recipeDescription);
                        JSONArray foodTagsArray = recipe.getJSONArray("recipeTags");
                        String[] foodTags = new String[foodTagsArray.length()];
                        for (int j = 0; j < foodTags.length; j++) {
                            foodTags[j] = foodTagsArray.getString(j);
                            //list.add(recipeTags[j]);
                        }
                        String foodCreated = recipe.getString("created");
                        String foodDate = recipe.getString("recipeDate");
                        int foodLikes = recipe.getInt("likes");
                        FoodModel foodModel = new FoodModel(foodId, foodName, foodImage, foodDescription, foodTags, foodCreated, foodDate, foodLikes);
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

    /*public ArrayList<FoodModel> convert(String s){
        String foodId;
        String foodName;
        String foodImage;
        String foodDescription;
        String[] foodTags;
        String foodCreated;
        String foodDate;
        int foodLikes;
        foodId = s.substring(s.indexOf("=")+2,s.indexOf(",")-1);
        s = s.substring(s.indexOf(",")+1);
        foodName = s.substring(s.indexOf("=")+2,s.indexOf(",")-1);
        s = s.substring(s.indexOf(",")+1);
        foodImage = s.substring(s.indexOf("=")+2,s.indexOf(",")-1);
        s = s.substring(s.indexOf(",")+1);
        foodDescription = s.substring(s.indexOf("=")+2,s.indexOf(",")-1);
        s = s.substring(s.indexOf(",")+1);
        foodTags = new String[]{"pilav"};
        s = s.substring(s.indexOf("'")+1);
        foodCreated = "berk";
        s = s.substring(s.indexOf(",")+1);
        foodDate = "2019-07-24";
        FoodModel fm = new FoodModel(foodId, foodName, foodImage, foodDescription, foodTags, foodCreated, foodDate, 0);
        ArrayList<FoodModel> recipeArrayList3 = new ArrayList<>();
        recipeArrayList3.add(fm);
        return recipeArrayList3;
    }*/
}