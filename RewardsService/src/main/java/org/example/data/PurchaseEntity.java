package org.example.data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="rewardpointsdb")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String purchaseId;
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

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
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
