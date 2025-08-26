/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author thanh
 */
public class KOL implements Serializable {

    private String kolId;
    private String name;
    private String phoneNumber;
    private String email;
    private String platformCode;
    private int followerCount;
    private int commissionRate;

    public KOL() {
    }

    public KOL(String kolId, String name, String phoneNumber, String email,
            String platformCode, int followerCount, int commissionRate) {
        this.kolId = kolId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.platformCode = platformCode;
        this.followerCount = followerCount;
        this.commissionRate = commissionRate;
    }
     public String getKolId() {
        return kolId;
    }
    
    public void setKolId(String kolId) {
        this.kolId = kolId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPlatformCode() {
        return platformCode;
    }
    
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }
    
    public int getFollowerCount() {
        return followerCount;
    }
    
    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }
    
    public int getCommissionRate() {
        return commissionRate;
    }
    
    public void setCommissionRate(int commissionRate) {
        this.commissionRate = commissionRate;
    }
     public void display() {
        System.out.format("%-20s: %s%n", "KOL ID", this.getKolId());
        System.out.format("%-20s: %s%n", "Name", this.getName());
        System.out.format("%-20s: %s%n", "Phone", this.getPhoneNumber());
        System.out.format("%-20s: %s%n", "Email", this.getEmail());
        System.out.format("%-20s: %s%n", "Platform", this.getPlatformCode());
        System.out.format("%-20s: %,d%n", "Followers", this.getFollowerCount());
        System.out.format("%-20s: %d%%%n", "Commission", this.getCommissionRate());
    }
    
    @Override
    public String toString() {
        return "KOL{" + "kolId=" + kolId + ", name=" + name + ", phoneNumber=" + phoneNumber + 
               ", email=" + email + ", platformCode=" + platformCode + ", followerCount=" + 
               followerCount + ", commissionRate=" + commissionRate + '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KOL other = (KOL) obj;
        return Objects.equals(this.kolId.toLowerCase(), other.kolId.toLowerCase());
    }
      @Override
    public int hashCode() {
        return Objects.hash(kolId.toLowerCase());
    }
}
