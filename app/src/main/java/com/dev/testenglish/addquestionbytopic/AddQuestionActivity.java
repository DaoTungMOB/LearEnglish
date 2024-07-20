package com.dev.testenglish.addquestionbytopic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dev.testenglish.R;
import com.dev.testenglish.database.ObjectBox;
import com.dev.testenglish.databinding.ActivityAddQuestionBinding;
import com.dev.testenglish.model.Topic;
import com.dev.testenglish.model.TopicQuestions;

import java.util.ArrayList;

import io.objectbox.Box;

public class AddQuestionActivity extends AppCompatActivity {
    ArrayList<Topic> listTopic; // Danh sách các chủ đề
    ActivityAddQuestionBinding binding; // Biến để liên kết với file XML của Activity
    int positionTopicSelect = 0; // Vị trí của chủ đề được chọn

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuestionBinding.inflate(getLayoutInflater()); // Khởi tạo binding
        setContentView(binding.getRoot()); // Thiết lập giao diện từ binding

        Box<Topic> topicBox = ObjectBox.get().boxFor(Topic.class); // Lấy Box chứa các đối tượng Topic
        listTopic = (ArrayList<Topic>) topicBox.getAll(); // Lấy tất cả các chủ đề và lưu vào listTopic
        initSpinner(); // Khởi tạo Spinner

        // Sự kiện khi bấm nút quay lại
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng Activity hiện tại
            }
        });

        // Sự kiện khi bấm nút thêm câu hỏi
        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu các trường câu hỏi và trả lời không rỗng
                if (!binding.edQuestion.getText().toString().trim().isEmpty()
                        && !binding.edAnswer.getText().toString().trim().isEmpty()) {
                    Box<TopicQuestions> topicQuestionsBox = ObjectBox.get().boxFor(TopicQuestions.class); // Lấy Box chứa các đối tượng TopicQuestions

                    // Tạo đối tượng TopicQuestions mới và thiết lập các thuộc tính
                    TopicQuestions topicQuestions11 = new TopicQuestions();
                    topicQuestions11.question = binding.edQuestion.getText().toString();
                    topicQuestions11.answer = binding.edAnswer.getText().toString();
                    topicQuestions11.description = binding.edDescription.getText().toString();
                    topicQuestions11.idTopic = listTopic.get(positionTopicSelect).id;
                    topicQuestionsBox.put(topicQuestions11); // Lưu đối tượng vào Box

                    // Hiển thị thông báo thêm thành công
                    Toast.makeText(AddQuestionActivity.this, getString(R.string.add_success), Toast.LENGTH_SHORT).show();
                    binding.edQuestion.getText().clear(); // Xóa nội dung của trường câu hỏi
                    binding.edAnswer.getText().clear(); // Xóa nội dung của trường trả lời
                } else {
                    // Hiển thị cảnh báo nếu các trường bắt buộc rỗng
                    Toast.makeText(AddQuestionActivity.this, getString(R.string.warning_add), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSpinner() {
        try {
            // Tạo adapter cho Spinner
            CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(this, listTopic);
            binding.spinnerTopic.setAdapter(spinnerAdapter); // Thiết lập adapter cho Spinner

            // Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
            binding.spinnerTopic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    // đối số position là vị trí phần tử trong list
//                    String msg = "position :" + position + " value :" + list.get(position);
//                    Toast.makeText(SpinnerActivity.this, msg, Toast.LENGTH_SHORT).show();
                    positionTopicSelect = position; // Lưu lại vị trí chủ đề được chọn
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
//                    Toast.makeText(SpinnerActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi nếu có
        }
    }
}