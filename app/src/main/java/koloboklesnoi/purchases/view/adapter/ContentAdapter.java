package koloboklesnoi.purchases.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import koloboklesnoi.purchases.R;
import koloboklesnoi.purchases.database.Purchase;
import koloboklesnoi.purchases.view.SelectActivity;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Purchase> list;
    private int content;
    private Context context;

    public ContentAdapter(Context context, List<Purchase> list, int content) {
        this.context = context;
        this.list = list;
        this.content = content;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        viewHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, SelectActivity.class);
                intent.putExtra(SelectActivity.SELECTED, content);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Purchase purchase = list.get(position);
        holder.name.setText(purchase.name);
        holder.description.setText(purchase.description);
        if(purchase.imageURI != null) {
            holder.imageURI = Uri.parse(purchase.imageURI);
            holder.avatar.setImageURI(holder.imageURI);
        }else {
            holder.avatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_image));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
