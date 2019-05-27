package cricketworldcup.worldcup.AdminPanel.QuizSetting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cricketworldcup.worldcup.AdminPanel.AdminHomeFragment;
import cricketworldcup.worldcup.R;

public class AnswerSettingFragment extends Fragment {
    private Button button;
    private EditText answerone, answertwo, matchno;
    private DatabaseReference databaseReference, databaseReferenceLive;
    private AdminHomeFragment adminHomeFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer_setting, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quiz_Answer_data");
        databaseReferenceLive = FirebaseDatabase.getInstance().getReference().child("live_quiz_answer");
        adminHomeFragment = new AdminHomeFragment();
        button = view.findViewById(R.id.answer_submit);
        answerone = view.findViewById(R.id.answer_one);
        answertwo = view.findViewById(R.id.answer_two);
        matchno = view.findViewById(R.id.answer_matchno);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(answerone.getText())) {

                    if (!TextUtils.isEmpty(answertwo.getText())) {

                        final String one = answerone.getText().toString();
                        final String two = answertwo.getText().toString();
                        final String match = matchno.getText().toString();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("qus_one_ans", one);
                        hashMap.put("qus_two_ans", two);
                        hashMap.put("matchno", match);


                        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                databaseReferenceLive.child("qus_one").setValue(one);
                                databaseReferenceLive.child("qus_two").setValue(two);
                                databaseReferenceLive.child("matchno").setValue(match);

                                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                                SetFragment(adminHomeFragment);

                            }
                        });


                    }
                } else {
                    Toast.makeText(getActivity(), "Enter Data Correctly", Toast.LENGTH_SHORT).show();
                }
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
