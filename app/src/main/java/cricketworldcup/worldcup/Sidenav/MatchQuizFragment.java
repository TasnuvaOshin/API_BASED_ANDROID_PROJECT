package cricketworldcup.worldcup.Sidenav;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Utility.ValidationProcess;

public class MatchQuizFragment extends Fragment {


    private RadioGroup qusOneGroup;
    private RadioGroup qustwoGroup;
    private RadioButton qusOneOptOne;
    private RadioButton qusOneOptTwo;
    private RadioButton qusTwoOptOne;
    private RadioButton qusTwoOptTwo;
    private TextView qusone, qustwo;
    private RelativeLayout relativeLayout, showError, quizSection;
    private DatabaseReference databaseReference;
    private View view;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceUserSubmit;
    private Button materialButton;
    private String currenUserId, currentUserNumber;
    private ImageView banner;
    private HashMap<String, String> hashMap;
  private   RadioButton radioButtonone,radioButtontwo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_match_quiz, container, false);

        banner = view.findViewById(R.id.iv_banner_ads);
        ShowAds();
        relativeLayout = view.findViewById(R.id.root);
        firebaseAuth = FirebaseAuth.getInstance();
       currenUserId = firebaseAuth.getCurrentUser().getUid();
       // currenUserId = "HQMCmakTYZd1rNbhW5P84F5hVGn1";

        //CHANGE IT HERE
      //  currentUserNumber = firebaseAuth.getCurrentUser().getPhoneNumber();
        databaseReferenceUserSubmit = FirebaseDatabase.getInstance().getReference().child("User_Quiz_Answer");
        //Toast.makeText(getActivity(), "" + currenUserId, Toast.LENGTH_SHORT).show();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        quizSection = view.findViewById(R.id.quiz_section);
        qusOneGroup = view.findViewById(R.id.radioGroup1);
        qustwoGroup = view.findViewById(R.id.radioGroup2);
        qusOneOptOne = view.findViewById(R.id.rb_qus_one_op_one);
        qusOneOptTwo = view.findViewById(R.id.rb_qus_one_op_two);
        qusTwoOptOne = view.findViewById(R.id.rb_qus_two_op_one);
        qusTwoOptTwo = view.findViewById(R.id.rb_qus_two_op_two);
        materialButton = view.findViewById(R.id.bt_user_ans_submit);
        qusone = view.findViewById(R.id.match_quiz_question_one);
        qustwo = view.findViewById(R.id.match_quiz_question_two);
        showError = view.findViewById(R.id.Show_Error);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        CheckStatus();
        SetUpQuiz();


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HandleUserSelection();
            }
        });

        return view;
    }
    private void CheckStatus() {


        databaseReference.child("matchno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String match_no = String.valueOf(dataSnapshot.getValue());
                databaseReferenceUserSubmit.child(match_no).child(currenUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            materialButton.setEnabled(false);
                            materialButton.setText("Already Submitted");

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SetUpQuiz() {

        databaseReference.child("qus_one").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String question = String.valueOf(dataSnapshot.child("qus").getValue());
                String optone = String.valueOf(dataSnapshot.child("opt_one").getValue());
                String opttwo = String.valueOf(dataSnapshot.child("opt_two").getValue());


                qusone.setText(question);
                qusOneOptOne.setText(optone);
                qusOneOptTwo.setText(opttwo);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("qus_two").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String question = String.valueOf(dataSnapshot.child("qus").getValue());
                String optone = String.valueOf(dataSnapshot.child("opt_one").getValue());
                String opttwo = String.valueOf(dataSnapshot.child("opt_two").getValue());


                qustwo.setText(question);
                qusTwoOptOne.setText(optone);
                qusTwoOptTwo.setText(opttwo);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void HandleUserSelection() {

        if (qusOneGroup.getCheckedRadioButtonId() == -1) {

            ValidationProcess.NotifyUser(relativeLayout, "Please submit both of quiz");
        }
        if (qustwoGroup.getCheckedRadioButtonId() == -1) {

            ValidationProcess.NotifyUser(relativeLayout, "Please submit both of quiz");
        } else {
            int selectedId = qusOneGroup.getCheckedRadioButtonId();
          radioButtonone = view.findViewById(selectedId);

            int selectedId2 = qustwoGroup.getCheckedRadioButtonId();
          radioButtontwo = view.findViewById(selectedId2);

         hashMap = new HashMap<>();
            databaseReference.child("matchno").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String match_no = String.valueOf(dataSnapshot.getValue());

                    hashMap.put("userphoneno", currentUserNumber);
                    hashMap.put("qus_one_ans", String.valueOf(radioButtonone.getText()));
                    hashMap.put("qus_two_ans", String.valueOf(radioButtontwo.getText()));


                    databaseReferenceUserSubmit.child(match_no).child(currenUserId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                ValidationProcess.NotifyUser(relativeLayout, "Answer Submitted");
                            } else {
                                ValidationProcess.NotifyUser(relativeLayout, "Fail To Submit");


                            }
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }


    private void ShowAds() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private boolean useDiceOne;

            @Override
            public void run() {

                if (!useDiceOne) {
                    banner.setImageResource(R.drawable.maxpro);
                } else {
                    banner.setImageResource(R.drawable.cal);
                }
                useDiceOne = !useDiceOne;
                handler.postDelayed(this, 5000);
            }
        }, 5000);

    }
}
