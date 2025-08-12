package org.amazonclone.service;

import org.amazonclone.entity.PurchaseHistory;

import java.util.List;

public interface PurchaseHistoryService {

    List<PurchaseHistory> findAll();

    List<PurchaseHistory> findByBuyerId(Long buyerId);

    PurchaseHistory findById(Long theId);

    PurchaseHistory save(PurchaseHistory thePurchaseHistory);

    void deleteById(int theId);
}

