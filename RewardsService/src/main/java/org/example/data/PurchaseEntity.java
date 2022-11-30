package org.example.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rewardpointsdb")
public class PurchaseEntity {

    @Id
    private String customerId;
    private String date;
    private int rewardPointsEarned;

    public PurchaseEntity(){
        super();
    }

    public PurchaseEntity(String customerId, String date, int rewardPointsEarned){
        this();
        this.setCustomerId(customerId);
        this.setDate(date);
        this.setRewardPointsEarned(rewardPointsEarned);
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRewardPointsEarned() {
        return rewardPointsEarned;
    }

    public void setRewardPointsEarned(int rewardPointsEarned) {
        this.rewardPointsEarned = rewardPointsEarned;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "customerId='" + customerId + '\'' +
                ", date='" + date + '\'' +
                ", rewardPointsEarned=" + rewardPointsEarned +
                '}';
    }
}
