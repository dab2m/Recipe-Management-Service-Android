package com.example.recipemanagementservice;

import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 14.06.2019
 */
public class Food {
    private String foodName;
    private int foodImage;
    private String foodDescription;
    private String foodTag;

    public Food() {

    }

    public Food(String foodName, int foodImage, String foodDescription, String foodTag) {
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodDescription = foodDescription;
        this.foodTag = foodTag;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(int foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodTag() {
        return foodTag;
    }

    public void setFoodTag(String foodTag) {
        this.foodTag = foodTag;
    }

    public static ArrayList<Food> getData() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        int foodImages[] = {R.drawable.frambuazli_pasta, R.drawable.fondu, R.drawable.nohutlu_pilav};
        String[] foodNames = {"Ozel Kremalı Frambuazlı Yaş Pasta", "Fondü", "Nohutlu Pilav"};

        for (int i = 0; i < foodImages.length; i++) {
            Food temp = new Food();
            temp.setFoodImage(foodImages[i]);
            temp.setFoodName(foodNames[i]);
            temp.setFoodDescription("description");
            temp.setFoodTag("recipe");
            foodList.add(temp);
        }
        return foodList;
    }
}
