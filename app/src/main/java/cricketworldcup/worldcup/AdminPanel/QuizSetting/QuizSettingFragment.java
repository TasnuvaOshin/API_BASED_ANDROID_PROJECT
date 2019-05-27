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


public class QuizSettingFragment extends Fragment {
    private EditText et_matchno, et_qusone, et_qusone_opone, et_qusone_optwo, et_qustwo, et_qustwo_opone, et_qustwo_optwo;
    private Button button;
    private DatabaseReference databaseReference, databaseReferenceLive;
    private AdminHomeFragment adminHomeFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_setting, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quiz_data");
        databaseReferenceLive = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        adminHomeFragment = new AdminHomeFragment();
        et_matchno = view.findViewById(R.id.quizsetting_matchno);
        et_qusone = view.findViewById(R.id.quizsetting_qusone);
        et_qusone_opone = view.findViewById(R.id.quizsetting_qusone_opone);
        et_qusone_optwo = view.findViewById(R.id.quizsetting_qusone_optwo);
        et_qustwo = view.findViewById(R.id.quizsetting_qustwo);
        et_qustwo_opone = view.findViewById(R.id.quizsetting_qustwo_opone);
        et_qustwo_optwo = view.findViewById(R.id.quizsetting_qustwo_optwo);

        button = view.findViewById(R.id.quizsetting_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(et_matchno.getText())) {
                    if (!TextUtils.isEmpty(et_qusone.getText())) {
                        if (!TextUtils.isEmpty(et_qustwo.getText())) {
                            final String matchno = et_matchno.getText().toString();
                            final String qusone = et_qusone.getText().toString();
                            final String qusone_opone = et_qusone_opone.getText().toString();
                            final String qusone_optwo = et_qusone_optwo.getText().toString();
                            String qustwo = et_qusone.getText().toString();
                            String qustwo_opone = et_qustwo_opone.getText().toString();
                            String qustwo_optwo = et_qustwo_optwo.getText().toString();


                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("matchno", matchno);
                            hashMap.put("qus_one", qusone);
                            hashMap.put("qus_one_opt_one", qusone_opone);
                            hashMap.put("qus_one_opt_two", qusone_optwo);
                            hashMap.put("qus_two", qustwo);
                            hashMap.put("qus_two_opt_one", qustwo_opone);
                            hashMap.put("qus_two_opt_two", qustwo_optwo);


                            databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {


                                    databaseReferenceLive.child("matchno").setValue(matchno);
                                    databaseReferenceLive.child("qus_one").child("qus").setValue(qusone);
                                    databaseReferenceLive.child("qus_one").child("opt_one").setValue(qusone_opone);
                                    databaseReferenceLive.child("qus_one").child("opt_two").setValue(qusone_optwo);
                                    databaseReferenceLive.child("qus_two").child("qus").setValue(qusone);
                                    databaseReferenceLive.child("qus_two").child("opt_one").setValue(qusone_opone);
                                    databaseReferenceLive.child("qus_two").child("opt_two").setValue(qusone_optwo);
                                    Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
                                    SetFragment(adminHomeFragment);
                                }
                            });


                        }


                    }


                } else {

                    Toast.makeText(getActivity(), "Please Fill Out The Form Correctly", Toast.LENGTH_SHORT).show();
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
