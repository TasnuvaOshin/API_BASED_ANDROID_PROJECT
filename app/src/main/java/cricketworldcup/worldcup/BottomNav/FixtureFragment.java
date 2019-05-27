package cricketworldcup.worldcup.BottomNav;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import cricketworldcup.worldcup.Model.fixture_model;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.ViewHolder.fixer_view_holder;


public class FixtureFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView banner;
    private FirebaseRecyclerOptions<fixture_model> options;
    private FirebaseRecyclerAdapter<fixture_model, fixer_view_holder> adapter;
    private DatabaseReference FixturedatabaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_fixture, container, false);
        TabHost host = view.findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("FIXTURES");
        spec.setContent(R.id.tab1);
        spec.setIndicator("FIXTURES");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("RESULTS");
        spec.setContent(R.id.tab2);
        spec.setIndicator("RESULTS");
        host.addTab(spec);

        banner= view.findViewById(R.id.iv_banner_ads);
        ShowAds();
        recyclerView = view.findViewById(R.id.fixture_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        FixturedatabaseReference = FirebaseDatabase.getInstance().getReference().child("match");
        options = new FirebaseRecyclerOptions.Builder<fixture_model>().setQuery(FixturedatabaseReference, fixture_model.class).build();
        adapter = new FirebaseRecyclerAdapter<fixture_model, fixer_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull fixer_view_holder holder, int position, @NonNull fixture_model model) {

                Picasso.get().load(model.getImg()).into(holder.imageView);
                holder.teamone.setText(model.getTeamone());
                holder.teamtwo.setText(model.getTeamtwo());
                holder.time.setText(model.getLongdes());
                holder.venue.setText(model.getShortdes());
            }

            @NonNull
            @Override
            public fixer_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new fixer_view_holder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fixture_row, viewGroup, false));
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
