package com.example.recipemanagementservice;

/**
 * Created by mustafatozluoglu on 14.06.2019
 */
public class Food {
    private String foodName;
    private byte[] foodImage;
    private String foodDescription;
    private String foodTag;

    public Food(String foodName, byte[] foodImage, String foodDescription, String foodTag) {
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

    public byte[] getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(byte[] foodImage) {
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
}
