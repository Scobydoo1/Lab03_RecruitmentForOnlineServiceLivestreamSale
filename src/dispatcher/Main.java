/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispatcher;

import business.KOLs;
import business.Platforms;
import java.util.Scanner;
import java.util.Set;
import models.KOL;
import tools.Inputter;

/**
 *
 * @author thanh
 */
public class Main {
   // Menu constants
    private static final int NEW_REGISTRATION = 1;
    private static final int UPDATE_REGISTRATION = 2;
    private static final int DISPLAY_REGISTERED_LIST = 3;
    private static final int DELETE_REGISTRATION = 4;
    private static final int SEARCH_BY_NAME = 5;
    private static final int FILTER_BY_CATEGORY = 6;
    private static final int STATISTICS_BY_PLATFORM = 7;
    private static final int SAVE_DATA_TO_FILE = 8;
    private static final int EXIT_PROGRAM = 9;
    
    // File paths
    private static final String KOLS_FILE = "kol_registrations.dat";
    private static final String PLATFORM_FILE = "KOLList.csv";
    
    // System components
    private static Inputter inputter;
    private static KOLs kols;
    private static Platforms platforms;
    private static Scanner scanner;
    
    /**
     * Initialize system components
     */
    private static void initializeSystem() {
        System.out.println("Initializing KOL Management System...");
        inputter = new Inputter();
        kols = new KOLs(KOLS_FILE);
        platforms = new Platforms(PLATFORM_FILE);
        scanner = new Scanner(System.in);
        System.out.println("System initialized successfully!\n");
    }
    
    /**
     * Display the main menu options
     */
    private static void displayMainMenu() {
        System.out.println("\n==================== KOL MANAGEMENT SYSTEM ====================");
        System.out.println("1. New Registration");
        System.out.println("2. Update Registration Information");
        System.out.println("3. Display Registered List");
        System.out.println("4. Delete Registration Information");
        System.out.println("5. Search KOLs by Name");
        System.out.println("6. Filter Data by Category");
        System.out.println("7. Statistics of Registration Numbers by Platform");
        System.out.println("8. Save Data to File");
        System.out.println("9. Exit the Program");
        System.out.println("================================================================");
        System.out.print("Enter your choice (1-9): ");
    }
    
    /**
     * Get menu choice from user with validation
     * @return Valid menu choice (1-9)
     */
    private static int getMenuChoice() {
        int choice = 0;
        boolean validInput = false;
        
        do {
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= 9) {
                    validInput = true;
                } else {
                    System.out.println("Invalid choice! Please enter a number between 1 and 9.");
                    System.out.print("Enter your choice: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                System.out.print("Enter your choice: ");
            }
        } while (!validInput);
        
        return choice;
    }
    
    /**
     * Main menu loop
     */
    private static void runMainMenu() {
        int choice;
        do {
            displayMainMenu();
            choice = getMenuChoice();
            processMenuChoice(choice);
        } while (choice != EXIT_PROGRAM);
    }
    
