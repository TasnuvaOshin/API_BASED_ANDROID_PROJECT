package cricketworldcup.worldcup.AdminPanel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import cricketworldcup.worldcup.AdminPanel.BannerSetting.BannerSettingFragment;
import cricketworldcup.worldcup.AdminPanel.QuizSetting.AdminQuizHomeFragment;
import cricketworldcup.worldcup.AdminPanel.QuizSetting.QuizSettingFragment;
import cricketworldcup.worldcup.R;

public class AdminHomeActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private AdminHomeFragment adminHomeFragment;
    private BannerSettingFragment bannerSettingFragment;
    private QuizSettingFragment quizSettingFragment;
    private AdminQuizHomeFragment adminQuizHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        //fragmetns
        adminQuizHomeFragment = new AdminQuizHomeFragment();
        adminHomeFragment = new AdminHomeFragment();
        bannerSettingFragment = new BannerSettingFragment();
        quizSettingFragment = new QuizSettingFragment();
        SetFragment(adminHomeFragment);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.admin_home:
                        SetFragment(adminHomeFragment);
                        break;

                    case R.id.admin_banner:
                        SetFragment(bannerSettingFragment);
                        break;

                    case R.id.admin_quiz:
                        SetFragment(adminQuizHomeFragment);
                        break;


                }
                return false;
            }
        });
    }
    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}

