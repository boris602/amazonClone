package org.amazonclone.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.amazonclone.dao.ProductDao;
import org.amazonclone.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public List<Product> findBySellerId(Long sellerId) {
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT prod FROM Product prod WHERE prod.seller.id = :sellerId", Product.class);
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }


    @Override
    public Product findById(Long theId) {
        return entityManager.find(Product.class, theId);
    }

    @Override
    public Product save(Product theProduct) {
        return entityManager.merge(theProduct);
    }

    @Override
    public void deleteById(int theId) {

    }
}
