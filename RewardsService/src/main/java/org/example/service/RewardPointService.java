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
    RewardPointRepository rewardPointsRepository;

    // Stores purchase information in a PurchaseEntity object and saves the object to the Reward Points DB
    public String savePurchaseToDb(String customerId, int purchaseAmount, String date){
        int rewardPointsEarned = calculateRewardPoints(purchaseAmount);

        PurchaseEntity purchaseEntity = new PurchaseEntity(customerId, date, rewardPointsEarned);
        rewardPointsRepository.save(purchaseEntity);

        return String.format("Purchase for $%d by customer %s on %s saved! Customer has earned %d reward points for this purchase!", purchaseAmount, customerId, date, rewardPointsEarned);
    }

    // Return the number of points a customer earns from a purchase
    public int calculateRewardPoints(int purchaseAmount){

        if(purchaseAmount > 100){
           return ((purchaseAmount - 100) * 2) + 50;
        }

        if(purchaseAmount > 50){
            return purchaseAmount - 50;
        }

        return 0;

    }

    // Returns the list of all customers found in the database with the total reward points they have each earned
    public String getTotalRewardsPointsEarned() {

        List<PurchaseEntity> results = (List<PurchaseEntity>) rewardPointsRepository.findAll();
        HashMap<String, Integer> rewardPointsMap = new HashMap<>();

        for(PurchaseEntity result : results){
            String customerId = result.getCustomerId();

            if(rewardPointsMap.containsKey(customerId)){
                rewardPointsMap.put(customerId, rewardPointsMap.get(customerId) + result.getRewardPointsEarned());
            }else{
                rewardPointsMap.put(customerId, result.getRewardPointsEarned());
            }
        }

        StringBuilder sb = new StringBuilder();
        for(String customerId : rewardPointsMap.keySet()){
            sb.append(String.format("Customer %s has earned a total of %d reward points.\n", customerId, rewardPointsMap.get(customerId)));
        }
        return sb.toString();
    }

    // Returns the list of all customers found in the database with a breakdown of how many points they have earned in each month
    public String getTotalRewardsPointsEarnedByMonth() {

        List<PurchaseEntity> results = (List<PurchaseEntity>) rewardPointsRepository.findAll();
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

        StringBuilder sb = new StringBuilder();
        for(String customerId : customerRewardPointsMap.keySet()){
            sb.append(String.format("Customer %s reward points break-down by month:\n", customerId));
            sb.append(customerRewardPointsMap.get(customerId) + "\n\n");
        }

        return sb.toString();
    }

    // Given a customerId, returns the total number of reward points that customer has earned
    public String getTotalRewardsPointsEarnedByCustomer(String customerId) {
        int totalRewardPointsEarned = 0;
        List<PurchaseEntity> results =  rewardPointsRepository.findByCustomerId(customerId);

        for(PurchaseEntity result : results){
            totalRewardPointsEarned += result.getRewardPointsEarned();
        }

        return String.format("Customer %s has earned a total of %d reward points!", customerId, totalRewardPointsEarned);
    }

    // Given a customerId, returns the number of reward points earned by a specific customer separated by month
    public String getRewardsPointsEarnedByCustomerByMonth(String customerId) {
        List<PurchaseEntity> results =  rewardPointsRepository.findByCustomerId(customerId);
        HashMap<String, Integer> rewardPointsMap = getRewardPointsEarnedByMonth(results);

        return rewardPointsMap.toString();
    }

    // Given a list of purchaseEntities, returns the number of reward points earned separated by month
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
