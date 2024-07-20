package com.dev.testenglish.questionbytopic;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dev.testenglish.model.TopicQuestions;

import java.util.ArrayList;

class QuestionPagerAdapter extends FragmentStatePagerAdapter{
    private Context mContext;
    private ArrayList<TopicQuestions> list;

    public QuestionPagerAdapter(@NonNull FragmentManager fm,ArrayList<TopicQuestions> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(position, list.get(position));
    }

}
