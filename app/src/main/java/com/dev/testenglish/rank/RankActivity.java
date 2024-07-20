package com.dev.testenglish.rank;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.testenglish.Common;
import com.dev.testenglish.databinding.ActivityRankBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {
    RankAdapter adapter;
    ArrayList<Integer> listRank;
    ActivityRankBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            binding = ActivityRankBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            initRecyclerView();
            binding.ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initRecyclerView() {
        try {
            Gson gson = new Gson(); // Or use new GsonBuilder().create();
            Type listType = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            listRank = new ArrayList<>();
            if (Common.readString(RankActivity.this, Common.HISTORY_POINT) != null && gson.fromJson(Common.readString(RankActivity.this, Common.HISTORY_POINT), listType) != null) {
                listRank = gson.fromJson(Common.readString(RankActivity.this, Common.HISTORY_POINT), listType);
            }
            adapter = new RankAdapter(listRank, this);
            binding.rvRank.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
