package org.amazonclone.rest;

import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.entity.User;

import java.util.List;
import java.util.Map;

public interface PurchaseHistoryRestController {

    public List<PurchaseHistory> findAll();

    public PurchaseHistory getPurchaseHistory (Long purchaseHistoryId);

    public PurchaseHistory addPurchaseHistory(PurchaseHistory purchaseHistory);


    public PurchaseHistory deletePurchaseHistory(Long theId);
}
