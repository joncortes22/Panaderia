package Model;

import Program.Main;

import java.util.ArrayList;
import java.util.Arrays;

public class Item {
    private int id;
    private String name;
    private int unitPrice;

    private int availability;

    public Item(int id, String name, int unitPrice, int availability) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.availability = availability;
    }

    public Item(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.unitPrice = item.getUnitPrice();
        this.availability = item.getAvailability();
    }

    //Getters & Setters

    public int getId() {
        return id;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
