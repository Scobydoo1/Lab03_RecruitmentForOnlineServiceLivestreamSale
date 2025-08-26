/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import models.KOL;
import models.Platform;

/**
 *
 * @author thanh
 */
public class KOLs extends HashSet<KOL> implements Workable<KOL>, Serializable {

    private String pathFile;
    private boolean saved;

    public KOLs(String pathFile) {
        this.pathFile = pathFile;
        this.saved = true;

    }

    private void init() {
        HashSet<KOL> result = readFromFile();
        this.clear();
        this.addAll(result);
    }

    public boolean isDuplicated(KOL k) {
        return this.contains(k);
    }

    @Override
    public void addNew(KOL t) {
        if (!this.isDuplicated(t)) {
            this.add(t);
            this.saved = false;
            System.out.println("KOL registration successful!");
        } else {
            System.out.println("Error: KOL with this ID already exists!");
        }
    }

    @Override
    public void update(KOL t) {
        this.remove(t);
        this.add(t);
        this.saved = false;
        System.out.println("KOL information updated successfully!");
    }

    @Override
    public KOL searchById(String id) {
        KOL result = null;
        for (KOL k : this) {
            if (k.getKolId().equalsIgnoreCase(id)) {
                result = k;
                break;
            }
        }
        return result;
    }

    public void show(Set<KOL> list) {
        if (list.isEmpty()) {
            System.out.println("No KOLs have registered yet.");
            return;
        }

        System.out.println("==============================================================================");
        System.out.format("%-10s | %-20s | %-12s | %-15s | %-10s | %-10s%n",
                "KOL ID", "Name", "Phone", "Platform", "Followers", "Commission");
        System.out.println("==============================================================================");

        for (KOL k : list) {
            System.out.format("%-10s | %-20s | %-12s | %-15s | %-10s | %-10s%n",
                    k.getKolId(), k.getName(), k.getPhoneNumber(),
                    k.getPlatformCode(),
                    String.format("%,d", k.getFollowerCount()),
                    k.getCommissionRate() + "%");
        }
        System.out.println("==============================================================================");
    }

  @Override
    public void showAll() {
        show(this);
    }
    
    /**
     * Filter KOLs by name (partial or full match)
     * @param name The name or partial name to search for
     * @return Set of matching KOLs
     */
    public Set<KOL> filterByName(String name) {
        Set<KOL> result = new HashSet<>();
        for (KOL k : this) {
            if (k.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(k);
            }
        }
        return result;
    }
    
    /**
     * Filter KOLs by category code
     * @param categoryCode The category code to filter by (BT, FS, BC, GM, TL)
     * @return Set of KOLs in the specified category
     */
    public Set<KOL> filterByCategory(String categoryCode) {
        Set<KOL> result = new HashSet<>();
        for (KOL k : this) {
            if (k.getKolId().toUpperCase().startsWith(categoryCode.toUpperCase())) {
                result.add(k);
            }
        }
        return result;
    }
    
    /**
     * Generate and display statistics of registrations by platform
     * @param platforms The platforms collection for platform name lookup
     */
    public void getStatisticsByPlatform(Platforms platforms) {
        if (this.isEmpty()) {
            System.out.println("No registration data available for statistics.");
            return;
        }
        
        Map<String, Integer> platformCounts = new HashMap<>();
        Map<String, Double> platformCommissionSums = new HashMap<>();
        
        // Calculate statistics for each platform
        for (KOL k : this) {
            String platformCode = k.getPlatformCode();
            platformCounts.put(platformCode, platformCounts.getOrDefault(platformCode, 0) + 1);
            platformCommissionSums.put(platformCode, 
                    platformCommissionSums.getOrDefault(platformCode, 0.0) + k.getCommissionRate());
        }
        
        // Display statistics
        System.out.println("Statistics of Registration by Platform:");
        System.out.println("=======================================================");
        System.out.format("%-20s | %-15s | %-20s%n", "Platform", "Number of KOLs", "Avg. Commission Rate");
        System.out.println("=======================================================");
        
        for (String platformCode : platformCounts.keySet()) {
            Platform platform = platforms.searchById(platformCode);
            String platformName = (platform != null) ? platform.getPlatformName() : "Unknown";
            int count = platformCounts.get(platformCode);
            double avgCommission = platformCommissionSums.get(platformCode) / count;
            
            System.out.format("%-20s | %-15d | %-20.1f%%%n",
                    platformName + " (" + platformCode + ")", count, avgCommission);
        }
        System.out.println("=======================================================");
    }
    
    /**
     * Delete a KOL by ID with confirmation
     * @param kolId The ID of KOL to delete
     * @return true if deleted, false if not found or cancelled
     */
    public boolean deleteById(String kolId) {
        KOL kol = searchById(kolId);
        if (kol == null) {
            System.out.println("This KOL has not registered yet.");
            return false;
        }
        
        // Display KOL details
        System.out.println("KOL Details:");
        kol.display();
        
        // Ask for confirmation
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Are you sure you want to delete this registration? (Y/N): ");
        String confirmation = scanner.nextLine().trim();
        
        if (confirmation.equalsIgnoreCase("Y") || confirmation.equalsIgnoreCase("YES")) {
            this.remove(kol);
            this.saved = false;
            System.out.println("The registration has been successfully deleted.");
            return true;
        } else {
            System.out.println("Deletion cancelled.");
            return false;
        }
    }
    
    /**
     * Check if data has been saved
     * @return true if saved, false if unsaved changes exist
     */
    public boolean isSaved() {
        return saved;
    }
    
    /**
     * Save KOL data to file using object serialization
     */
    public void saveToFile() {
        if (this.saved) {
            System.out.println("No changes to save.");
            return;
        }
        
        try {
            // Create file if it doesn't exist
            File f = new File(pathFile);
            if (!f.exists()) {
                f.createNewFile();
            }
            
            // Create file output stream
            FileOutputStream fos = new FileOutputStream(f);
            
            // Create object output stream
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            // Write data to file
            oos.writeObject(this);
            
            // Close streams
            oos.close();
            fos.close();
            
            // Update saved status
            this.saved = true;
            System.out.println("Registration data has been successfully saved to " + pathFile);
            
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Read KOL data from file
     * @return HashSet of KOLs read from file
     */
    public HashSet<KOL> readFromFile() {
        HashSet<KOL> result = new HashSet<>();
        try {
            // Create file object
            File f = new File(pathFile);
            if (!f.exists()) {
                return result; // Return empty set if file doesn't exist
            }
            
            // Create file input stream
            FileInputStream fis = new FileInputStream(f);
            
            // Create object input stream
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            // Read data from file
            result = (HashSet<KOL>) ois.readObject();
            
            // Close streams
            ois.close();
            fis.close();
            
        } catch (Exception e) {
            System.out.println("Error reading data: " + e.getMessage());
            // Don't print stack trace for file not found - it's expected on first run
        }
        return result;
    }
   }

 