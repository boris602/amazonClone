package org.amazonclone.service;

import org.amazonclone.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    List<Product> findBySellerId(Long sellerId);

    Product findById(Long theId);

    Product save(Product theProduct);

    void deleteById(int theId);
}
