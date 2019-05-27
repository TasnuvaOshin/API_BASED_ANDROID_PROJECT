package cricketworldcup.worldcup.BottomNav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Sidenav.MatchQuizFragment;
import cricketworldcup.worldcup.TeamSelection.MyTeamFragment;


public class MainHomeFragment extends Fragment {
    private TextView teamone, teamtwo;
    private Button quiz, myteam;
    private MatchQuizFragment matchQuizFragment;
    private Bundle bundle;
    private ImageView banner;
    private MyTeamFragment myTeamFragment;
    private DatabaseReference databaseReference;
    String data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_main_home, container, false);
       databaseReference = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        myTeamFragment = new MyTeamFragment();
        matchQuizFragment = new MatchQuizFragment();
        teamone = view.findViewById(R.id.teamonename);
        teamtwo = view.findViewById(R.id.teamtwoname);
        quiz = view.findViewById(R.id.btn_matchQUiz);
        myteam = view.findViewById(R.id.btn_myteam);
        bundle = new Bundle();
        banner= view.findViewById(R.id.iv_banner_ads);
        ShowAds();

databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        data = (String) dataSnapshot.child("matchno").getValue();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

        myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("teamone", String.valueOf(teamone.getText()));
                bundle.putString("teamtwo", String.valueOf(teamtwo.getText()));
                bundle.putString("matchno",data);
                myTeamFragment.setArguments(bundle);
                SetFragment(myTeamFragment);


            }
        });





        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SetFragment(matchQuizFragment);


            }
        });

      return view;
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

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }
}
