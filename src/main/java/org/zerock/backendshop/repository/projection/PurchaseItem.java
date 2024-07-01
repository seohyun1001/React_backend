package org.zerock.backendshop.repository.projection;

import java.time.LocalDateTime;

public interface PurchaseItem {

    String getName();

    Integer getQuantity();

    LocalDateTime getPurchaseTime();

}
