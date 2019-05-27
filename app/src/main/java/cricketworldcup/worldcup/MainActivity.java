package cricketworldcup.worldcup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cricketworldcup.worldcup.Authentication.LoginActivity;
import cricketworldcup.worldcup.BottomNav.EditProfileFragment;
import cricketworldcup.worldcup.BottomNav.FixtureFragment;
import cricketworldcup.worldcup.BottomNav.LiveFragment;
import cricketworldcup.worldcup.BottomNav.MainHomeFragment;
import cricketworldcup.worldcup.BottomNav.TeamFragment;
import cricketworldcup.worldcup.Sidenav.HistoryFragment;
import cricketworldcup.worldcup.Sidenav.MatchQuizFragment;
import cricketworldcup.worldcup.Sidenav.MyQuizScoreFragment;
import cricketworldcup.worldcup.Sidenav.MyTeamScoreFragment;
import cricketworldcup.worldcup.Sidenav.MyTeamWinnerFragment;
import cricketworldcup.worldcup.Sidenav.PreviewMyTeamFragment;
import cricketworldcup.worldcup.Sidenav.PrizeFragment;
import cricketworldcup.worldcup.Sidenav.QuizWinnerFragment;
import cricketworldcup.worldcup.Sidenav.StandingFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private Bundle bundle;
    private BottomNavigationView bottomNavigationView;
    private FixtureFragment fixtureFragment;
    private MainHomeFragment mainHomeFragment;
    private TeamFragment teamFragment;
    private ProgressDialog progressDialog;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LiveFragment liveFragment;
    private MatchQuizFragment matchQuizFragment;
    private FirebaseAuth firebaseAuth;
    private HistoryFragment historyFragment;
    private StandingFragment standingFragment;
    private PrizeFragment prizeFragment;
    private PreviewMyTeamFragment previewMyTeamFragment;
    private QuizWinnerFragment quizWinnerFragment;
    private MyTeamWinnerFragment myTeamWinnerFragment;
    private MyTeamScoreFragment myTeamScoreFragment;
    private MyQuizScoreFragment myQuizScoreFragment;
    private EditProfileFragment editProfileFragment;
    private DatabaseReference databaseReference;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("live_quiz");

        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        liveFragment = new LiveFragment();
        prizeFragment = new PrizeFragment();
        myTeamScoreFragment = new MyTeamScoreFragment();
        firebaseAuth = FirebaseAuth.getInstance();
        matchQuizFragment = new MatchQuizFragment();
        historyFragment = new HistoryFragment();
        editProfileFragment = new EditProfileFragment();
        quizWinnerFragment = new QuizWinnerFragment();
        previewMyTeamFragment = new PreviewMyTeamFragment();
        myQuizScoreFragment = new MyQuizScoreFragment();
        mainHomeFragment = new MainHomeFragment();
        standingFragment = new StandingFragment();
        myTeamWinnerFragment = new MyTeamWinnerFragment();
        fixtureFragment = new FixtureFragment();
        teamFragment = new TeamFragment();
        SetFragment(mainHomeFragment);
        SetUpSideBar();
        SetUpSideDrawer();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                data = (String) dataSnapshot.child("matchno").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        MainActivity.this.overridePendingTransition(0, 0);
                        return true;

                    case R.id.fixture:
                        SetFragment(fixtureFragment);
                        return true;

                    case R.id.team_profile:
                        SetFragment(teamFragment);
                        return true;

                    case R.id.user_profile:
                        SetFragment(editProfileFragment);
                        return true;

                    case R.id.live:
                        SetFragment(liveFragment);
                        return true;


                }
                return false;
            }
        });
    }

    private void SetUpSideBar() {


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.home_home:
                        SetFragment(mainHomeFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_live:
                        SetFragment(liveFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_fixture:
                        SetFragment(fixtureFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_team:
                        SetFragment(teamFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_profile:
                        SetFragment(editProfileFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_quiz:
                        SetFragment(matchQuizFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_history:
                        SetFragment(historyFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_logout:
                        logMeout();
                        break;

                    case R.id.home_standing:
                        SetFragment(standingFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_prize:
                        SetFragment(prizeFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_my_team:
                        bundle.putString("matchno", data);
                        previewMyTeamFragment.setArguments(bundle);
                        SetFragment(previewMyTeamFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_quiz_winner:
                        SetFragment(quizWinnerFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_my_team_winner:
                        SetFragment(myTeamWinnerFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_my_team_score:
                        SetFragment(myTeamScoreFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_quiz_score:
                        SetFragment(myQuizScoreFragment);
                        drawerLayout.closeDrawers();
                        break;


                }


                return false;
            }
        });
    }

    private void logMeout() {


        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.overridePendingTransition(0, 0);
        finish();

    }

    private void SetUpSideDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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
