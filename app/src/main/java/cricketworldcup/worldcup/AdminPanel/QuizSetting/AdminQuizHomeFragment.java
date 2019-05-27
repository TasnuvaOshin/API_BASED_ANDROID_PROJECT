package cricketworldcup.worldcup.AdminPanel.QuizSetting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cricketworldcup.worldcup.R;


public class AdminQuizHomeFragment extends Fragment {

    Button question, answer;
    QuizSettingFragment quizSettingFragment;
    AnswerSettingFragment answerSettingFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_quiz_home, container, false);
           quizSettingFragment = new QuizSettingFragment();
           answerSettingFragment = new AnswerSettingFragment();

        question = view.findViewById(R.id.question);
        answer = view.findViewById(R.id.answer);

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(quizSettingFragment);
            }
        });


        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(answerSettingFragment);
            }
        });



        return view;
    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }
}
