/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import business.Platforms;
import java.util.Scanner;
import models.KOL;

/**
 *
 * @author thanh
 */
public class Inputter {
 private Scanner scanner;
    
    /**
     * Constructor initializing scanner
     */
    public Inputter() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Generic input method with validation
     * @param message Message to display to user
     * @param errorMsg Error message for invalid input
     * @param regex Validation regex pattern
     * @return Valid user input
     */
    public String input(String message, String errorMsg, String regex) {
        String result;
        boolean reInput = false;
        do {
            System.out.println(message);
            result = scanner.nextLine().trim();
            reInput = !Acceptable.isValid(result, regex);
            if (reInput) {
                System.out.println(errorMsg + ". Please re-enter...");
            }
        } while (reInput);
        return result;
    }
    
    /**
     * Input KOL information with validation
     * @param isUpdated true if updating existing KOL, false for new registration
     * @param platforms Platform collection for validation
     * @return KOL object with validated data
     */
    public KOL inputKOL(boolean isUpdated, Platforms platforms) {
        KOL kol = new KOL();
        
        // Input KOL ID (only for new registration)
        if (!isUpdated) {
            String message = "Enter KOL ID\n(8 characters: category code (BT/FS/BC/GM/TL) + 6 digits):";
            String errorMsg = "KOL ID must start with BT, FS, BC, GM, or TL followed by 6 digits";
            String regex = Acceptable.kolIdRegex;
            kol.setKolId(input(message, errorMsg, regex).toUpperCase());
        }
        
        // Input name
        String message = "Enter name (5-30 characters):";
        String errorMsg = "Name must be between 5 and 30 characters";
        String regex = Acceptable.nameRegex;
        kol.setName(input(message, errorMsg, regex));
        
        // Input phone number
        message = "Enter phone number (10 digits, Vietnamese operators):";
        errorMsg = "Invalid phone format! Must be 10 digits from Vietnamese operators";
        regex = Acceptable.phoneRegex;
        kol.setPhoneNumber(input(message, errorMsg, regex));
        
        // Input email
        message = "Enter email address:";
        errorMsg = "Invalid email format";
        regex = Acceptable.emailRegex;
        kol.setEmail(input(message, errorMsg, regex));
        
        // Input platform code with validation
        String platformCode;
        do {
            message = "Enter platform code (TK01, FB01, IG01, YT01):";
            errorMsg = "Platform code must be from the available list";
            regex = Acceptable.anything;
            platformCode = input(message, errorMsg, regex).toUpperCase();
            
            if (!platforms.contains(platformCode)) {
                System.out.println("Platform code not found! Available platforms:");
                platforms.showAll();
            }
        } while (!platforms.contains(platformCode));
        kol.setPlatformCode(platformCode);
        
        // Input follower count
        int followerCount;
        do {
            try {
                message = "Enter follower count (positive integer):";
                errorMsg = "Follower count must be a positive integer";
                regex = Acceptable.positiveInteger;
                String followerInput = input(message, errorMsg, regex);
                followerCount = Integer.parseInt(followerInput);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter a valid positive integer.");
            }
        } while (true);
        
        kol.setFollowerCount(followerCount);
        
        // Calculate and set commission rate
        int commissionRate = calculateCommissionRate(followerCount);
        kol.setCommissionRate(commissionRate);
        
        return kol;
    }
    
    /**
     * Calculate commission rate based on follower count
     * @param followerCount Number of followers
     * @return Commission rate (20 for <1M followers, 25 for â‰¥1M followers)
     */
    public int calculateCommissionRate(int followerCount) {
        if (followerCount >= 1000000) {
            return 25; // Premium rate for 1M+ followers
        } else {
            return 20; // Standard rate for <1M followers
        }
    }
    
    /**
     * Get category name from category code
     * @param categoryCode The 2-character category code
     * @return Full category name
     */
    public String getCategoryName(String categoryCode) {
        switch (categoryCode.toUpperCase()) {
            case "BT":
                return "Beauty";
            case "FS":
                return "Fashion";
            case "BC":
                return "Broadcasting";
            case "GM":
                return "Gaming";
            case "TL":
                return "Travel";
            default:
                return "Unknown";
        }
    }
}