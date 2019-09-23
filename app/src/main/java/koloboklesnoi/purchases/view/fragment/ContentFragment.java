package koloboklesnoi.purchases.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import koloboklesnoi.purchases.R;
import koloboklesnoi.purchases.database.Purchase;
import koloboklesnoi.purchases.view.adapter.ContentAdapter;
import koloboklesnoi.purchases.view.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {

    private List<Purchase> list = new ArrayList<>();
    private int content = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(getContext(),list, content);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public List<Purchase> getList() {
        return list;
    }

    public void setList(List<Purchase> list) {
        this.list = list;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
