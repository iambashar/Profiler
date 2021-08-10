package com.teamdui.profiler.ui.dailycalorie;

import android.provider.ContactsContract;
import android.widget.ImageView;

public class Exercise {
    String catName;
    String timeEach;
    int deleteIcon;
    double burnHour;

    Exercise(String catName, String timeEach, int deleteIcon, double burnHour)
    {
        this.catName = catName;
        this.timeEach = timeEach;
        this.deleteIcon = deleteIcon;
        this.burnHour = burnHour;
    }

    public String getCatName() {
        return catName;
    }

    public String getTimeEach() {
        return timeEach;
    }

    public int getDeleteIcon() {
        return deleteIcon;
    }

    public double getBurnHour()
    {
        return burnHour;
    }
}
