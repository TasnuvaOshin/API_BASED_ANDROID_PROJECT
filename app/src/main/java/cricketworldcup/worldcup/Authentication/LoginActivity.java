package cricketworldcup.worldcup.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import cricketworldcup.worldcup.MainActivity;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Utility.ValidationProcess;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "login_varification";
    private RelativeLayout relativeLayout, login_section, login_verification_section;
    private TextInputEditText phoneno, verification_code;
    private String phone;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks Callbacks;
    private String verificatioN_id;
    private String token;
    private String current_userid;
    private Button materialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verification_code = findViewById(R.id.et_verification_code);
        relativeLayout = findViewById(R.id.root);
        materialButton = findViewById(R.id.btn_signup);
        login_section = findViewById(R.id.login_section);
        login_verification_section = findViewById(R.id.login_verifiy_section);
        phoneno = findViewById(R.id.et_mobile_no);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        Callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                ValidationProcess.NotifyUser(relativeLayout, "Wrong Code !");
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                verificatioN_id = verificationId;
                token = String.valueOf(forceResendingToken);
                ValidationProcess.NotifyUser(relativeLayout, "Verification Code Send");

            }

        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.overridePendingTransition(0, 0);
            finish();

        }
    }

    public void SignUpCall(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        LoginActivity.this.overridePendingTransition(0, 0);
    }

    public void VerifyProcess(View view) {

        if (TextUtils.isEmpty(verification_code.getText().toString())) {
            ValidationProcess.NotifyUser(relativeLayout, "Empty !");


        } else {

            String code = verification_code.getText().toString();
            VerifyCode(code);
        }
    }

    public void VerifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatioN_id, code);
        signInWithPhoneAuthCredential(credential);


    }

    public void LogInProcess(View view) {
        if (TextUtils.isEmpty(phoneno.getText().toString())) {
            phoneno.requestFocus();
            phoneno.setError("Enter Your Phone No");
            ValidationProcess.NotifyUser(relativeLayout, "Enter your phone no");

        } else {


            phone = "+88" + phoneno.getText().toString().trim();
            // ValidationProcess.NotifyUser(relativeLayout,phone);
            databaseReference.orderByChild("userphoneno").equalTo(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        materialButton.setVisibility(View.GONE);
                        login_section.setVisibility(View.GONE);
                        login_verification_section.setVisibility(View.VISIBLE);


                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                phone,
                                60,
                                TimeUnit.SECONDS,
                                LoginActivity.this,
                                Callbacks

                        );


                    } else {


                        ValidationProcess.NotifyUser(relativeLayout, "You Are not a Registered User!");

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            current_userid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.overridePendingTransition(0, 0);
                            finish();
                            FirebaseUser user = task.getResult().getUser();


                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                ValidationProcess.NotifyUser(relativeLayout, "Registration Failed !");

                            }
                        }
                    }
                });
    }
}
