package com.dev.testenglish.testbytopic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dev.testenglish.R;
import com.dev.testenglish.database.ObjectBox;
import com.dev.testenglish.databinding.ActivityTopicBinding;
import com.dev.testenglish.model.Topic;
import com.dev.testenglish.questionbytopic.QuestionByTopicActivity;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;

import io.objectbox.Box;

public class TopicActivity extends AppCompatActivity {
    TopicAdapter adapter;
    ArrayList<Topic> listTopic;
    ActivityTopicBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Box<Topic> topicBox = ObjectBox.get().boxFor(Topic.class);
        listTopic = (ArrayList<Topic>) topicBox.getAll();
        initRecyclerView();

        initListTopic();

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListTopic() {
        try {
//
            for (Topic topic : listTopic) {
                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .normalImageRes(R.drawable.ic_add)
                        .normalText(topic.name);
                binding.bmb.addBuilder(builder);
            }


            binding.bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
                    Toast.makeText(TopicActivity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onBackgroundClick() {

                }

                @Override
                public void onBoomWillHide() {

                }

                @Override
                public void onBoomDidHide() {

                }

                @Override
                public void onBoomWillShow() {

                }

                @Override
                public void onBoomDidShow() {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        try {

            binding.rvTopic.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new TopicAdapter(listTopic, this, new TopicAdapter.ItemClickListener() {

                @Override
                public void onItemClick(Topic topic) {


                    Intent intent = new Intent(TopicActivity.this, QuestionByTopicActivity.class);
                    intent.putExtra(QuestionByTopicActivity.ID_TOPIC, topic.id);
                    intent.putExtra(QuestionByTopicActivity.NAME_TOPIC, topic.name);
                    startActivity(intent);

                }
            });
            binding.rvTopic.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
