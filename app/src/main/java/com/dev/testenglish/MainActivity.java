package com.dev.testenglish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.testenglish.addquestionbytopic.AddQuestionActivity;
import com.dev.testenglish.database.ObjectBox;
import com.dev.testenglish.databinding.ActivityMainBinding;
import com.dev.testenglish.login.LoginActivity;
import com.dev.testenglish.model.Topic;
import com.dev.testenglish.model.TopicQuestions;
import com.dev.testenglish.questionbytopic.QuestionByTopicActivity;
import com.dev.testenglish.rank.RankActivity;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;

import io.objectbox.Box;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<Integer> listIcon = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Kiểm tra xem cơ sở dữ liệu đã được tạo chưa. Nếu chưa, tạo cơ sở dữ liệu.
        if (!Common.getBoolean(MainActivity.this, Common.CREATE_DATABASE)) {
            initDatabase();
            Common.putBoolean(MainActivity.this, Common.CREATE_DATABASE, true);
        }
        // Thêm các biểu tượng vào danh sách
        listIcon.add(R.drawable.dolphin);
        listIcon.add(R.drawable.bee);
        listIcon.add(R.drawable.horse);
        listIcon.add(R.drawable.owl);
        listIcon.add(R.drawable.peacock);
        initListener();
        // Khởi tạo danh sách các chủ đề
        initListTopic();
    }

    private void initListTopic() {
        try {
            Box<Topic> topicBox = ObjectBox.get().boxFor(Topic.class);
            ArrayList<Topic> listTopic = (ArrayList<Topic>) topicBox.getAll();

            //Tạo menu 5 chủ để
            binding.bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_1);
            binding.bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_1);

            //danh sách item cũng chỉ bằng 5 chủ đề vừa tạo
            for (int i = 0; i < listTopic.size(); i++) {
                TextInsideCircleButton.Builder builder = new TextInsideCircleButton.Builder()
                        .normalImageRes(listIcon.get(i))
                        .normalText(listTopic.get(i).name);
                binding.bmb.addBuilder(builder);
            }
            // Thiết lập sự kiện lắng nghe khi một item trong menu được chọn
            binding.bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
//                    Toast.makeText(MainActivity.this, String.valueOf(index), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, QuestionByTopicActivity.class);
                    intent.putExtra(QuestionByTopicActivity.ID_TOPIC, listTopic.get(index).id);
                    intent.putExtra(QuestionByTopicActivity.NAME_TOPIC, listTopic.get(index).name);
                    startActivity(intent);
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

    private void initListener() {

        // Thiết lập sự kiện lắng nghe cho nút Rank
        binding.lnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                startActivity(intent);
            }
        });
        // Thiết lập sự kiện lắng nghe cho nút Add Question
        binding.lnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });

        binding.lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Common.putBoolean(MainActivity.this, Common.IS_LOGIN, false);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void initDatabase() {
        // Lấy hộp (box) cho thực thể Topic và TopicQuestions từ ObjectBox
        Box<Topic> topicBox = ObjectBox.get().boxFor(Topic.class);
        Box<TopicQuestions> topicQuestionsBox = ObjectBox.get().boxFor(TopicQuestions.class);

        //chủ đề thời tiết
        Topic topicCapital = new Topic();
        topicCapital.name = getString(R.string.weather);
        topicBox.put(topicCapital);

        //chủ đề âm nhạc
        Topic topicMusic = new Topic();
        topicMusic.name = getString(R.string.music);
        topicBox.put(topicMusic);

        //chủ đề gia đình
        Topic topicFamily = new Topic();
        topicFamily.name = getString(R.string.family);
        topicBox.put(topicFamily);

        //chủ đề động vật
        Topic topicAnimal = new Topic();
        topicAnimal.name = getString(R.string.animal);
        topicBox.put(topicAnimal);

        //chủ đề khác
        Topic topicOther = new Topic();
        topicOther.name = getString(R.string.other);
        topicBox.put(topicOther);

        TopicQuestions topicQuestions11 = new TopicQuestions();
        topicQuestions11.question = getString(R.string.question11);
        topicQuestions11.answer = getString(R.string.answer11);
        topicQuestions11.idTopic = topicAnimal.id;
        topicQuestionsBox.put(topicQuestions11);

        TopicQuestions topicQuestions12 = new TopicQuestions();
        topicQuestions12.question = getString(R.string.question12);
        topicQuestions12.answer = getString(R.string.answer12);
        topicQuestions12.idTopic = topicAnimal.id;
        topicQuestionsBox.put(topicQuestions12);


        TopicQuestions topicQuestions1 = new TopicQuestions();
        topicQuestions1.question = getString(R.string.question1);
        topicQuestions1.answer = getString(R.string.answer1);
        topicQuestions1.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions1);

        TopicQuestions topicQuestions2 = new TopicQuestions();
        topicQuestions2.question = getString(R.string.question2);
        topicQuestions2.answer = getString(R.string.answer2);
        topicQuestions2.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions2);

        TopicQuestions topicQuestions3 = new TopicQuestions();
        topicQuestions3.question = getString(R.string.question3);
        topicQuestions3.answer = getString(R.string.answer3);
        topicQuestions3.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions3);

        TopicQuestions topicQuestions4 = new TopicQuestions();
        topicQuestions4.question = getString(R.string.question4);
        topicQuestions4.answer = getString(R.string.answer4);
        topicQuestions4.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions4);

        TopicQuestions topicQuestions5 = new TopicQuestions();
        topicQuestions5.question = getString(R.string.question5);
        topicQuestions5.answer = getString(R.string.answer5);
        topicQuestions5.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions5);

        TopicQuestions topicQuestions6 = new TopicQuestions();
        topicQuestions6.question = getString(R.string.question6);
        topicQuestions6.answer = getString(R.string.answer6);
        topicQuestions6.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions6);

        TopicQuestions topicQuestions7 = new TopicQuestions();
        topicQuestions7.question = getString(R.string.question7);
        topicQuestions7.answer = getString(R.string.answer7);
        topicQuestions7.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions7);

        TopicQuestions topicQuestions8 = new TopicQuestions();
        topicQuestions8.question = getString(R.string.question8);
        topicQuestions8.answer = getString(R.string.answer8);
        topicQuestions8.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions8);

        TopicQuestions topicQuestions9 = new TopicQuestions();
        topicQuestions9.question = getString(R.string.question9);
        topicQuestions9.answer = getString(R.string.answer9);
        topicQuestions9.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions9);

        TopicQuestions topicQuestions10 = new TopicQuestions();
        topicQuestions10.question = getString(R.string.question10);
        topicQuestions10.answer = getString(R.string.answer10);
        topicQuestions10.idTopic = topicCapital.id;
        topicQuestionsBox.put(topicQuestions10);

    }


}
