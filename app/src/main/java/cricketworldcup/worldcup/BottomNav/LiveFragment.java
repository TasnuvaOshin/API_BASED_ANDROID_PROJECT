package cricketworldcup.worldcup.BottomNav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.ScoreCard.ScorecardFragment;


public class LiveFragment extends Fragment {
    private ImageView banner;
    private WebView webView;
    private Button button;
    private ScorecardFragment scorecardFragment;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View view = inflater.inflate(R.layout.fragment_live, container, false);

        banner= view.findViewById(R.id.iv_banner_ads);
        ShowAds();
        scorecardFragment = new ScorecardFragment();
        webView= view.findViewById(R.id.wv_broadcast);
        button = view.findViewById(R.id.btn_scorecard);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.youtube.com/watch?v=-57NF20HULM");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFragment(scorecardFragment);
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
