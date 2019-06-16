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

    public Food(){

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
        int foodImages[] = {R.drawable.no, R.drawable.no, R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no,R.drawable.no};
        String[] foodNames = {"Geleceği Yazanlar", "Paycell", "Tv+","Dergilik","Bip","GNC","Hesabım","Sim","LifeBox","Merhaba Umut","Yaani","Hayal Ortağım","Goller Cepte","Piri"};

        for (int i = 0; i < foodImages.length; i++) {
            Food temp = new Food();
            temp.setFoodImage(foodImages[i]);
            temp.setFoodName(foodNames[i]);
            temp.setFoodDescription("Turkcell");
            temp.setFoodTag("asd");
            foodList.add(temp);
        }
        return foodList;
    }
}
