/** Model for Tab **/
package com.bar.JAR.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Tab")
public class Tab {

    @Id
    @Column(name = "tab_id")
    private int tabID;
    
    @Column(name = "is_open")
    private boolean isOpen;

    @Column(name = "tab_amount")
    private double tabAmount;

    @Column(name = "money_spent")
    private double moneySpent;

    @Column(name = "signature_drink")
    private String signatureDrink;

    public Tab() {
    }

    public Tab(int tabID,boolean isOpen, double tabAmount, double moneySpent, String signatureDrink) {
        this.tabID = tabID;
        this.isOpen = isOpen;
        this.tabAmount = tabAmount;
        this.moneySpent = moneySpent;
        this.signatureDrink = signatureDrink;
    }

    public int getTabID() {
        return tabID;
    }

    public void setTabID(int tabID) {
        this.tabID = tabID;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public double getTabAmount() {
        return tabAmount;
    }

    public void setTabAmount(double tabAmount) {
        this.tabAmount = tabAmount;
    }

    public double getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(double moneySpent) {
        this.moneySpent = moneySpent;
    }

    public String getSignatureDrink() {
        return signatureDrink;
    }

    public void setSignatureDrink(String signatureDrink) {
        this.signatureDrink = signatureDrink;
    }
}
