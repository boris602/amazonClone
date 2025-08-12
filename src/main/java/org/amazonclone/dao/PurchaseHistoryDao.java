package org.amazonclone.dao;

import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.entity.User;

import java.util.List;


public interface PurchaseHistoryDao {

        List<PurchaseHistory> findAll();

        List<PurchaseHistory> findByBuyerId(Long userId);

        PurchaseHistory findById(Long theId);

        PurchaseHistory save(PurchaseHistory thePurchaseHistory);

        public void deleteById(int theId);
}
