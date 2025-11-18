package com.shogrenjacobdev.librestock;

public class Item {
    private final int id;
    private String sku;
    private String name;
    private int quantity;
    private final int collection;
    private String description;

    public Item(int id, String sku, String name, int quantity, int collection, String description) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.collection = collection;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCollection() {
        return collection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return
                "id = " + id + "\n" +
                "sku = " + sku + "\n" +
                "name = " + name + "\n" +
                "collection = " + collection + "\n" +
                "description = " + description + "\n";
    }
}
