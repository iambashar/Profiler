package com.teamdui.profiler.ui.History;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;
import static com.teamdui.profiler.ui.History.History.r;

public class dbquery {

    dbquery(){
        try{
            myRef.child(uid).child("date").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                        for(DataSnapshot myDataSnapshot : snapshot.getChildren())
                        {
                            date dataPoint = myDataSnapshot.getValue(date.class);//create datapoint object
                            r.add(new rec(dataPoint.set.cal, dataPoint.set.calburn, myDataSnapshot.getKey()));
                        }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }catch (Exception e){

        })
    }
}
