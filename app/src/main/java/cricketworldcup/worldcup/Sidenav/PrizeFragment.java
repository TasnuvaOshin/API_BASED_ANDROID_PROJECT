package cricketworldcup.worldcup.Sidenav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cricketworldcup.worldcup.R;


public class PrizeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.fragment_prize, container, false);
        return view;
    }

}
