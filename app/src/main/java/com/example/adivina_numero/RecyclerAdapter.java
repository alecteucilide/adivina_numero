package com.example.adivina_numero;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    ArrayList<Match> arrayMatch = new ArrayList<Match>();

    public RecyclerAdapter(ArrayList<Match> arrayMatch) {
        this.arrayMatch = arrayMatch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(arrayMatch.get(position).getName());
        holder.tvScore.setText(String.valueOf(arrayMatch.get(position).getScore()));
        holder.tvTime.setText(String.valueOf(arrayMatch.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return arrayMatch.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvScore, tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvScore = itemView.findViewById(R.id.tvScore);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
