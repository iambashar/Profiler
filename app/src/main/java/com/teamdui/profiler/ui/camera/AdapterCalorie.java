package com.teamdui.profiler.ui.camera;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;
import com.teamdui.profiler.ui.dailycalorie.Food;

import java.util.List;

public class AdapterCalorie extends RecyclerView.Adapter<AdapterCalorie.ViewHolder> implements AdapterView.OnItemClickListener {

    private List<Food>foodList;
    public ImageFragment imageFragment = new ImageFragment();
    public AdapterCalorie(List<Food>foodList)
    {
        this.foodList = foodList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_food, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCalorie.ViewHolder holder, int position) {
        String foodName = foodList.get(position).getFoodName();
        String calorieEach = foodList.get(position).getCalorieEach() + " cal";
        int deleteIcon = foodList.get(position).getDeleteIcon();
        holder.foodNameView.setText(foodName);
        holder.calorieEachView.setText(calorieEach);
        holder.deleteIconView.setImageResource(deleteIcon);

        holder.deleteIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, foodList.size());
                imageFragment.reduceCalorie(calorieEach);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


    }
}
