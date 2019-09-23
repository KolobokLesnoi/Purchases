package koloboklesnoi.purchases.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import koloboklesnoi.purchases.R;
import koloboklesnoi.purchases.view.DetailActivity;

public class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView avatar;
    public TextView name;
    public TextView description;
    public boolean selected = false;
    public Uri imageURI;

    public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_list, parent, false));
        avatar = (ImageView) itemView.findViewById(R.id.list_avatar);
        name = (TextView) itemView.findViewById(R.id.list_title);
        description = (TextView) itemView.findViewById(R.id.list_desc);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("name",name.getText());
                intent.putExtra("description", description.getText());
                intent.setData(imageURI);
                context.startActivity(intent);
            }
        });
    }
}
