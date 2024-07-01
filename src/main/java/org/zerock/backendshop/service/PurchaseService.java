package org.zerock.backendshop.service;

import org.zerock.backendshop.model.Purchase;
import org.zerock.backendshop.repository.projection.PurchaseItem;

import java.util.List;

public interface PurchaseService {

    Purchase savePurchase(Purchase purchase);

    List<PurchaseItem> findPurchaseItemOfUser(String username);

}
