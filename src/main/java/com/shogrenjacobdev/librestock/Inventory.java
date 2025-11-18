package com.shogrenjacobdev.librestock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Inventory {

    private DbAccess db;

    Inventory(DbAccess db) {
        this.db = db;
    }

    public Item getItemById(int id) {
        int itemId;
        String sku;
        String name;
        int quantity;
        int collection; // Returns id of collection item belongs to
        String description;

        Map<String, Object> result = db.getRowById(id, "items");
        System.out.println(result.toString());


        itemId = (int) result.get("itemId");
        sku = (String) result.get("sku");
        name = (String) result.get("name");
        quantity = (int) result.get("quantity");
        collection = (int) result.get("collection");
        description = (String) result.get("description");

        return new Item(itemId, sku, name, quantity, collection, description);
    }

    // Note: Only returns the first occurence of an item with the given name, to get all items with a given name use getItemsByName
    public Item getItemByName(String name) {
        int itemId;
        String sku;
        String itemName;
        int quantity;
        int collection;
        String description;

        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM items WHERE name = ?", name);

            Map<String, Object> item = result.getFirst();

            itemId = (int) item.get("itemId");
            sku = (String) item.get("sku");
            itemName = (String) item.get("name");
            quantity = (int) item.get("quantity");
            collection = (int) item.get("collection");
            description = (String) item.get("description");

            return new Item(itemId, sku, itemName, quantity, collection, description);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getItemByName: " + e.getMessage());
            return null;
        }
    }

    public List<Item> getItemsByName(String name) {
        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM items WHERE name = ?", name);
            List<Item> items = new ArrayList<>();

            for  (int i = 0; i < result.size(); i++) {
                Map<String, Object> item = result.get(i);
                int id = (int) item.get("itemId");
                String sku = (String) item.get("sku");
                String itemName = (String) item.get("name");
                int quantity = (int) item.get("quantity");
                int collection = (int) item.get("collection");
                String description = (String) item.get("description");

                items.add(new Item(id, sku, itemName, quantity, collection, description));
            }

            return items;
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getItemsByName: " + e.getMessage());
            return null;
        }
    }

    public Item getItemBySku(String sku) {
        int itemId;
        String itemSku;
        String itemName;
        int quantity;
        int collection;
        String description;

        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM items WHERE sku = ?", sku);

            Map<String, Object> item = result.getFirst();

            itemId = (int) item.get("itemId");
            itemSku = (String) item.get("sku");
            itemName = (String) item.get("name");
            quantity = (int) item.get("quantity");
            collection = (int) item.get("collection");
            description = (String) item.get("description");

            return new Item(itemId, itemSku, itemName, quantity, collection, description);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getItemByName: " + e.getMessage());
            return null;
        }
    }
}
