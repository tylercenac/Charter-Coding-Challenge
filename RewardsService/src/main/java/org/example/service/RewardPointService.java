package org.example.service;

import org.example.data.PurchaseEntity;
import org.example.repository.RewardPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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

    public String getTotalRewardsPointsEarned() {

        List<PurchaseEntity> results = (List<PurchaseEntity>) rewardPointRepository.findAll();
        HashMap<String, Integer> rewardPointsMap = new HashMap<>();

        for(PurchaseEntity result : results){
            String customerId = result.getCustomerId();

            if(rewardPointsMap.containsKey(customerId)){
                rewardPointsMap.put(customerId, rewardPointsMap.get(customerId) + result.getRewardPointsEarned());
            }else{
                rewardPointsMap.put(customerId, result.getRewardPointsEarned());
            }
        }

        return rewardPointsMap.toString();
    }


    public String getTotalRewardsPointsEarnedByCustomer(String customerId) {
        int totalRewardPointsEarned = 0;
        List<PurchaseEntity> results =  rewardPointRepository.findByCustomerId(customerId);

        for(PurchaseEntity result : results){
            totalRewardPointsEarned += result.getRewardPointsEarned();
        }

        return String.format("Customer %s has earned a total of %d reward points!", customerId, totalRewardPointsEarned);
    }

    public String getRewardsPointsEarnedByCustomerByMonth(String date, String customerId) {



        return "Not yet implemented";
    }
}
