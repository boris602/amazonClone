package org.amazonclone.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.amazonclone.entity.PurchaseHistory;
import org.amazonclone.rest.PurchaseHistoryRestController;
import org.amazonclone.service.PurchaseHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Data
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class PurchaseHistoryRestControllerImpl implements PurchaseHistoryRestController {

    private PurchaseHistoryService purchaseHistoryService;
    private ObjectMapper objectMapper;


    @Override
    @GetMapping("/purchaseHistory")
    public List<PurchaseHistory> findAll() {
        return purchaseHistoryService.findAll();
    }

    @Override
    @GetMapping("/purchaseHistory/{purchaseHistoryId}")
    public PurchaseHistory getPurchaseHistory(@PathVariable  Long purchaseHistoryId) {
        PurchaseHistory thePurchaseHistory = purchaseHistoryService.findById(purchaseHistoryId);
        if (thePurchaseHistory == null) {
            throw new RuntimeException("PurchaseHistory-Id not found - " + purchaseHistoryId);
        }
        return thePurchaseHistory;
    }

    @Override
    @PostMapping("/purchaseHistory")
    public PurchaseHistory addPurchaseHistory(PurchaseHistory purchaseHistory) {
        return purchaseHistoryService.save(purchaseHistory);
    }


    @Override
    @DeleteMapping("/purchaseHistory/{purchaseHistoryId}")
    public PurchaseHistory deletePurchaseHistory(@PathVariable Long purchaseHistoryId) {
        PurchaseHistory tempPurchaseHistory = purchaseHistoryService.findById(purchaseHistoryId);
        if (tempPurchaseHistory == null){
            throw new RuntimeException("PurchaseHistory-Id not found - " + purchaseHistoryId);
        }
        return tempPurchaseHistory;
    }
}
