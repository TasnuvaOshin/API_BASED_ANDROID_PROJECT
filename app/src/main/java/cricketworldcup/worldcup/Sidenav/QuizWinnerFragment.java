package cricketworldcup.worldcup.Sidenav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cricketworldcup.worldcup.R;


public class QuizWinnerFragment extends Fragment {
    private Button materialButton;
    private MyTeamWinnerFragment myTeamWinnerFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_quiz_winner, container, false);
        myTeamWinnerFragment = new MyTeamWinnerFragment();
        materialButton = view.findViewById(R.id.bt_team_winner);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,myTeamWinnerFragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


}
