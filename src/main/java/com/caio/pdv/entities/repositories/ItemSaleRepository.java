package com.caio.pdv.entities.repositories;

import com.caio.pdv.entities.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepository extends JpaRepository<ItemSale, Long> {
}
