package org.amazonclone.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import org.amazonclone.entity.Product;
import org.amazonclone.entity.User;
import org.amazonclone.rest.ProductRestController;
import org.amazonclone.service.ProductService;
import org.amazonclone.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;



@RestController
@Data
@RequestMapping("/api")
public class ProductRestControllerImpl implements ProductRestController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Value("${app.upload-dir:/var/uploads}")
    private String uploadDir;

    public ProductRestControllerImpl(ProductService productService, ObjectMapper objectMapper, UserService userService) {
        this.productService = productService;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }


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
    public Product addProduct(Product theProduct) {
        theProduct.setProdId(0L);
        return productService.save(theProduct);
    }


    @PostMapping("/products")
    public ResponseEntity<Map<String, String>> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("user") User user,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        if (image == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "an image is required"));
        }
        else if (user.getId() == null || user.getUserName() == null){
            return ResponseEntity.badRequest().body(Map.of("error", "only a user can add an item"));
        }
        else if (product.getProdName().length()>30 || product.getProdName().length()<5) {
            return ResponseEntity.badRequest().body(Map.of("error",
                    "the product name must be between 5 and 30 characters long"));
        }

        else if (product.getProdPrice() <= 0 || product.getProdPrice()>100000) {
            return ResponseEntity.badRequest().body(Map.of("error",
                    "the product price must be more than 0 and not more than 100000"));
        }
        else if (product.getAmount()<= 0 || product.getAmount()>10000) {
            return ResponseEntity.badRequest().body(Map.of("error",
                    "the amount must be at least 1 and not more than 10000"));
        }
        else if (image.getSize()> 20L * 1024*1024) {
            return ResponseEntity.badRequest().body(Map.of("error",
                    "the image is allowed to be at most 20MB in size"));
        }
        else if (product.getDescription().length()>1000) {
            return ResponseEntity.badRequest().body(Map.of("error",
                    "the description must be at most 1000 characters long"));
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.matches("image/(jpeg|png|webp)")) {
            return ResponseEntity.badRequest().body(Map.of("error", "only jpeg,png or webp files are allowed"));
        }

        // Map contentType to file extension
        String fileExtension = switch (contentType) {
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/webp" -> ".webp";
            default -> ".bin"; // Fallback (shouldn't happen due to contentType check)
        };
        User seller = userService.findById(user.getId());
        Path userUploadPath = Paths.get(uploadDir, seller.getUserName());
        if (!Files.exists(userUploadPath)) {
            Files.createDirectories(userUploadPath);
        }

        Path filePath = userUploadPath.resolve(product.getProdName() + fileExtension);
        Files.write(filePath, image.getBytes());

        // Set the file path (e.g., /uploads/username/Shirt.jpg)
        product.setProdPictureLink("/uploads/" + seller.getUserName() + "/" + product.getProdName() + fileExtension);

        // Save the product
        product.setProdId(null); // Ensure new product
        product.setSeller(seller);
        productService.save(product);
        return ResponseEntity.status(201).body(Map.of("message", "Product created"));
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


