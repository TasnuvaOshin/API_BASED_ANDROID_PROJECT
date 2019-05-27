package cricketworldcup.worldcup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import cricketworldcup.worldcup.Authentication.LoginActivity;

public class SplashScreen extends AppCompatActivity {
    public ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.pb_splash);
        firebaseAuth = FirebaseAuth.getInstance();
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress=0; progress<100; progress+=30) {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void startApp() {
        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
        SplashScreen.this.overridePendingTransition(0,0);
    }
}
