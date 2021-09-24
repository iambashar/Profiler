package com.teamdui.profiler.ui.dailycalorie;

public class Food {
    String foodName;
    String calorieEach;
    int deleteIcon;
    String key;

    public Food(){

    }

    public Food(String foodName, String calorieEach, int deleteIcon, String key) {
        this.foodName = foodName;
        this.calorieEach = calorieEach;
        this.deleteIcon = deleteIcon;
        this.key = key;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCalorieEach() {
        return calorieEach;
    }

    public int getDeleteIcon() {
        return deleteIcon;
    }

    public String getKey() {
        return  key;
    }
}
