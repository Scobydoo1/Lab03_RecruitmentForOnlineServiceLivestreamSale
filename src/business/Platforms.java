/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import models.Platform;

/**
 *
 * @author thanh
 */
public class Platforms  extends HashMap<String, Platform>{

    private String pathFile;
    
    /**
     * Constructor to initialize Platforms collection with CSV file path
     * @param pathFile Path to the CSV file containing platform data
     */
    public Platforms(String pathFile) {
        this.pathFile = pathFile;
        this.readFromFile();
    }
    
    /**
     * Convert CSV line data to Platform object
     * @param temp CSV line containing platform data
     * @return Platform object or null if parsing fails
     */
    public Platform dataToObject(String temp) {
        Platform platform = null;
        try {
            String[] data = temp.split(",");
            // Skip header row
            if (data[0] != null && !data[0].equals("Code")) {
                platform = new Platform();
                platform.setCode(data[0].trim());
                platform.setPlatformName(data[1].trim());
                platform.setDescription(data[2].trim());
            }
        } catch (Exception e) {
            System.out.println("Error parsing platform data: " + temp);
            platform = null;
        }
        return platform;
    }
    
    /**
     * Read platform data from CSV file
     */
    public void readFromFile() {
        try {
            // Create file object
            File f = new File(this.pathFile);
            if (!f.exists()) {
                System.out.println("Platform file not found: " + pathFile);
                return;
            }
            
            // Create file reader
            FileReader fr = new FileReader(f);
            
            // Create buffered reader for efficient reading
            BufferedReader br = new BufferedReader(fr);
            String temp = "";
            
            // Read each line and convert to Platform object
            while ((temp = br.readLine()) != null) {
                Platform platform = dataToObject(temp);
                if (platform != null) {
                    this.put(platform.getCode(), platform);
                }
            }
            
            br.close();
            fr.close();
            
        } catch (Exception e) {
            System.out.println("Error reading platform file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Display all available platforms
     */
    public void showAll() {
        if (this.isEmpty()) {
            System.out.println("No platform data available.");
            return;
        }
        
        System.out.println("Available Platforms:");
        System.out.println("=====================================");
        for (Platform p : this.values()) {
            System.out.format("Code: %-6s | Name: %-15s%n", p.getCode(), p.getPlatformName());
            System.out.println("Description: " + p.getDescription());
            System.out.println("-------------------------------------");
        }
    }
    
    /**
     * Search for a platform by code
     * @param id The platform code to search for
     * @return Platform object or null if not found
     */
    public Platform searchById(String id) {
        return this.get(id);
    }
    
    /**
     * Check if a platform code exists
     * @param id The platform code to check
     * @return true if exists, false otherwise
     */
    public boolean contains(String id) {
        return this.containsKey(id);
    }
}