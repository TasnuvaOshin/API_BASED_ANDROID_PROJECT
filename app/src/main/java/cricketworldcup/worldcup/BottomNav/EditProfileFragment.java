package cricketworldcup.worldcup.BottomNav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Utility.ValidationProcess;


public class EditProfileFragment extends Fragment {
    private TextInputEditText nameInput, emailInput, phonenoInput, dsmcode;
    private String name, email, phoneno;
    private RelativeLayout relativeLayout;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private Button save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        nameInput = view.findViewById(R.id.et_reg_name);
        emailInput = view.findViewById(R.id.et_reg_email_no);
        phonenoInput = view.findViewById(R.id.et_reg_mobile_no);
        dsmcode = view.findViewById(R.id.et_reg_dsm_no);
        relativeLayout = view.findViewById(R.id.root);
        save = view.findViewById(R.id.btn_save);
        firebaseAuth = FirebaseAuth.getInstance();
        phonenoInput.setEnabled(false);
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String dsm = String.valueOf(dataSnapshot.child("userdsmcode").getValue());
                String phoneno = String.valueOf(dataSnapshot.child("userphoneno").getValue());
                String name = String.valueOf(dataSnapshot.child("username").getValue());
                String email = String.valueOf(dataSnapshot.child("useremail").getValue());
                nameInput.setHint(name);
                phonenoInput.setText(phoneno);
                dsmcode.setHint(dsm);
                emailInput.setHint(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailInput.getText().toString();
                String name = nameInput.getText().toString();
                String dsm = dsmcode.getText().toString();
                if(!TextUtils.isEmpty(email)){

                    databaseReference.child("useremail").setValue(email);
                }
                if(!TextUtils.isEmpty(name)){

                    databaseReference.child("username").setValue(name);
                }
                if(!TextUtils.isEmpty(dsm)){

                    databaseReference.child("userdsmcode").setValue(dsm);
                }
                ValidationProcess.NotifyUser(relativeLayout,"Saved");
            }
        });


        return view;
    }

}
