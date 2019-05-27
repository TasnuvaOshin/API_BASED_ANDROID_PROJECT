package cricketworldcup.worldcup.Sidenav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import cricketworldcup.worldcup.R;


public class StandingFragment extends Fragment {
private TabHost tabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_standing, container, false);
        TabHost host = view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("GROUP-STAGE");
        spec.setContent(R.id.tab1);
        spec.setIndicator("GROUP-STAGE");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("KNOCK-OUT");
        spec.setContent(R.id.tab2);
        spec.setIndicator("KNOCK-OUT");
        host.addTab(spec);

        return view;
    }


}
