package com.teamdui.profiler;

import org.junit.Test;
import static org.junit.Assert.*;

import android.app.Activity;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.teamdui.profiler.ui.backend.Persistor;
import com.teamdui.profiler.ui.bmicalculator.BMIFragment;

import java.util.regex.Pattern;


public class UnitTests {
    @Test
    public void BMI_CalculatorCorrect()
    {
        float bmi = BMIFragment.GetBMI(65f, 1.65f);
        assertEquals( 23.9f, bmi ,0.5f);
    }



}
