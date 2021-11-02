package com.catdev.project.respository;

import com.catdev.project.entity.CustomerBuyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerBuyRepository extends JpaRepository<CustomerBuyEntity, Long> {
    @Query(value = "select sum(cs.quantity) from CustomerBuyEntity cs")
    Long totalQuantityInCustomerBuy();

    @Query(value = "select count(cs.quantity) from cus_buy cs",nativeQuery = true)
    Long totalQuantityInCustomerBuyNativeQuery();
}
