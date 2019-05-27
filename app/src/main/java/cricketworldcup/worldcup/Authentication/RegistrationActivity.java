package cricketworldcup.worldcup.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import cricketworldcup.worldcup.Model.UserData;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Utility.ValidationProcess;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "mobile_verification";
    private TextInputEditText nameInput, emailInput, phonenoInput, verificationcode, dsmcode;
    private String name, email, phoneno;
    private RelativeLayout relativeLayout, verify, registration;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks changedCallbacks;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String verificatioN_id, token, current_userid, dsm;
    private Button materialButton;
    private ProgressDialog progressDialog;
    DatabaseReference def;

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            RegistrationActivity.this.overridePendingTransition(0, 0);
            finish();

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.........");
        progressDialog.setCanceledOnTouchOutside(false);
        verificationcode = findViewById(R.id.et_verification_code);
        nameInput = findViewById(R.id.et_reg_name);
        emailInput = findViewById(R.id.et_reg_email_no);
        phonenoInput = findViewById(R.id.et_reg_mobile_no);
        verify = findViewById(R.id.registration_verifiy);
        registration = findViewById(R.id.registration_on);
        dsmcode = findViewById(R.id.et_reg_dsm_no);
        materialButton = findViewById(R.id.btn_signin);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        changedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                ValidationProcess.NotifyUser(relativeLayout, "Wrong Code !");
                progressDialog.dismiss();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                verificatioN_id = verificationId;
                token = String.valueOf(forceResendingToken);
                ValidationProcess.NotifyUser(relativeLayout, "Verification Code Send");
                progressDialog.dismiss();
            }

        };

    }

    public void SignUpProcess(View view) {
        progressDialog.show();
        hideKeyBorad();


        name = nameInput.getText().toString().trim();
        email = emailInput.getText().toString().trim();
        phoneno = "+88" + phonenoInput.getText().toString().trim();
        relativeLayout = findViewById(R.id.root);
        dsm = dsmcode.getText().toString();
        if (TextUtils.isEmpty(name)) {

            nameInput.requestFocus();
            nameInput.setError("Please Insert Your Name");
            ValidationProcess.NotifyUser(relativeLayout, "Please Insert Your Name");
            progressDialog.dismiss();
        }
        if (TextUtils.isEmpty(dsm)) {

            dsmcode.requestFocus();
            dsmcode.setError("Please Insert your DSM Code");
            ValidationProcess.NotifyUser(relativeLayout, "Please Insert your DSM Code");

            progressDialog.dismiss();

        }

        if (TextUtils.isEmpty(phoneno)) {
            progressDialog.dismiss();
            phonenoInput.requestFocus();
            phonenoInput.setError("Please Insert your Phone-no");
            ValidationProcess.NotifyUser(relativeLayout, "Please Insert your Phone-no");

        } else {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

           def = FirebaseDatabase.getInstance().getReference().child("data");
            databaseReference.orderByChild("userphoneno").equalTo(phoneno).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//goto login

                        ValidationProcess.NotifyUser(relativeLayout,"You Are Already Registered");
                        progressDialog.dismiss();
                    } else {


                        def.orderByChild("G").equalTo(dsm).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    registration.setVisibility(View.GONE);
                                    verify.setVisibility(View.VISIBLE);
                                    materialButton.setVisibility(View.GONE);
                                    VerifyNumber();

                                } else {
                                    progressDialog.dismiss();
                                    ValidationProcess.NotifyUser(relativeLayout,"Wrong DSM NO !");

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }


    }

    private void VerifyNumber() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneno,
                60,
                TimeUnit.SECONDS,
                this,
                changedCallbacks


        );

    }


    private void hideKeyBorad() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void VerifyProcess(View view) {
        //verification will be there
        String code = verificationcode.getText().toString();
        VerifyCode(code);
    }


    public void VerifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatioN_id, code);
        signInWithPhoneAuthCredential(credential);


    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            current_userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            SubmitUserInfo(current_userid);

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

    private void SubmitUserInfo(String current_userid) {
        name = nameInput.getText().toString().trim();
        email ="maxpro@gmail.com";
        phoneno = "+88" + phonenoInput.getText().toString().trim();
        dsm = dsmcode.getText().toString();

        UserData userData = new UserData(name, email, phoneno, dsm);


        databaseReference.child(current_userid).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    ValidationProcess.NotifyUser(relativeLayout, "Successfully Registered");
                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    RegistrationActivity.this.overridePendingTransition(0, 0);
                    finish();


                } else {


                    ValidationProcess.NotifyUser(relativeLayout, "Registration Failed");

                }
            }
        });

    }


    public void GoToLoginPage(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        RegistrationActivity.this.overridePendingTransition(0, 0);
        finish();
    }
}