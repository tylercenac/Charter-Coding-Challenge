package org.example.controller;

import org.example.service.RewardPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardPointController {

    @Autowired
    private RewardPointService rewardPointService;

    @GetMapping
    public String test(){
        return "testSuccessful";
    }

    @GetMapping("/rewardPoints/{purchaseAmount}")
    public int getPointsFromPurchase(@PathVariable int purchaseAmount){
        return rewardPointService.calculateRewardPoints(purchaseAmount);
    }

}
