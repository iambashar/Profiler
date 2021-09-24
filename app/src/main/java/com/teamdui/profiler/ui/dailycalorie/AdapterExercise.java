package com.teamdui.profiler.ui.dailycalorie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;

import java.util.List;

import static com.teamdui.profiler.MainActivity.myRef;
import static com.teamdui.profiler.MainActivity.uid;

public class AdapterExercise extends RecyclerView.Adapter<AdapterExercise.ViewHolder> implements AdapterView.OnItemClickListener {

    private List<Exercise>exerciseList;
    public DailyExerciseFragment dailyExerciseFragment = new DailyExerciseFragment();
    public AdapterExercise(List<Exercise>exerciseList)
    {
        this.exerciseList = exerciseList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_exercise, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExercise.ViewHolder holder, int position) {
        String catName = exerciseList.get(position).getCatName();
        String timeEach = exerciseList.get(position).getTimeEach() ;
        int deleteIcon = exerciseList.get(position).getDeleteIcon();
        holder.catNameView.setText(catName);
        holder.timeEachView.setText(timeEach);
        holder.deleteIconView.setImageResource(deleteIcon);

        holder.deleteIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String k = null;
                for (int i=0; i<=position; i++)
                    k = exerciseList.get(i).getKey();
                myRef.child(uid).child("Exercise").child(k).removeValue();

                double x = exerciseList.get(position).getBurnHour();
                exerciseList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, exerciseList.size());
                dailyExerciseFragment.reduceTime(timeEach, x);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView catNameView;
        private TextView timeEachView;
        private ImageView deleteIconView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catNameView = itemView.findViewById(R.id.catInList);
            timeEachView = itemView.findViewById(R.id.timeInList);
            deleteIconView = itemView.findViewById(R.id.deleteIconInListex);
        }


    }
}
