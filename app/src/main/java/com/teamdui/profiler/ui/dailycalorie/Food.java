package com.teamdui.profiler.ui.dailycalorie;

import android.provider.ContactsContract;
import android.widget.ImageView;

public class Food {
    String foodName;
    String calorieEach;
    int deleteIcon;

    Food(String foodName, String calorieEach, int deleteIcon)
    {
        this.foodName = foodName;
        this.calorieEach = calorieEach;
        this.deleteIcon = deleteIcon;
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
}
