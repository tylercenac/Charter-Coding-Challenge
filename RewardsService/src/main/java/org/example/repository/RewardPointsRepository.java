package org.example.repository;

import org.example.data.PurchaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardPointsRepository extends CrudRepository<PurchaseEntity, String> {

    List<PurchaseEntity> findByCustomerId(String customerId);
}
