package org.amazonclone.rest;

import org.amazonclone.entity.Product;
import org.amazonclone.entity.User;

import java.util.List;
import java.util.Map;

public interface ProductRestController {

    public List<Product> findAll();

    public List<Product> findBySellerId(Long userId);

    public Product getProduct (Long productId);

    public Product addProduct(Product theProduct);

    public Product updateProduct(Product theProduct);

    public Product patchProduct(Long productId,
                          Map<String, Object> patchPayload);

    public Product deleteProduct(Long theId);
}
