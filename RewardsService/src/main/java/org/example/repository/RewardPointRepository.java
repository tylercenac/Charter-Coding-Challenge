package org.example.repository;

import org.example.data.PurchaseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardPointRepository extends CrudRepository<PurchaseEntity, String> {
}
