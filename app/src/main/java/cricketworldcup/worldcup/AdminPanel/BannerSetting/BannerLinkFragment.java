package cricketworldcup.worldcup.AdminPanel.BannerSetting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cricketworldcup.worldcup.R;

public class BannerLinkFragment extends Fragment {
    private String section;
    private EditText bannerOne,bannerTwo;
    private Button submit;
    private DatabaseReference databaseReference;
    private BannerSettingFragment bannerSettingFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banner_link, container, false);
        section = this.getArguments().getString("section");

       databaseReference = FirebaseDatabase.getInstance().getReference().child("Banner_Settings").child(section);
       bannerSettingFragment = new BannerSettingFragment();
       bannerOne = view.findViewById(R.id.banner_one_link);
        bannerTwo = view.findViewById(R.id.banner_two_link);
        submit = view.findViewById(R.id.banner_submit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(bannerOne.getText())){
                    String bannerone = bannerOne.getText().toString();

                    databaseReference.child("banner_one").setValue(bannerone);

                }
                if(!TextUtils.isEmpty(bannerTwo.getText())){
                    String bannertwo = bannerTwo.getText().toString();

                    databaseReference.child("banner_two").setValue(bannertwo);

                }

                Toast.makeText(getActivity(), "Information Saved !", Toast.LENGTH_SHORT).show();
                SetFragment(bannerSettingFragment);
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
