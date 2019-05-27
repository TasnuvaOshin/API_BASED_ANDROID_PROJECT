package cricketworldcup.worldcup.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class preview_view_holder extends RecyclerView.ViewHolder {
public TextView textView;

    public preview_view_holder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.name);
    }
}
