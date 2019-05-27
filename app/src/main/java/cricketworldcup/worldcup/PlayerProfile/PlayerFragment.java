package cricketworldcup.worldcup.PlayerProfile;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cricketworldcup.worldcup.Model.player_model;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.ViewHolder.player_view_holder;


public class PlayerFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReferencePlayer;
    private FirebaseRecyclerOptions<player_model> options;
    private FirebaseRecyclerAdapter<player_model, player_view_holder> adapter;

    private CorrectPlayerProfileFragment correctPlayerProfileFragment;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_player, container, false);
        final String value = this.getArguments().getString("team");
        bundle = new Bundle();
        recyclerView = view.findViewById(R.id.players_recycelerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        correctPlayerProfileFragment = new CorrectPlayerProfileFragment();
        databaseReferencePlayer = FirebaseDatabase.getInstance().getReference().child("squad").child(value);
        options = new FirebaseRecyclerOptions.Builder<player_model>().setQuery(databaseReferencePlayer, player_model.class).build();
        adapter = new FirebaseRecyclerAdapter<player_model, player_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull player_view_holder holder, int position, @NonNull final player_model model) {
                //  holder.playername.setText(model.getPlayer_name());
                Picasso.get().load(model.getImg()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bundle.putString("team",value);
                        bundle.putString("name",model.getPlayer_name());
                        correctPlayerProfileFragment.setArguments(bundle);

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, correctPlayerProfileFragment);
                        fragmentTransaction.addToBackStack("my_fragment").commit();
                    }
                });
            }

            @NonNull
            @Override
            public player_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new player_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.player, viewGroup, false));
            }
        };

        recyclerView.setAdapter(adapter);






       return view;
    }


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
}