package org.example.controller;

import io.swagger.annotations.ApiParam;
import org.example.service.RewardPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardPointController {

    @Autowired
    private RewardPointService rewardPointService;

    @PostMapping("/purchase/{customerId}/{purchaseAmount}/{date}")
    public String savePurchaseRecord(@PathVariable String customerId, @PathVariable int purchaseAmount, @PathVariable String date){
        return rewardPointService.savePurchaseToDb(customerId, purchaseAmount, date);
    }

    @GetMapping("/reward-points")
    public String getTotalRewardsPointsEarned(){
        return rewardPointService.getTotalRewardsPointsEarned();
    }

    @GetMapping("/reward-points/by-month")
    public String getTotalRewardsPointsEarnedByMonth(){
        return rewardPointService.getTotalRewardsPointsEarnedByMonth();
    }

    @GetMapping("/reward-points/{customerId}")
    public String getTotalRewardsPointsEarnedByCustomer(@PathVariable String customerId){
        return rewardPointService.getTotalRewardsPointsEarnedByCustomer(customerId);
    }

    @GetMapping("/reward-points/by-month/{customerId}")
    public String getRewardsPointsEarnedByMonth(@PathVariable String customerId){
        return rewardPointService.getRewardsPointsEarnedByCustomerByMonth(customerId);
    }

}
