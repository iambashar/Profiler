package com.teamdui.profiler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.teamdui.profiler.databinding.ActivityMainBinding;
import com.teamdui.profiler.ui.authentication.LoginActivity;
import com.teamdui.profiler.ui.dailycalorie.Exercise;
import com.teamdui.profiler.ui.dailycalorie.Food;
import com.teamdui.profiler.ui.history.date;
import com.teamdui.profiler.ui.history.set;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.teamdui.profiler.ui.dailycalorie.DailyExerciseFragment.exerciseList;
import static com.teamdui.profiler.ui.dailycalorie.DailyMealFragment.foodList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView bottomNavView;
    private NavigationView sideNavView;
    private AppBarConfiguration mAppBarConfiguration;
    private CoordinatorLayout contentView;
    private DrawerLayout drawer;
    private static final float END_SCALE = 0.85f;
    private Button logoutButton;
    public static volatile String uri;
    public int scal = 0, sexr = 0, swat = 0;
    private DatabaseReference urlRef;

    public static FirebaseAuth mAuth;
    public static DatabaseReference myRef;
    public static String date;
    public static String uid;
    public static volatile int calorieDaily = 0;
    public static volatile int calorieGoal = 0;
    public static volatile int glassDaily = 0;
    public static volatile int glassGoal = 0;
    public static volatile int exerciseDaily = 0;
    public static volatile int exerciseGoal = 0;
    public static volatile double netCalorie = 0;
    public static volatile double burnedCalorie = 0;
    public static volatile byte[] bytesProfileImage;
    public static View headerView;
    public static volatile String fullName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        final String dbUrl = "https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/";

        uid = user.getUid();
        LocalDate todayDate = LocalDate.now();
        date = todayDate.toString();

        myRef = FirebaseDatabase.getInstance(dbUrl).getReference("userid");
        myRef.keepSynced(true);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                setVariables();
                dbUpdate();
            }
        });
        thread.start();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNavView = findViewById(R.id.nav_view);
        sideNavView = findViewById(R.id.side_nav_view);
        contentView = findViewById(R.id.content_view);
        drawer = binding.drawerLayout;

        urlRef = FirebaseDatabase.getInstance(dbUrl).getReference("url");
        urlRef.keepSynced(true);
        urlRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                uri = (String) snapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        headerView = sideNavView.inflateHeaderView(R.layout.nav_header_main);

        logoutButton = headerView.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dailycalorie,
                R.id.navigation_goaltracker,
                R.id.navigation_bmi,
                R.id.navigation_camera,
                R.id.nav_Profile,
                R.id.nav_help,
                R.id.profileEdit,
                R.id.nav_history
        ).setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(sideNavView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void logoutUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
    }

    public static void setVariables() {

        try {
            myRef.child(uid).child("Meal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    GenericTypeIndicator<HashMap<String, Food>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Food>>() {
                    };
                    Map<String, Food> objectHashMap = snapshot.getValue(objectsGTypeInd);
                    if (objectHashMap != null)
                        foodList = new ArrayList<Food>(objectHashMap.values());
                    else
                        foodList = null;
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            //
        }

        try {
            myRef.child(uid).child("Exercise").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    GenericTypeIndicator<HashMap<String, Exercise>> objectsGTypeInd = new GenericTypeIndicator<HashMap<String, Exercise>>() {
                    };
                    Map<String, Exercise> objectHashMap = snapshot.getValue(objectsGTypeInd);
                    if (objectHashMap != null)
                        exerciseList = new ArrayList<Exercise>(objectHashMap.values());
                    else
                        exerciseList = null;
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            //
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void dbUpdate() {
        LocalDate todayDate = LocalDate.now();
        String date2 = todayDate.minusDays(1).toString();

        myRef.child(uid).child("date").child(date2).child("set").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    set st = snapshot.getValue(set.class);
                    scal = st.cal;
                    sexr = st.exr;
                    swat = st.wat;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        myRef.child(uid).child("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean flag = true;
                    for (DataSnapshot du : snapshot.getChildren()) {
                        if (date.contentEquals(du.getKey())) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        date data = new date();
                        data.progress.cal = 0;
                        data.progress.exr = 0;
                        data.progress.wat = 0;
                        data.progress.calburn = 0;
                        data.set.cal = scal;
                        data.set.exr = sexr;
                        data.set.wat = swat;
                        myRef.child(uid).child("date").child(date).setValue(data);
                        myRef.child(uid).child("Meal").removeValue();
                        myRef.child(uid).child("Exercise").removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}

