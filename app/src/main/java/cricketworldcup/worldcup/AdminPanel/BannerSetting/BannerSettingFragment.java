package cricketworldcup.worldcup.AdminPanel.BannerSetting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cricketworldcup.worldcup.R;

public class BannerSettingFragment extends Fragment {
    private Button homepage, livepage, fixturepage, teampage, profilepage, standingpage, matchquizpage, myteampage, historypage;
    private Bundle bundle;
    private BannerLinkFragment bannerLinkFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_setting, container, false);
        bannerLinkFragment = new BannerLinkFragment();
        bundle = new Bundle();
        homepage = view.findViewById(R.id.home_page_banner);
        livepage = view.findViewById(R.id.live_page_banner);
        fixturepage = view.findViewById(R.id.fixture_page_banner);
        teampage = view.findViewById(R.id.team_page_banner);
        profilepage = view.findViewById(R.id.profile_page_banner);
        standingpage = view.findViewById(R.id.standing_page_banner);
        matchquizpage = view.findViewById(R.id.matchquiz_page_banner);
        myteampage = view.findViewById(R.id.myteam_page_banner);
        historypage = view.findViewById(R.id.history_page_banner);

        historypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "historypage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });
        myteampage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "myteampage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        matchquizpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "matchquizpage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        standingpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "standingpage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        profilepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "profilepage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });









        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "homepage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        livepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "livepage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        fixturepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "fixturepage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
            }
        });

        teampage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("section", "teampage");
                bannerLinkFragment.setArguments(bundle);
                SetFragment(bannerLinkFragment);
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
