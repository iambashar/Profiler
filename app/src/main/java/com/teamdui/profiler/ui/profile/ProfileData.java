package com.teamdui.profiler.ui.profile;

import java.util.Date;

public class ProfileData {
    public String fname;
    public String lname;

    public Date dob;    // yes ik this is deprecated, idc

    public int heightFeet;
    public int heightInches;

    public double weight;

    public ProfileData() {
        fname = "";
        lname = "";
        dob = new Date();
        heightFeet = 0;
        heightInches = 0;
    }
}
