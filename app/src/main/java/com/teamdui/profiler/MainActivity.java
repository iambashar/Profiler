package com.teamdui.profiler;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.teamdui.profiler.ui.dailycalorie.Exercise;
import com.teamdui.profiler.ui.dailycalorie.Food;
import com.teamdui.profiler.ui.login.LoginActivity;
import com.teamdui.profiler.ui.profile.ProfileData;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
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
    public static CircleImageView profileImage;
    public static volatile String uri;
    public int scal = 0, sexr = 0, swat = 0;
    private DatabaseReference urlRef;

    public static FirebaseAuth mAuth;
    public static DatabaseReference myRef;
    public static String date;
    public static String uid;
    public static int calorieDaily = 0;
    public static int calorieGoal = 0;
    public static int glassDaily = 0;
    public static int glassGoal = 0;
    public static int exerciseDaily = 0;
    public static int exerciseGoal = 0;
    public static double netCalorie = 0;
    public static double burnedCalorie = 0;
    public static byte[] bytesProfileImage;
    public static ProfileData profileData;
    public static View headerView;
    public static String firstName, lastName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        final String dbUrl = "https://profiler-280f7-default-rtdb.asia-southeast1.firebasedatabase.app/";

        uid = user.getUid();
        LocalDate todayDate = LocalDate.now();
        date = todayDate.toString();


        myRef = FirebaseDatabase.getInstance(dbUrl).getReference("userid");

        myRef.keepSynced(true);


        dbUpdate();
        setVariables();

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

        profileImage = headerView.findViewById(R.id.profileImg);
        setpropic();

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
                R.id.nav_history,
                R.id.profileEdit
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

    public static void setpropic(){
        if (bytesProfileImage != null)
            profileImage.setImageBitmap(BitmapFactory.decodeByteArray(bytesProfileImage, 0, bytesProfileImage.length));
    }

    private void logoutUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent logoutIntent = new Intent(getApplicationContext(), LoginActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(logoutIntent);
    }

    public static void setVariables() {
        try{
            myRef.child(uid).child("profile").child("Image").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        bytesProfileImage = Base64.getDecoder().decode(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
                });
            }catch (Exception e){
                bytesProfileImage = new byte[0];
            }

        try{
            myRef.child(uid).child("profile").child("fname").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        firstName = (String) snapshot.getValue();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            firstName = "";
        }
        try{
            myRef.child(uid).child("profile").child("lname").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        lastName = (String) snapshot.getValue();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            lastName = "";
        }

        try {
            myRef.child(uid).child("date").child(date).child("progress").child("cal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        calorieDaily = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            calorieDaily = 0;
        }

        try {
            myRef.child(uid).child("date").child(date).child("set").child("cal").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        calorieGoal = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            calorieGoal = 0;
        }

        try {
            myRef.child(uid).child("date").child(date).child("progress").child("wat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        glassDaily = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            glassDaily = 0;
        }

        try {
            myRef.child(uid).child("date").child(date).child("set").child("wat").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        glassGoal = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        } catch (Exception e) {
            glassGoal = 0;
        }

        try {
            myRef.child(uid).child("date").child(date).child("progress").child("exr").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        exerciseDaily = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            exerciseDaily = 0;
        }

        try {
            myRef.child(uid).child("date").child(date).child("set").child("exr").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        exerciseGoal = Integer.parseInt(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            exerciseGoal = 0;
        }

        try {
            myRef.child(uid).child("calburn").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        burnedCalorie = Double.parseDouble(String.valueOf(snapshot.getValue()));
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            burnedCalorie = 0;
        }

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

        try {
            myRef.child(uid).child("profile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        profileData = dataSnapshot.getValue(ProfileData.class);
                        TextView name = headerView.findViewById(R.id.fullName);
                        name.setText((profileData.fname + " " + profileData.lname));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError databaseError) {

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

        myRef.child(uid).child("date").child(date2).child("set").child("cal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    scal = Integer.parseInt(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        myRef.child(uid).child("date").child(date2).child("set").child("exr").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    sexr = Integer.parseInt(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        myRef.child(uid).child("date").child(date2).child("set").child("wat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    swat = Integer.parseInt(String.valueOf(snapshot.getValue()));
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
                        if (date.contentEquals(du.getKey().toString())) {
                            flag = false;
                        }
                    }
                    if (flag) {
                        myRef.child(uid).child("date").child(date).child("set").child("cal").setValue(scal);
                        myRef.child(uid).child("date").child(date).child("set").child("exr").setValue(sexr);
                        myRef.child(uid).child("date").child(date).child("set").child("wat").setValue(swat);
                        myRef.child(uid).child("calburn").setValue(0);
                        myRef.child(uid).child("Meal").removeValue();
                        myRef.child(uid).child("Exercise").removeValue();

                        myRef.child(uid).child("date").child(date).child("progress").child("cal").setValue(0);
                        myRef.child(uid).child("date").child(date).child("progress").child("exr").setValue(0);
                        myRef.child(uid).child("date").child(date).child("progress").child("wat").setValue(0);
                        myRef.child(uid).child("date").child(date).child("progress").child("calburn").setValue(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        } );
    }
}

