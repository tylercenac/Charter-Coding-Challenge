package org.example.service;

import org.example.data.PurchaseEntity;
import org.example.repository.RewardPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public String getTotalRewardsPointsEarnedByMonth() {

        List<PurchaseEntity> results = (List<PurchaseEntity>) rewardPointRepository.findAll();
        HashMap<String, List<PurchaseEntity>> customerMap = new HashMap<>();

        // For each purchase record
        for(PurchaseEntity result : results){
            String customerId = result.getCustomerId();
            List<PurchaseEntity> purchaseEntities;

            // Determine if customerMap already contains customer or not

            //if customerMap contains customer, add purchaseEntity to list
            if(customerMap.containsKey(customerId)){
                purchaseEntities = customerMap.get(customerId);

            }else{
                purchaseEntities = new ArrayList<>();

            }
            purchaseEntities.add(result);
            customerMap.put(customerId, purchaseEntities);

        }

        HashMap<String, HashMap<String, Integer>> customerRewardPointsMap = new HashMap<>();

        for(String customerId : customerMap.keySet()){
            HashMap<String, Integer> rewardPointsByMonth = getRewardPointsEarnedByMonth(customerMap.get(customerId));
            customerRewardPointsMap.put(customerId, rewardPointsByMonth);
        }

        return customerRewardPointsMap.toString();
    }


    public String getTotalRewardsPointsEarnedByCustomer(String customerId) {
        int totalRewardPointsEarned = 0;
        List<PurchaseEntity> results =  rewardPointRepository.findByCustomerId(customerId);

        for(PurchaseEntity result : results){
            totalRewardPointsEarned += result.getRewardPointsEarned();
        }

        return String.format("Customer %s has earned a total of %d reward points!", customerId, totalRewardPointsEarned);
    }

    public String getRewardsPointsEarnedByCustomerByMonth(String customerId) {
        List<PurchaseEntity> results =  rewardPointRepository.findByCustomerId(customerId);
        HashMap<String, Integer> rewardPointsMap = getRewardPointsEarnedByMonth(results);

        return rewardPointsMap.toString();
    }

    public HashMap<String, Integer> getRewardPointsEarnedByMonth(List<PurchaseEntity> purchaseEntities){

        HashMap<String, Integer> rewardPointsMap = new HashMap<>();

        for(PurchaseEntity purchaseEntity : purchaseEntities){
            int monthIndex = purchaseEntity.getDate().indexOf("-");
            int yearIndex = purchaseEntity.getDate().lastIndexOf("-");


            String monthAndYear = purchaseEntity.getDate().substring(0, monthIndex) + purchaseEntity.getDate().substring(yearIndex);

            if(rewardPointsMap.containsKey(monthAndYear)){
                rewardPointsMap.put(monthAndYear, rewardPointsMap.get(monthAndYear) + purchaseEntity.getRewardPointsEarned());
            }else{
                rewardPointsMap.put(monthAndYear, purchaseEntity.getRewardPointsEarned());
            }
        }

        return rewardPointsMap;
    }

}
