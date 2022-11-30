package org.example.service;

import org.springframework.stereotype.Service;

@Service
public class RewardPointService {

    public int calculateRewardPoints(int purchaseAmount){

        if(purchaseAmount > 100){
           return ((purchaseAmount - 100) * 2) + 50;
        }

        if(purchaseAmount <= 100 && purchaseAmount > 50){
            return purchaseAmount - 50;
        }

        return 0;

    }


}
