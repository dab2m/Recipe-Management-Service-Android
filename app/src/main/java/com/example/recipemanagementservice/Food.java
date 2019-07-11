package com.example.recipemanagementservice;

import java.util.ArrayList;

/**
 * Created by mustafatozluoglu on 14.06.2019
 */
public class Food {
    private String foodId;
    private String foodName;
    private String foodImage;
    private String foodDescription;
    private String foodTags;
    private String foodCreated;
    private String foodDate;

    public Food() {

    }

    public Food(String foodId, String foodName, String foodImage, String foodDescription, String foodTags, String foodCreated, String foodDate) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodDescription = foodDescription;
        this.foodTags = foodTags;
        this.foodCreated = foodCreated;
        this.foodDate = foodDate;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(String foodTags) {
        this.foodTags = foodTags;
    }

    public String getFoodCreated() {
        return foodCreated;
    }

    public void setFoodCreated(String foodCreated) {
        this.foodCreated = foodCreated;
    }

    public String getFoodDate() {
        return foodDate;
    }

    public void setFoodDate(String foodDate) {
        this.foodDate = foodDate;
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodImage='" + foodImage + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", foodTags=" + foodTags +
                ", foodCreated='" + foodCreated + '\'' +
                ", foodDate='" + foodDate + '\'' +
                '}';
    }

    public static ArrayList<Food> getData() {
        ArrayList<Food> foodList = new ArrayList<Food>();
        int foodImages[] = {R.drawable.frambuazli_pasta, R.drawable.fondu, R.drawable.nohutlu_pilav};
        String[] foodNames = {"Ozel Kremalı Frambuazlı Yaş Pasta", "Fondü", "Nohutlu Pilav"};

        for (int i = 0; i < foodImages.length; i++) {
            Food temp = new Food();
            temp.setFoodName(foodNames[i]);
            temp.setFoodDescription("description");
            foodList.add(temp);
        }
        return foodList;
    }

}
