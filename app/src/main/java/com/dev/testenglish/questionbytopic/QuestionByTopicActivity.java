package com.dev.testenglish.questionbytopic;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.dev.testenglish.Common;
import com.dev.testenglish.R;
import com.dev.testenglish.database.ObjectBox;
import com.dev.testenglish.databinding.ActivityQuestionByTopicBinding;
import com.dev.testenglish.model.TopicQuestions;
import com.dev.testenglish.model.TopicQuestions_;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.objectbox.Box;
import io.objectbox.query.Query;

public class QuestionByTopicActivity extends AppCompatActivity {
    ActivityQuestionByTopicBinding binding;
    private static final String FORMAT = "%02d:%02d";
    public static final String ID_TOPIC = "ID_TOPIC";
    public static final String NAME_TOPIC = "NAME_TOPIC";
    ArrayList<TopicQuestions> listQuestionByTopic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionByTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        Glide.with(this).load(R.drawable.ic_hourglass).into(binding.ivHourglass);

        Bundle bundle = getIntent().getExtras();
        long idTopic = (long) bundle.get(ID_TOPIC);
        String nameTopic = (String) bundle.get(NAME_TOPIC);
        //danh sách câu hỏi theo chủ đề
        Box<TopicQuestions> topicQuestionsBox = ObjectBox.get().boxFor(TopicQuestions.class);
        Query<TopicQuestions> query = topicQuestionsBox
                .query(TopicQuestions_.idTopic.equal(idTopic)).build();
       listQuestionByTopic = (ArrayList<TopicQuestions>) query.find();
        query.close();
        binding.txtTitle.setText(getString(R.string.topic, nameTopic));
        if (listQuestionByTopic.size() > 0) {
            initViewPager();
            createCountDownTime();
        } else {
            binding.second.setVisibility(View.GONE);
            binding.tvSubmit.setVisibility(View.GONE);
            binding.ivHourglass.setVisibility(View.GONE);
            Toast.makeText(QuestionByTopicActivity.this, getString(R.string.no_question), Toast.LENGTH_SHORT).show();
        }
        initListener();


    }

    private void initListener() {
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();

            }
        });

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(QuestionByTopicActivity.this)
                        .setTitle("Finish entry")
                        .setMessage("Are you sure you want to finish this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                finish();

                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void submit() {
        try {
            int point = 0;
            for (int i = 0; i < listQuestionByTopic.size(); i++) {
                if (listQuestionByTopic.get(i).answer.equals(listQuestionByTopic.get(i).myAnswer)) {
                    point += 10;
                }
            }
//            Toast.makeText(QuestionByTopicActivity.this, getString(R.string.my_point, String.valueOf(point)), Toast.LENGTH_SHORT).show();
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("Point!")
                    .setContentText(String.valueOf(point))
                    .setCustomImage(R.drawable.ic_rank)
                    .show();
            Gson gson = new Gson(); // Or use new GsonBuilder().create();

//            JSONObject jsonObject = new JSONObject(Common.readString(QuestionByTopicActivity.this,Common.HISTORY_POINT));

            Type listType = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            List<Integer> participantJsonList = new ArrayList<>();
            if (Common.readString(QuestionByTopicActivity.this, Common.HISTORY_POINT) != null && gson.fromJson(Common.readString(QuestionByTopicActivity.this, Common.HISTORY_POINT), listType) != null) {
                participantJsonList = gson.fromJson(Common.readString(QuestionByTopicActivity.this, Common.HISTORY_POINT), listType);
            }
            participantJsonList.add(point);
            Collections.sort(participantJsonList, Collections.reverseOrder());
            Common.writeString(QuestionByTopicActivity.this, Common.HISTORY_POINT, new Gson().toJson(participantJsonList));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initViewPager() {
        try {
            QuestionPagerAdapter adapterViewPager = new QuestionPagerAdapter(getSupportFragmentManager(), listQuestionByTopic);
            float scaleFactor = 0.85f;
//            binding.vpQuestion.setPageMargin(-35);
            binding.vpQuestion.setOffscreenPageLimit(2);
//
            binding.vpQuestion.setPageTransformer(true, (ViewPager.PageTransformer) new DepthPageTransformer());
            binding.vpQuestion.setAdapter(adapterViewPager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCountDownTime() {
        binding.second.setVisibility(View.VISIBLE);
        new CountDownTimer(180000, 1000) {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            public void onTick(long millisUntilFinished) {
                binding.second.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                submit();
            }
        }.start();
    }

}
