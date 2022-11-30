package org.example.service;

import org.example.data.PurchaseEntity;
import org.example.repository.RewardPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardPointService {

    @Autowired
    RewardPointRepository rewardPointRepository;

    public String savePurchaseToDb(String customerId, int purchaseAmount, String date){
        int rewardPointsEarned = calculateRewardPoints(purchaseAmount);

        PurchaseEntity purchaseEntity = new PurchaseEntity(customerId, date, rewardPointsEarned);
        rewardPointRepository.save(purchaseEntity);

        return String.format("Purchase for $%d by customer %s on %s saved! Customer has earned %d reward points for this purchase!", purchaseAmount, customerId, date, rewardPointsEarned);
    }

    public int calculateRewardPoints(int purchaseAmount){

        if(purchaseAmount > 100){
           return ((purchaseAmount - 100) * 2) + 50;
        }

        if(purchaseAmount > 50){
            return purchaseAmount - 50;
        }

        return 0;

    }


    public String getTotalRewardsPointsEarned(String customerId) {

        return "Not yet implemented";
    }

    public String getRewardsPointsEarnedByMonth(String date, String customerId) {

        return "Not yet implemented";
    }
}
