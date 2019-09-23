package koloboklesnoi.purchases.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import androidx.room.Room;
import butterknife.BindView;
import butterknife.ButterKnife;
import koloboklesnoi.purchases.R;
import koloboklesnoi.purchases.database.AppDatabase;
import koloboklesnoi.purchases.database.Purchase;
import koloboklesnoi.purchases.model.DatabaseModel;
import koloboklesnoi.purchases.presenter.MvpView;
import koloboklesnoi.purchases.presenter.Presenter;
import koloboklesnoi.purchases.view.adapter.FragmentAdapter;
import koloboklesnoi.purchases.view.adapter.ViewHolder;
import koloboklesnoi.purchases.view.fragment.SelectFragment;

import java.util.List;

public class SelectActivity extends AppCompatActivity implements MvpView {

    @BindView(R.id.toolbar_select) Toolbar toolbar;
    @BindView(R.id.frame) ViewPager frame;

    private FragmentAdapter adapter;
    private Presenter presenter;
    private List<Purchase> selectedList;
    private boolean selected = false;

    public final static String SELECTED = "selected";
    private int content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        content = getIntent().getIntExtra(SELECTED, 0);

        adapter = new FragmentAdapter(getSupportFragmentManager());

        AppDatabase purchase = Room.databaseBuilder(this, AppDatabase.class, "purchase").build();
        AppDatabase receipt = Room.databaseBuilder(this, AppDatabase.class, "receipt").build();
        presenter = new Presenter(this, purchase, receipt);
        if(content == DatabaseModel.PURCHASE) presenter.getPurchaseList();
        if(content == DatabaseModel.RECEIPT) presenter.getReceiptList();
    }

    @Override
    public void showPurchaseList(List<Purchase> purchases) {
        SelectFragment selectFragment = new SelectFragment();
        selectFragment.setList(purchases);
        adapter.addFragment(selectFragment, "Покупки");
        frame.setAdapter(adapter);

        selectedList = purchases;
    }

    @Override
    public void showReceiptList(List<Purchase> receipt) {
        SelectFragment selectFragment = new SelectFragment();
        selectFragment.setList(receipt);
        adapter.addFragment(selectFragment, "Чек");
        frame.setAdapter(adapter);

        selectedList = receipt;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_select, menu);
        if(content == DatabaseModel.RECEIPT){
            menu.findItem(R.id.send).setIcon(ContextCompat.getDrawable(this,R.drawable.revert));
        }
        return true;
    }

    public void selectAll(MenuItem item){
        SelectFragment selectFragment = (SelectFragment) adapter.getItem(0);
        for(ViewHolder viewHolder: selectFragment.getViewHolderList()){
            if(!selected) {
                viewHolder.avatar.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected));
                viewHolder.selected = true;
            }else {
                if(viewHolder.imageURI != null) {
                    viewHolder.avatar.setImageURI(viewHolder.imageURI);
                }else {
                    viewHolder.avatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_image));
                }
                viewHolder.selected = false;
            }
        }
        selected = !selected;
    }

    public void send(MenuItem item) {
        checkSelectedList();
        if(content == DatabaseModel.PURCHASE) {
            presenter.insertAllToReceipt(selectedList);
            presenter.deleteAllFromPurchase(selectedList);
        }
        if(content == DatabaseModel.RECEIPT){
            presenter.insertAllToPurchase(selectedList);
            presenter.deleteAllFromReceipt(selectedList);
        }

        finish();

    }

    public void delete(MenuItem item) {
        checkSelectedList();

        if(content == DatabaseModel.PURCHASE)presenter.deleteAllFromPurchase(selectedList);
        if(content == DatabaseModel.RECEIPT)presenter.deleteAllFromReceipt(selectedList);
        finish();
    }


    private void checkSelectedList(){
        SelectFragment selectFragment = (SelectFragment) adapter.getItem(0);
        List<ViewHolder> list = selectFragment.getViewHolderList();

        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).selected) {
                selectedList.remove(j);
                j--;
            }
            j++;
        }
    }
}
