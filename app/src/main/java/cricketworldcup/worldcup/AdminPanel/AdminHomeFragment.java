package cricketworldcup.worldcup.AdminPanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cricketworldcup.worldcup.AdminPanel.RegistrationStatus.RegStatusActivity;
import cricketworldcup.worldcup.R;


public class AdminHomeFragment extends Fragment {
    private Button reg_status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        reg_status = view.findViewById(R.id.btn_admin_reg_status);
        reg_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),RegStatusActivity.class));
                getActivity().overridePendingTransition(0,0);
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