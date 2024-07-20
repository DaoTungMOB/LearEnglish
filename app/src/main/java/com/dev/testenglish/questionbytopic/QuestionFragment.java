package com.dev.testenglish.questionbytopic;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dev.testenglish.R;
import com.dev.testenglish.databinding.FragmentQuestionBinding;
import com.dev.testenglish.model.TopicQuestions;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragment extends Fragment {
    // Store instance variables
    private int page;
    private TopicQuestions questions;
    FragmentQuestionBinding binding;
    char[] listCharMyAnswer;
    char[] listCharResult;
    AnswerQuestionAdapter  adapter;
    AnswerQuestionAdapter  adapterDefault;
//    private List<Integer> COLORS = Arrays.asList(R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5);

    // newInstance constructor for creating fragment with arguments
    public static QuestionFragment newInstance(int page, TopicQuestions questions) {
        QuestionFragment questionFragment = new QuestionFragment();
        questionFragment.page = page;
        questionFragment.questions = questions;
        return questionFragment;
    }


    // Inflate the view for the fragment based on layout XML
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        binding = FragmentQuestionBinding.bind(view);
        binding.tvQuestion.setText("Question "+(page+1)+": "+questions.question);

        if (questions.description !=null && !questions.description.equals("")) {
            binding.tvDescription.setText(questions.description);
            binding.tvDescription.setVisibility(View.VISIBLE);
            binding.tvTitleDescription.setVisibility(View.VISIBLE);
        }else {
            binding.tvDescription.setVisibility(View.GONE);
            binding.tvTitleDescription.setVisibility(View.GONE);
        }
//        binding.lnBackground.setBackgroundColor(getContext().getColor(R.color.color5));
        initRecyclerAnswer();
        initRecyclerMyAnswer();

        //support
        binding.ivSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvAnswer.setText(questions.answer);
            }
        });
        return view;
    }
    public char[] shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        System.out.println(output.toString());
        return output.toString().toCharArray();
    }
    private void initRecyclerMyAnswer() {
        listCharMyAnswer= new char[questions.answer.length()];
          adapter = new AnswerQuestionAdapter(listCharMyAnswer,getContext(), new AnswerQuestionAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int position,char topic) {

                int positionUpdate=0;
                for (int i = 0; i < listCharResult.length; i++) {
                    if (String.valueOf(listCharResult[i]).trim().isEmpty()){
                        positionUpdate= i;
                        break;
                    }
                }
                listCharResult[positionUpdate] = topic;
                adapterDefault.notifyItemChanged(positionUpdate);
                listCharMyAnswer[position]='\0';//set rỗng
                adapter.notifyItemChanged(position);
                questions.myAnswer = String.valueOf(listCharMyAnswer);
            }
        });
        binding.rvAnswer.setAdapter(adapter);
    }

    private void initRecyclerAnswer() {
        listCharResult= shuffle(questions.answer);
          adapterDefault = new AnswerQuestionAdapter(listCharResult,getContext(), new AnswerQuestionAdapter.ItemClickListener(){

            @Override
            public void onItemClick(int position,char topic) {
                int positionUpdate=0;
                for (int i = 0; i < listCharMyAnswer.length; i++) {
                    if (String.valueOf(listCharMyAnswer[i]).trim().isEmpty()){
                        positionUpdate= i;
                        break;
                    }
                }
                listCharMyAnswer[positionUpdate] = topic;
                adapter.notifyItemChanged(positionUpdate);

                listCharResult[position]='\0';//set rỗng
                adapterDefault.notifyItemChanged(position);


                questions.myAnswer = String.valueOf(listCharMyAnswer);

            }
        });
        binding.rvAnswerDefault.setAdapter(adapterDefault);
    }
}
