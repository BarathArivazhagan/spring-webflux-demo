package com.barath.webflux.app;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by barath on 22/12/17.
 */
@Entity
@Table(name = "INVENTORY")
public class Inventory implements Serializable{

    @Id
    @GeneratedValue
    @Column(name="INVENTORY_ID")
    private Long inventoryId;


    @Column(name="PRODUCT_NAME")
    private String productName;

    @Column(name="LOCATION_NAME")
    private String locationName;

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Inventory(Long inventoryId, String productName, String locationName) {
        this.inventoryId = inventoryId;
        this.productName = productName;
        this.locationName = locationName;
    }

    public Inventory() {
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", productName='" + productName + '\'' +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
