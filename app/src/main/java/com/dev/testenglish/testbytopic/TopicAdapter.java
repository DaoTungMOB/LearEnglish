package com.dev.testenglish.testbytopic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.testenglish.R;
import com.dev.testenglish.databinding.ItemTopicBinding;
import com.dev.testenglish.model.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private List<Topic> topicList;
    private Context context;
    private ItemClickListener mClickListener;

    public TopicAdapter(List<Topic> topicList, Context context, ItemClickListener mClickListener) {
        this.topicList = topicList;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater=LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.item_topic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvTopicName.setText(topicList.get(position).name);
        holder.binding.tvTopicName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(topicList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        ItemTopicBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = ItemTopicBinding.bind(itemView);


        }




    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(Topic topic);
    }
}

