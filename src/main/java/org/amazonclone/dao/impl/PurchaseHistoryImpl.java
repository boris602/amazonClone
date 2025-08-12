package org.amazonclone.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.amazonclone.dao.PurchaseHistoryDao;
import org.amazonclone.entity.PurchaseHistory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PurchaseHistoryImpl implements PurchaseHistoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PurchaseHistory> findAll() {
        return entityManager.createQuery(
                "SELECT ph FROM PurchaseHistory ph", PurchaseHistory.class).getResultList();
    }

    @Override
    public PurchaseHistory findById(Long theId) {
        return entityManager.find(PurchaseHistory.class, theId);
    }

    @Override
    public List<PurchaseHistory> findByBuyerId(Long buyerId) {
        TypedQuery<PurchaseHistory> query = entityManager.createQuery(
                "SELECT ph FROM PurchaseHistory ph WHERE ph.buyer.id = :buyerId", PurchaseHistory.class);
        query.setParameter("buyerId", buyerId);
        return query.getResultList();
    }

    @Override
    public PurchaseHistory save(PurchaseHistory thePurchaseHistory) {
        return entityManager.merge(thePurchaseHistory);
    }

    @Override
    public void deleteById(int theId) {
        entityManager.remove(entityManager.getReference(PurchaseHistory.class, theId));
    }
}
