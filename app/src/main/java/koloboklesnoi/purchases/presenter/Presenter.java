package koloboklesnoi.purchases.presenter;

import koloboklesnoi.purchases.database.AppDatabase;
import koloboklesnoi.purchases.database.Purchase;
import koloboklesnoi.purchases.model.DatabaseModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import java.util.List;

public class Presenter {

    private MvpView view;
    private AppDatabase purchaseDatabase;
    private AppDatabase receiptDatabase;

    public Presenter(MvpView view, AppDatabase purchaseDatabase, AppDatabase receiptDatabase) {
        this.view = view;
        this.purchaseDatabase = purchaseDatabase;
        this.receiptDatabase = receiptDatabase;
    }

    public void getPurchaseList(){
        setCallable(new DatabaseModel(purchaseDatabase,DatabaseModel.PURCHASE), DatabaseModel.PURCHASE);
    }

    public void getReceiptList(){
        setCallable(new DatabaseModel(receiptDatabase,DatabaseModel.PURCHASE), DatabaseModel.RECEIPT);
    }

    public void insertToPurhcase(Purchase purchase){
        setCallable(new DatabaseModel(purchaseDatabase, DatabaseModel.INSERT, purchase), DatabaseModel.PURCHASE);
    }

    public void insertAllToPurchase(List<Purchase> purchase){
        setCallable(new DatabaseModel(purchaseDatabase, DatabaseModel.INSERT_ALL, purchase), DatabaseModel.PURCHASE);
    }

    public void insertAllToReceipt(List<Purchase> purchase){
        setCallable(new DatabaseModel(receiptDatabase, DatabaseModel.INSERT_ALL, purchase), DatabaseModel.RECEIPT);
    }

    public void deleteAllFromPurchase(List<Purchase> purchaseList){
        setCallable(new DatabaseModel(purchaseDatabase,DatabaseModel.DELETE_ALL,purchaseList), DatabaseModel.PURCHASE);
    }

    public void deleteAllFromReceipt(List<Purchase> purchaseList){
        setCallable(new DatabaseModel(receiptDatabase,DatabaseModel.DELETE_ALL, purchaseList), DatabaseModel.RECEIPT);
    }

    private void setCallable(DatabaseModel databaseModel, final int query){
        Observable.fromCallable(databaseModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Purchase>>() {
                    @Override
                    public void call(List<Purchase> purchases) {
                        if(query == DatabaseModel.PURCHASE) view.showPurchaseList(purchases);
                        if(query == DatabaseModel.RECEIPT) view.showReceiptList(purchases);
                    }
                });
    }
}
