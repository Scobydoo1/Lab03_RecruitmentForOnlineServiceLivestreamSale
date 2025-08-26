/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;

/**
 *
 * @author thanh
 */
public class Platform {
     private String code;
    private String platformName;
    private String description;
     public Platform() {
    }
     public Platform(String code, String platformName, String description) {
        this.code = code;
        this.platformName = platformName;
        this.description = description;
    }
     public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getPlatformName() {
        return platformName;
    }
    
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
     @Override
    public String toString() {
        return "Platform{" + "code=" + code + ", platformName=" + platformName + 
               ", description=" + description + '}';
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
        final Platform other = (Platform) obj;
        return Objects.equals(this.code, other.code);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
