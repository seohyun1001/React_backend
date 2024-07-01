package org.zerock.backendshop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.backendshop.model.Purchase;
import org.zerock.backendshop.model.User;
import org.zerock.backendshop.repository.PurchaseRepository;
import org.zerock.backendshop.repository.UserRepository;
import org.zerock.backendshop.repository.projection.PurchaseItem;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

    @Override
    public Purchase savePurchase(Purchase purchase) {
        purchase.setPurchaseTime(LocalDateTime.now());
        return purchaseRepository.save(purchase);
    }

    @Override
    public List<PurchaseItem> findPurchaseItemOfUser(String username) {
        System.out.println("service~~~~~~~~~~"+username);
        User user = userRepository.findByUsername(username).orElseThrow();

        return purchaseRepository.findAllPurchaseOfUser(user.getId());
    }
}