    /**
     * Handle new KOL registration
     */
    private static void handleNewRegistration() {
        System.out.println("\n=== NEW KOL REGISTRATION ===");
        
        try {
            KOL newKOL = inputter.inputKOL(false, platforms);
            kols.addNew(newKOL);
            
            System.out.println("\nRegistration Summary:");
            newKOL.display();
            
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle KOL registration update
     */
    private static void handleUpdateRegistration() {
        System.out.println("\n=== UPDATE KOL REGISTRATION ===");
        
        System.out.print("Enter KOL ID to update: ");
        String kolId = scanner.nextLine().trim().toUpperCase();
        
        KOL existingKOL = kols.searchById(kolId);
        if (existingKOL == null) {
            System.out.println("This KOL has not registered yet.");
        } else {
            System.out.println("Current KOL Information:");
            existingKOL.display();
            
            System.out.println("\nEnter updated information:");
            KOL updatedKOL = inputter.inputKOL(true, platforms);
            updatedKOL.setKolId(existingKOL.getKolId()); // Keep original ID
            
            kols.update(updatedKOL);
            
            System.out.println("\nUpdated Information:");
            updatedKOL.display();
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle display of all registered KOLs
     */
    private static void handleDisplayList() {
        System.out.println("\n=== REGISTERED KOL LIST ===");
        kols.showAll();
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle KOL registration deletion
     */
    private static void handleDeleteRegistration() {
        System.out.println("\n=== DELETE KOL REGISTRATION ===");
        
        System.out.print("Enter KOL ID to delete: ");
        String kolId = scanner.nextLine().trim().toUpperCase();
        
        kols.deleteById(kolId);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle search KOLs by name
     */
    private static void handleSearchByName() {
        System.out.println("\n=== SEARCH KOLS BY NAME ===");
        
        System.out.print("Enter name or partial name to search: ");
        String searchName = scanner.nextLine().trim();
        
        Set<KOL> foundKOLs = kols.filterByName(searchName);
        
        if (foundKOLs.isEmpty()) {
            System.out.println("No one matches the search criteria!");
        } else {
            System.out.println("Matching KOLs:");
            kols.show(foundKOLs);
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle filter KOLs by category
     */
    private static void handleFilterByCategory() {
        System.out.println("\n=== FILTER KOLS BY CATEGORY ===");
        System.out.println("Available Categories:");
        System.out.println("BT - Beauty");
        System.out.println("FS - Fashion");
        System.out.println("BC - Broadcasting");
        System.out.println("GM - Gaming");
        System.out.println("TL - Travel");
        
        System.out.print("Enter category code (BT/FS/BC/GM/TL): ");
        String categoryCode = scanner.nextLine().trim().toUpperCase();
        
        if (!categoryCode.matches("^(BT|FS|BC|GM|TL)$")) {
            System.out.println("Invalid category code!");
        } else {
            Set<KOL> filteredKOLs = kols.filterByCategory(categoryCode);
            
            if (filteredKOLs.isEmpty()) {
                System.out.println("No KOLs have registered under this category.");
            } else {
                String categoryName = inputter.getCategoryName(categoryCode);
                System.out.println("Registered KOLs Under " + categoryName + " Category (" + categoryCode + "):");
                kols.show(filteredKOLs);
            }
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle display of registration statistics by platform
     */
    private static void handleStatistics() {
        System.out.println("\n=== REGISTRATION STATISTICS BY PLATFORM ===");
        kols.getStatisticsByPlatform(platforms);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle save data to file
     */
    private static void handleSaveData() {
        System.out.println("\n=== SAVE DATA TO FILE ===");
        kols.saveToFile();
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Handle program exit with unsaved changes check
     */
    private static void handleExit() {
        System.out.println("\n=== EXIT PROGRAM ===");
        
        if (!kols.isSaved()) {
            System.out.print("Do you want to save the changes before exiting? (Y/N): ");
            String saveChoice = scanner.nextLine().trim();
            
            if (saveChoice.equalsIgnoreCase("Y") || saveChoice.equalsIgnoreCase("YES")) {
                kols.saveToFile();
            } else {
                System.out.print("You have unsaved changes. Are you sure you want to exit without saving? (Y/N): ");
                String confirmExit = scanner.nextLine().trim();
                
                if (!(confirmExit.equalsIgnoreCase("Y") || confirmExit.equalsIgnoreCase("YES"))) {
                    return; // Don't exit, return to menu
                }
            }
        }
        
        System.out.println("Thank you for using KOL Management System!");
        System.out.println("Program terminated successfully.");
    }
    
    /**
     * Process menu choice and call appropriate handler
     * @param choice User's menu choice
     */
    private static void processMenuChoice(int choice) {
        switch (choice) {
            case NEW_REGISTRATION:
                handleNewRegistration();
                break;
            case UPDATE_REGISTRATION:
                handleUpdateRegistration();
                break;
            case DISPLAY_REGISTERED_LIST:
                handleDisplayList();
                break;
            case DELETE_REGISTRATION:
                handleDeleteRegistration();
                break;
            case SEARCH_BY_NAME:
                handleSearchByName();
                break;
            case FILTER_BY_CATEGORY:
                handleFilterByCategory();
                break;
            case STATISTICS_BY_PLATFORM:
                handleStatistics();
                break;
            case SAVE_DATA_TO_FILE:
                handleSaveData();
                break;
            case EXIT_PROGRAM:
                handleExit();
                break;
            default:
                System.out.println("This function is not available.");
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
                break;
        }
    }
    
    /**
     * Main method - program entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        try {
            initializeSystem();
            runMainMenu();
        } catch (Exception e) {
            System.out.println("System error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}