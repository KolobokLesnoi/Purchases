package koloboklesnoi.purchases.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import koloboklesnoi.purchases.R;
import koloboklesnoi.purchases.database.Purchase;

import java.util.ArrayList;
import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<ViewHolder> viewHolderList = new ArrayList<>();
    private List<Purchase> list;
    private Context context;

    public SelectAdapter(Context context, List<Purchase> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        viewHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.selected) {
                    viewHolder.avatar.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.drawable.selected));
                    viewHolder.selected = true;
                }else {
                    if(viewHolder.imageURI != null) {
                        viewHolder.avatar.setImageURI(viewHolder.imageURI);
                    }else {
                        viewHolder.avatar.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.default_image));
                    }
                    viewHolder.selected = false;
                }
            }
        });
        viewHolderList.add(viewHolder);
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
        if(list != null) return list.size();
        return 0;
    }

    public List<ViewHolder> getViewHolderList() {
        return viewHolderList;
    }
}
