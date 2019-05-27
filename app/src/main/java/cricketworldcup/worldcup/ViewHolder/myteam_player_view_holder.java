package cricketworldcup.worldcup.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class myteam_player_view_holder extends RecyclerView.ViewHolder {
    public TextView name;
    public Button materialButton;

    public CheckBox checkBox;
    public myteam_player_view_holder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_player_name);
        materialButton = itemView.findViewById(R.id.bt_player_select);
        checkBox = itemView.findViewById(R.id.check);
    }
}
