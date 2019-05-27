package cricketworldcup.worldcup.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class player_view_holder extends RecyclerView.ViewHolder {

    public TextView playername;
    public ImageView imageView;
    public player_view_holder(@NonNull View itemView) {
        super(itemView);
        playername = itemView.findViewById(R.id.player_name);
        imageView = itemView.findViewById(R.id.squad_row_img);
    }
}
