package com.teamdui.profiler;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DeviceTests {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.teamdui.profiler", appContext.getPackageName());
    }

    @Test
    public void firebaseInstanceCheck(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        assertNotEquals(null, mAuth);
    }

    @Test
    public void firebaseDatabaseReferenceCheck()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("userid");
        assertNotEquals(null, ref);
    }

    @Test
    public void PasswordRegexCheck()
    {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Pattern p = Pattern.compile(appContext.getString(R.string.password_regex));

        boolean matchTrue = p.matcher("Abcdef12").matches();
        boolean matchFalse = p.matcher("abcdeab").matches();

        assertTrue(matchTrue);
        assertFalse(matchFalse);
    }



}