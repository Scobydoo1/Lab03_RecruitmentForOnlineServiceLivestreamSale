/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author thanh
 */
public interface Acceptable {
     // KOL ID: 2 character category code + 6 digits
    public static String kolIdRegex = "^(BT|FS|BC|GM|TL)\\d{6}$";
    
    // Name: 5-30 characters, non-empty
    public static String nameRegex = "^.{5,30}$";
    
    // Email: standard email format
    public static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    // Vietnamese phone numbers (10 digits, major operators)
    // Viettel: 032-039, 086, 096-098
    // Mobifone: 070, 076-079, 089, 090, 093
    // Vinaphone: 081-085, 088, 091, 094
    // Vietnamobile: 052, 056, 058, 092
    public static String phoneRegex = "^(03[2-9]|086|09[6-8]|070|07[6-9]|089|090|093|08[1-5]|088|091|094|052|056|058|092)\\d{7}$";
    
    // Positive integer
    public static String positiveInteger = "^[1-9]\\d*$";
    
    // Platform codes from CSV
    public static String platformRegex = "^(TK01|FB01|IG01|YT01)$";
    
    // Accept anything (for optional updates)
    public static String anything = ".*";
    
    /**
     * Validate input data against regex pattern
     * @param data The data to validate
     * @param regex The regex pattern to match against
     * @return true if valid, false otherwise
     */
    public static boolean isValid(String data, String regex) {
        return data.matches(regex);
    }
}
