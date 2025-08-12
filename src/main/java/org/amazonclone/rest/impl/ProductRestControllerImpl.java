package org.amazonclone.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.entity.Product;
import org.amazonclone.rest.ProductRestController;
import org.amazonclone.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@Data
@AllArgsConstructor
@RequestMapping("/api")
public class ProductRestControllerImpl implements ProductRestController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    @Override
    @GetMapping("/products")
    public List<Product> findAll() {
        return productService.findAll();
    }

    @Override
    @GetMapping("/products/userProducts/{sellerId}")
    public List<Product> findBySellerId(@PathVariable Long sellerId) {
        return productService.findBySellerId(sellerId);
    }

    @Override
    @GetMapping("/products/singleProduct/{productId}")
    public Product getProduct(Long productId) {
        Product theProduct = productService.findById(productId);
        if (theProduct == null) {
            throw new RuntimeException("Product-Id not found - " + productId);
        }
        return theProduct;
    }

    @Override
    @PostMapping("/products")
    public Product addProduct(Product theProduct) {
        theProduct.setProdId(0L);
        return productService.save(theProduct);
    }

    @Override
    @PutMapping("/products")
    public Product updateProduct(Product theProduct) {
        return productService.save(theProduct);
    }

    @Override
    public Product patchProduct(Long productId, Map<String, Object> patchPayload) {
        Product tempProduct = productService.findById(productId);
        if (tempProduct == null){
            throw new RuntimeException("Product-Id not found - " + productId);
        }
        else if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Product-Id not allowed in request body - " + productId);
        }
        return apply(patchPayload,tempProduct);
    }



    private Product apply(Map<String, Object> patchPayload, Product tempProduct) {

        ObjectNode productNode = objectMapper.convertValue(tempProduct, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);
        productNode.setAll(patchNode);

        return objectMapper.convertValue(productNode, Product.class);
    }


    @Override
    @DeleteMapping("/products/{productId}")
    public Product deleteProduct(@PathVariable Long productId) {
        Product tempProduct = productService.findById(productId);
        if (tempProduct == null){
            throw new RuntimeException("Product-Id not found - " + productId);
        }
        return tempProduct;
    }
}


