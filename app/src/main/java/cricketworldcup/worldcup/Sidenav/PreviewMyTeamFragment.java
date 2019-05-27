package cricketworldcup.worldcup.Sidenav;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import cricketworldcup.worldcup.Model.preview_model;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.ViewHolder.preview_view_holder;


public class PreviewMyTeamFragment extends Fragment {

    private RecyclerView recyclerViewMyTeam;
    private FirebaseRecyclerOptions<preview_model> options;
    private FirebaseRecyclerAdapter<preview_model, preview_view_holder> adapter;
    private DatabaseReference databaseReference, databaseReferenceLive;
    private FirebaseAuth firebaseAuth;

    private String matchno, currentUserID;
    int match;


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_my_team, container, false);
        recyclerViewMyTeam = view.findViewById(R.id.recyclerView_MyTeamShow);
        databaseReferenceLive = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        recyclerViewMyTeam.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        matchno = this.getArguments().getString("matchno");
        //Toast.makeText(getActivity(), ""+currentUserID, Toast.LENGTH_SHORT).show();
        databaseReferenceLive.child("matchno").addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                match = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.getValue()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("team_selection").child(currentUserID).child(matchno);

        options = new FirebaseRecyclerOptions.Builder<preview_model>().setQuery(databaseReference, preview_model.class).build();
        adapter = new FirebaseRecyclerAdapter<preview_model, preview_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull preview_view_holder holder, int position, @NonNull preview_model model) {
                holder.textView.setText(model.getPlayer_name());
            }

            @NonNull
            @Override
            public preview_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new preview_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.preview_player, viewGroup, false));
            }
        };


        recyclerViewMyTeam.setAdapter(adapter);

        return view;
    }


}
