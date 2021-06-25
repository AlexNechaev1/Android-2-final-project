package com.example.android_2_final_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.android_2_final_project.Question;
import com.example.android_2_final_project.R;

public class QuestionFragment extends Fragment {

    private final static String QUESTION_KEY = "QUESTION";
    private final static String ANSWER_1_KEY = "ANSWER_1";
    private final static String ANSWER_2_KEY = "ANSWER_2";
    private final static String ANSWER_3_KEY = "ANSWER_3";

    private TextView questionTV;
    private RadioGroup radioGroup;

    public static QuestionFragment newInstance(Question question) {
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QUESTION_KEY, question.getQuestion());
        bundle.putString(ANSWER_1_KEY, question.getAnswersOne());
        bundle.putString(ANSWER_2_KEY, question.getAnswersTwo());
        bundle.putString(ANSWER_3_KEY, question.getAnswersThree());
        questionFragment.setArguments(bundle);
        return questionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

    }

    private void initViews(View view) {
        questionTV = view.findViewById(R.id.question_text_view);
        questionTV.setText(requireArguments().getString(QUESTION_KEY,""));
        radioGroup = view.findViewById(R.id.signup_question);
        ((RadioButton)radioGroup.getChildAt(0)).setText(requireArguments().getString(ANSWER_1_KEY,""));
        ((RadioButton)radioGroup.getChildAt(1)).setText(requireArguments().getString(ANSWER_2_KEY,""));
        ((RadioButton)radioGroup.getChildAt(2)).setText(requireArguments().getString(ANSWER_3_KEY,""));
    }
}
