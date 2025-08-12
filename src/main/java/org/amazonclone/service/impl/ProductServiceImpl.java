package org.amazonclone.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.dao.ProductDao;
import org.amazonclone.entity.Product;
import org.amazonclone.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findBySellerId(Long sellerId) {
        return productDao.findBySellerId(sellerId);
    }

    @Override
    public Product findById(Long theId) {
        return productDao.findById(theId);
    }

    @Override
    @Transactional
    public Product save(Product theProduct) {
        return productDao.save(theProduct);
    }

    @Override
    @Transactional
    public void deleteById(int theId) {
        productDao.deleteById(theId);
    }
}


