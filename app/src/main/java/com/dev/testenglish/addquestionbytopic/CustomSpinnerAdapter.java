package com.dev.testenglish.addquestionbytopic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.dev.testenglish.R;
import com.dev.testenglish.model.Topic;

import java.util.List;

class CustomSpinnerAdapter extends BaseAdapter {

    private List<Topic> list; // Danh sách các chủ đề sẽ được hiển thị trong Spinner

    private Context context; // Ngữ cảnh của ứng dụng

    // Constructor để khởi tạo adapter với ngữ cảnh và danh sách chủ đề
    public CustomSpinnerAdapter(Context context, List<Topic> list) {
        this.context = context;
        this.list = list;
    }

    // Trả về số lượng phần tử trong danh sách
    @Override
    public int getCount() {
        if (this.list == null) { // Nếu danh sách rỗng thì trả về 0
            return 0;
        }
        return this.list.size(); // Trả về số lượng chủ đề trong danh sách
    }

    // Trả về đối tượng ở vị trí position trong danh sách
    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    // Trả về ID của phần tử ở vị trí position
    @Override
    public long getItemId(int position) {
        Topic language = (Topic) this.getItem(position); // Lấy chủ đề tại vị trí position
        return language.id; // Trả về ID của chủ đề
        // return position; (Return position if you need).
    }

    // Phương thức getView để tạo và trả về View hiển thị phần tử tại vị trí position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = LayoutInflater.from(context); // Tạo một LayoutInflater từ ngữ cảnh
        View view = mInflater.inflate(R.layout.item_topic_select, parent, false); // Inflate layout cho mỗi phần tử trong Spinner
        AppCompatTextView label = (AppCompatTextView) view.findViewById(R.id.tvTopicName); // Lấy TextView từ layout
        label.setText(list.get(position).name); // Thiết lập tên chủ đề cho TextView
        return view; // Trả về View đã được thiết lập
    }
}