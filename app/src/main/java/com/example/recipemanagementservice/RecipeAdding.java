package com.example.recipemanagementservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RecipeAdding extends AppCompatActivity {

    EditText recipeNameText;
    EditText recipeTagsText;
    EditText recipeExplanationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_adding);

        recipeNameText = (EditText) findViewById(R.id.recipeNameText);
        recipeTagsText = (EditText) findViewById(R.id.recipeTagsText);
        recipeExplanationText = (EditText) findViewById(R.id.recipeExplanationText);
    }
}
