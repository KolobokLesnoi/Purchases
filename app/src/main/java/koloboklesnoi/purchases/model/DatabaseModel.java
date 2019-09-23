package koloboklesnoi.purchases.model;

import koloboklesnoi.purchases.database.AppDatabase;
import koloboklesnoi.purchases.database.Purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class DatabaseModel implements Callable<List<Purchase>>{

    private AppDatabase database;
    private int query;
    private Purchase purchase;
    private List<Purchase> purchaseList;

    public final static int PURCHASE = 1;
    public final static int RECEIPT = 2;
    public final static int INSERT = 3;
    public final static int INSERT_ALL = 4;
    public final static int DELETE_ALL = 5;

    public DatabaseModel(AppDatabase database, int query) {
        this.database = database;
        this.query = query;
    }

    public DatabaseModel(AppDatabase database, int query, Purchase purchase) {
        this.database = database;
        this.query = query;
        this.purchase = purchase;
    }

    public DatabaseModel(AppDatabase database, int query, List<Purchase> purchaseList) {
        this.database = database;
        this.query = query;
        this.purchaseList = purchaseList;
    }

    @Override
    public List<Purchase> call() throws Exception {
        List<Purchase> list = new ArrayList<>();
        if(query == INSERT) database.getBase().insert(purchase);
        if(query == INSERT_ALL) database.getBase().insertAll(purchaseList);
        if(query == DELETE_ALL) database.getBase().deleteAll(purchaseList);
        if(query == PURCHASE) list = database.getBase().getAll();
        if(query == RECEIPT) list = database.getBase().getAll();
        return list;
    }
}
