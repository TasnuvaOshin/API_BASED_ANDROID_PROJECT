package cricketworldcup.worldcup.BottomNav;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cricketworldcup.worldcup.Model.team_model;
import cricketworldcup.worldcup.PlayerProfile.PlayerFragment;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.ViewHolder.team_view_holder;

public class TeamFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<team_model> options;
    private FirebaseRecyclerAdapter<team_model, team_view_holder> adapter;

    private DatabaseReference databaseReferenceTeam;
    private String mParam1;
    private String mParam2;
    private Bundle bundle;
    private ImageView banner;
    private PlayerFragment playerFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        banner = view.findViewById(R.id.iv_banner_ads);
        ShowAds();
        databaseReferenceTeam = FirebaseDatabase.getInstance().getReference().child("teams");
        recyclerView = view.findViewById(R.id.team_recyclerview);
        recyclerView.setHasFixedSize(true);
        bundle = new Bundle();
        playerFragment = new PlayerFragment();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        options = new FirebaseRecyclerOptions.Builder<team_model>().setQuery(databaseReferenceTeam.orderByChild("name"), team_model.class).build();
        adapter = new FirebaseRecyclerAdapter<team_model, team_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull team_view_holder holder, int position, @NonNull final team_model model) {

                //   holder.teamname.setText(model.getName());
                Picasso.get().load(model.getImg()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bundle.putString("team", model.getName());
                        playerFragment.setArguments(bundle);
                        SetFragment(playerFragment);
                    }
                });

            }

            @NonNull
            @Override
            public team_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new team_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.team_row, viewGroup, false));
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

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }

    private void ShowAds() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private boolean useDiceOne;

            @Override
            public void run() {

                if (!useDiceOne) {
                    banner.setImageResource(R.drawable.maxpro);
                } else {
                    banner.setImageResource(R.drawable.cal);
                }
                useDiceOne = !useDiceOne;
                handler.postDelayed(this, 5000);
            }
        }, 5000);

    }
}
