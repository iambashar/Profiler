package com.teamdui.profiler.ui.dailycalorie;

public class Exercise {
    String catName;
    String timeEach;
    int deleteIcon;
    double burnHour;
    String key;

    public Exercise() {

    }

    public Exercise(String catName, String timeEach, int deleteIcon, double burnHour, String key) {
        this.catName = catName;
        this.timeEach = timeEach;
        this.deleteIcon = deleteIcon;
        this.burnHour = burnHour;
        this.key = key;
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

    public double getBurnHour() {
        return burnHour;
    }

    public String getKey() {
        return key;
    }
}
