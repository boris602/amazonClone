package org.amazonclone.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.dao.PurchaseHistoryDao;
import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.service.PurchaseHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Data
@AllArgsConstructor
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {
    private PurchaseHistoryDao purchaseHistoryDao;

    @Override
    public List<PurchaseHistory> findAll() {
        return purchaseHistoryDao.findAll();
    }

    @Override
    public List<PurchaseHistory> findByBuyerId(Long buyerId) {
        return purchaseHistoryDao.findByBuyerId(buyerId);
    }

    @Override
    public PurchaseHistory findById(Long theId) {
        return purchaseHistoryDao.findById(theId);
    }

    @Override
    @Transactional
    public PurchaseHistory save(PurchaseHistory thePurchaseHistory) {
        return purchaseHistoryDao.save(thePurchaseHistory);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        purchaseHistoryDao.deleteById(theId);
    }
}
