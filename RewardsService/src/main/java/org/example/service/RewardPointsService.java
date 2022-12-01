package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.data.PurchaseEntity;
import org.example.repository.RewardPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardPointsService {

    @Autowired
    RewardPointsRepository rewardPointsRepository;

    // Stores purchase information in a PurchaseEntity object and saves the object to the Reward Points DB
    public String savePurchaseToDb(String customerId, int purchaseAmount, String date){
        int rewardPointsEarned = calculateRewardPoints(purchaseAmount);

        PurchaseEntity purchaseEntity = new PurchaseEntity(customerId, date, rewardPointsEarned);
        rewardPointsRepository.save(purchaseEntity);

        return String.format("Purchase for $%d by customer %s on %s saved! Customer has earned %d reward points for this purchase!", purchaseAmount, customerId, date, rewardPointsEarned);
    }

    // Return the number of reward points a customer earns from a purchase
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

        List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) rewardPointsRepository.findAll();
        HashMap<String, Integer> rewardPointsMap = new HashMap<>();

        for(PurchaseEntity purchaseEntity : purchaseEntities){
            String customerId = purchaseEntity.getCustomerId();

            if(rewardPointsMap.containsKey(customerId)){
                // If rewardPointsMap already contains this customerId, add the reward points earned from this purchase entity to the existing total
                rewardPointsMap.put(customerId, rewardPointsMap.get(customerId) + purchaseEntity.getRewardPointsEarned());
            }else{
                // Else add the new customerId to the rewardPointsMap with the reward points earned for just this purchase
                rewardPointsMap.put(customerId, purchaseEntity.getRewardPointsEarned());
            }
        }

        // Return results in a more readable format
        StringBuilder sb = new StringBuilder();
        for(String customerId : rewardPointsMap.keySet()){
            sb.append(String.format("Customer %s has earned a total of %d reward points.\n", customerId, rewardPointsMap.get(customerId)));
        }
        return sb.toString();
    }

    // Returns the list of all customers found in the database with a breakdown of how many points they have earned in each month
    public String getTotalRewardsPointsEarnedByMonth() {

        List<PurchaseEntity> purchaseEntities = (List<PurchaseEntity>) rewardPointsRepository.findAll();

        // customerMap will use customerId as the key and the value will be the list of all purchases made by that customer
        HashMap<String, List<PurchaseEntity>> customerMap = new HashMap<>();

        // For each purchase record
        for(PurchaseEntity purchaseEntity : purchaseEntities){
            String customerId = purchaseEntity.getCustomerId();
            List<PurchaseEntity> purchaseEntitiesForThisCustomer;

            // Determine if customerMap already contains customer or not
            if(customerMap.containsKey(customerId)){
                // If customerMap contains customer, grab the existing list of purchaseEntitiesForThisCustomer
                purchaseEntitiesForThisCustomer = customerMap.get(customerId);
            }else{
                // Else create a new a list that will store the purchaseEntitiesForThisCustomer
                purchaseEntitiesForThisCustomer = new ArrayList<>();

            }
            purchaseEntitiesForThisCustomer.add(purchaseEntity);
            customerMap.put(customerId, purchaseEntitiesForThisCustomer);

        }

        // customerRewardPointsMap will use the customerId as a key and the value will be another map
        // with its keys being the month and year of purchase (in mm-yyyy format) and its values being
        // the number of reward points earned that month
        HashMap<String, HashMap<String, Integer>> customerRewardPointsMap = new HashMap<>();

        // For each customer in the customer map, break down their reward points earned by month and store it in the customerRewardPointsMap
        for(String customerId : customerMap.keySet()){
            HashMap<String, Integer> rewardPointsByMonth = getRewardPointsEarnedByMonth(customerMap.get(customerId));
            customerRewardPointsMap.put(customerId, rewardPointsByMonth);
        }

        // Return results in a more readable format
        StringBuilder sb = new StringBuilder();
        for(String customerId : customerRewardPointsMap.keySet()){
            sb.append(String.format("Customer %s reward points break-down by month:\n", customerId));
            sb.append(customerRewardPointsMap.get(customerId) + "\n\n");
        }

        return sb.toString();
    }

    // Given a customerId, returns the total number of reward points that customer has earned
    public String getTotalRewardsPointsEarnedByCustomer(String customerId) {
        List<PurchaseEntity> results =  rewardPointsRepository.findByCustomerId(customerId);

        // Add all reward points earned from each purchase made by this customer
        int totalRewardPointsEarned = results.stream().mapToInt(PurchaseEntity::getRewardPointsEarned).sum();

        return String.format("Customer %s has earned a total of %d reward points!", customerId, totalRewardPointsEarned);
    }

    // Given a customerId, returns the number of reward points earned by a specific customer separated by month
    public String getRewardsPointsEarnedByCustomerByMonth(String customerId) {
        List<PurchaseEntity> results =  rewardPointsRepository.findByCustomerId(customerId);

        // Break down all reward points earned by month
        // rewardPointsMap will have a key of month and year in mm-yyyy format
        // and its value will be the number of reward points earned in that month
        HashMap<String, Integer> rewardPointsMap = getRewardPointsEarnedByMonth(results);

        return rewardPointsMap.toString();
    }

    // Given a list of purchaseEntities, returns the number of reward points earned separated by month
    public HashMap<String, Integer> getRewardPointsEarnedByMonth(List<PurchaseEntity> purchaseEntities){

        HashMap<String, Integer> rewardPointsMap = new HashMap<>();

        for(PurchaseEntity purchaseEntity : purchaseEntities){

            // Convert the date from each purchase entity from mm-dd-yyyy to mm-yyyy
            // as we only care about the month and year for this function
            int monthIndex = purchaseEntity.getDate().indexOf("-");
            int yearIndex = purchaseEntity.getDate().lastIndexOf("-");
            String monthAndYear = purchaseEntity.getDate().substring(0, monthIndex) + purchaseEntity.getDate().substring(yearIndex);

            if(rewardPointsMap.containsKey(monthAndYear)){
                // If rewardPointsMap already has an entry for this month, add the reward points earned in this purchase to the existing total
                rewardPointsMap.put(monthAndYear, rewardPointsMap.get(monthAndYear) + purchaseEntity.getRewardPointsEarned());
            }else{
                // Else add this month to rewardPointsMap with the reward points earned from this purchase
                rewardPointsMap.put(monthAndYear, purchaseEntity.getRewardPointsEarned());
            }
        }

        return rewardPointsMap;
    }

}
