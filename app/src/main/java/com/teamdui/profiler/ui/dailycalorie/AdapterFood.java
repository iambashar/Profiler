package com.teamdui.profiler.ui.dailycalorie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {

    private List<Food>foodList;
    public AdapterFood(List<Food>foodList)
    {
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public AdapterFood.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterFood.ViewHolder holder, int position) {
        String foodName = foodList.get(position).getFoodName();
        String calorieEach = foodList.get(position).getCalorieEach();
        int deleteIcon = foodList.get(position).getDeleteIcon();
        holder.setData(foodName, calorieEach, deleteIcon);
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView foodNameView;
        private TextView calorieEachView;
        private ImageView deleteIconView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameView = itemView.findViewById(R.id.foodInList);
            calorieEachView = itemView.findViewById(R.id.calorieInList);
            deleteIconView = itemView.findViewById(R.id.deleteIconInList);
        }

        public void setData(String foodName, String calorieEach, int deleteIcon) {
            foodNameView.setText(foodName);
            calorieEachView.setText(calorieEach);
            deleteIconView.setImageResource(deleteIcon);
        }
    }
}
