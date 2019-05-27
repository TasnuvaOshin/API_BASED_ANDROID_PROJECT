package cricketworldcup.worldcup.ScoreCard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cricketworldcup.worldcup.R;


public class ScorecardFragment extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorecard, container, false);
        webView = view.findViewById(R.id.webview);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.cricbuzz.com/live-cricket-scorecard/22509/mi-vs-csk-final-indian-premier-league-2019");


        return view;
    }


}
