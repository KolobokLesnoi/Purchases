package koloboklesnoi.purchases.presenter;

import koloboklesnoi.purchases.database.Purchase;

import java.util.List;

public interface MvpView {
    void showPurchaseList(List<Purchase> purchases);
    void showReceiptList(List<Purchase> receipt);
}
