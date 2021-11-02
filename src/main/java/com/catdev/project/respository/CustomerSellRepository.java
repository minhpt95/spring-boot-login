package com.catdev.project.respository;

import com.catdev.project.entity.CustomerSellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;

public interface CustomerSellRepository extends JpaRepository<CustomerSellEntity, Long> {
    @Query(value = "select sum(cs.quantity) from CustomerSellEntity cs")
    Long totalQuantityInCustomerSell();

    @Query(value = "select count(cs.quantity) from cus_sell cs",nativeQuery = true)
    Long totalQuantityInCustomerSellNative();
}
