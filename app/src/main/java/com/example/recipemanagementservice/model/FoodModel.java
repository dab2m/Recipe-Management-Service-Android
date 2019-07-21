package com.example.recipemanagementservice.model;

/**
 * Created by mustafatozluoglu on 14.06.2019
 */
public class FoodModel {
    private String foodId;
    private String foodName;
    private String foodImage;
    private String foodDescription;
    private String foodTags;
    private String foodCreated;
    private String foodDate;
    private int foodLikes;

    public FoodModel() {
    }

    public FoodModel(String foodId, String foodName, String foodImage, String foodDescription, String foodTags, String foodCreated, String foodDate, int foodLikes) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodImage = foodImage;
        this.foodDescription = foodDescription;
        this.foodTags = foodTags;
        this.foodCreated = foodCreated;
        this.foodDate = foodDate;
        this.foodLikes = foodLikes;
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

    public int getFoodLikes() {
        return foodLikes;
    }

    public void setFoodLikes(int foodLikes) {
        this.foodLikes = foodLikes;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodImage='" + foodImage + '\'' +
                ", foodDescription='" + foodDescription + '\'' +
                ", foodTags=" + foodTags +
                ", foodCreated='" + foodCreated + '\'' +
                ", foodDate='" + foodDate + '\'' +
                '}';
    }
}