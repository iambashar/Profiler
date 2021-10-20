package com.teamdui.profiler.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teamdui.profiler.R;

import java.util.List;

public class adapterRecord extends RecyclerView.Adapter<adapterRecord.ViewHolder> {

    private List<rec> recList;
    public History history = new History();

    public adapterRecord(List<rec> recList) {
        this.recList = recList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int calEarn = recList.get(position).cal;
        double calBurn = recList.get(position).calburn;
        String date = recList.get(position).date;
        date = date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4);
        holder.dateView.setText(date);
        holder.calEarnView.setText(Integer.toString(calEarn));
        holder.calBurnView.setText(Integer.toString((int) calBurn));
        holder.netCalView.setText( ((calEarn - (int) calBurn) > 0 ? "+" : "") + Integer.toString(calEarn - (int) calBurn));
    }

    @Override
    public int getItemCount() {
        return recList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView;
        private TextView calEarnView;
        private TextView calBurnView;
        private TextView netCalView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.dateText);
            calEarnView = itemView.findViewById(R.id.calorieEarnText);
            calBurnView = itemView.findViewById(R.id.calorieBurnText);
            netCalView = itemView.findViewById(R.id.netCalorieText);
        }


    }
}





