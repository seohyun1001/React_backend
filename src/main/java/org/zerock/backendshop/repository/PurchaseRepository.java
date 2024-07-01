package org.zerock.backendshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.backendshop.model.Purchase;
import org.zerock.backendshop.repository.projection.PurchaseItem;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select " +
            "prd.name as name,prd.price as price, pur.quantity as quantity, pur.purchaseTime as purchaseTime " +
            "from Purchase pur left join Product prd on prd.id = pur.productId " +
            "where pur.userId = :userId" )
    List<PurchaseItem> findAllPurchaseOfUser(@Param("userId") Long userId);

}
