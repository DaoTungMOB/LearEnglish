package com.dev.testenglish.rank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.testenglish.R;
import com.dev.testenglish.databinding.ItemRankBinding;
import com.dev.testenglish.databinding.ItemTopicBinding;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<Integer> listRank;
    private Context context;

    public RankAdapter(List<Integer> topicList, Context context) {
        this.listRank = topicList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_rank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvPoint.setText(context.getString(R.string.top_rank,String.valueOf(listRank.get(position))));
        if (position == 0)// top1
            holder.binding.ivPoint.setImageResource(R.drawable.ic_first);
        else if (position == 1) // top 2
            holder.binding.ivPoint.setImageResource(R.drawable.ic_second);
        else
            holder.binding.ivPoint.setImageResource(R.drawable.ic_third);


    }

    @Override
    public int getItemCount() {
        return listRank.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        ItemRankBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = ItemRankBinding.bind(itemView);


        }


    }

}

