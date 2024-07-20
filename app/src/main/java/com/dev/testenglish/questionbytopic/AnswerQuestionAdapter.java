package com.dev.testenglish.questionbytopic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.testenglish.R;
import com.dev.testenglish.databinding.ItemQuestionBinding;

public class AnswerQuestionAdapter extends RecyclerView.Adapter<AnswerQuestionAdapter.ViewHolder> {
    private char[] charList; // Mảng các ký tự sẽ được hiển thị trong RecyclerView
    private Context context; // Ngữ cảnh của ứng dụng
    private ItemClickListener mClickListener; // Đối tượng lắng nghe sự kiện click

    // Constructor khởi tạo adapter với mảng ký tự, ngữ cảnh và đối tượng lắng nghe sự kiện click
    public AnswerQuestionAdapter(char[] charList, Context context, ItemClickListener mClickListener) {
        this.charList = charList;
        this.context = context;
        this.mClickListener = mClickListener;
    }

    // Tạo ViewHolder mới khi RecyclerView cần
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context); // Tạo LayoutInflater từ ngữ cảnh
        View view = mInflater.inflate(R.layout.item_question, parent, false); // Inflate layout cho mỗi phần tử trong RecyclerView
        return new ViewHolder(view); // Trả về ViewHolder mới với view đã được inflate
    }

    // Gán dữ liệu vào ViewHolder tại vị trí được chỉ định
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.tvChar.setText(String.valueOf(charList[position])); // Gán ký tự cho TextView

        // Thiết lập sự kiện click cho TextView
        holder.binding.tvChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.binding.tvChar.getText().toString().trim().isEmpty()) { // Kiểm tra nếu TextView không rỗng
                    mClickListener.onItemClick(position, charList[position]); // Gọi phương thức onItemClick của ItemClickListener
                }
            }
        });
    }

    // Trả về số lượng phần tử trong mảng ký tự
    @Override
    public int getItemCount() {
        return charList.length;
    }

    // Lớp ViewHolder lưu trữ và tái chế các view khi chúng được cuộn khỏi màn hình
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemQuestionBinding binding; // Binding cho layout item_question

        ViewHolder(View itemView) {
            super(itemView);
            binding = ItemQuestionBinding.bind(itemView); // Khởi tạo binding với itemView
        }
    }

    // Giao diện để lắng nghe sự kiện click từ các phần tử trong RecyclerView
    public interface ItemClickListener {
        void onItemClick(int position, char charShow); // Phương thức xử lý sự kiện click
    }
}

