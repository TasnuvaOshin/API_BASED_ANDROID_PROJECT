package cricketworldcup.worldcup.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class team_view_holder extends RecyclerView.ViewHolder {
    public TextView teamname;
    public ImageView imageView;
    public team_view_holder(@NonNull View itemView) {
        super(itemView);
        teamname = itemView.findViewById(R.id.team_name);
        imageView = itemView.findViewById(R.id.img);
    }
}
