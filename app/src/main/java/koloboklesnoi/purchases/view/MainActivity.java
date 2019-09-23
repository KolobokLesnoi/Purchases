package koloboklesnoi.purchases.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
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
import koloboklesnoi.purchases.view.fragment.ContentFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MvpView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabs;

    @BindView(R.id.drawer) DrawerLayout drawerLayout;

    @BindView(R.id.fab) FloatingActionButton floatingActionButton;

    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        tabs.setupWithViewPager(viewPager);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            VectorDrawableCompat indicator
                    = VectorDrawableCompat.create(getResources(), R.drawable.ic_menu, getTheme());
            indicator.setTint(ResourcesCompat.getColor(getResources(),R.color.white,getTheme()));
            supportActionBar.setHomeAsUpIndicator(indicator);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivityForResult(intent,1);
            }
        });

        AppDatabase purchase = Room.databaseBuilder(this, AppDatabase.class, "purchase").build();
        AppDatabase receipt = Room.databaseBuilder(this, AppDatabase.class, "receipt").build();
        presenter = new Presenter(this, purchase, receipt);
        presenter.getPurchaseList();
        presenter.getReceiptList();
    }

    @Override
    public void showPurchaseList(List<Purchase> purchases){
        FragmentAdapter fragmentAdapter = (FragmentAdapter) viewPager.getAdapter();
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setList(purchases);
        contentFragment.setContent(DatabaseModel.PURCHASE);
        fragmentAdapter.setFragment(0, contentFragment);
    }

    @Override
    public void showReceiptList(List<Purchase> receipt){
        FragmentAdapter fragmentAdapter = (FragmentAdapter) viewPager.getAdapter();
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setList(receipt);
        contentFragment.setContent(DatabaseModel.RECEIPT);
        fragmentAdapter.setFragment(1, contentFragment);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContentFragment(), "Покупки");
        adapter.addFragment(new ContentFragment(), "Чек");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Purchase purchase = new Purchase();
                purchase.name = data.getStringExtra("name");
                purchase.description = data.getStringExtra("description");
                purchase.imageURI = data.getStringExtra("photo");
                presenter.insertToPurhcase(purchase);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // добавить обновление
    }
}
