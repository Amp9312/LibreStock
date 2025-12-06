package com.shogrenjacobdev.librestock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        // System.out.println(result.toString());

        if (result == null) {
            Testing.TestFail("getItemById", "Result returned null.");
            return null;
        }

        itemId = (int) result.get("itemId");
        sku = (String) result.get("sku");
        name = (String) result.get("name");
        quantity = (int) result.get("quantity");
        collection = (int) result.get("collection");
        description = (String) result.get("description");

        Testing.TestPass("getItemById", "All row contents retrieved successfully");

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
            System.out.println("Query returned rows: " + result.size());

            if (!result.isEmpty()) {
                System.out.println("Columns: " + result.get(0).keySet());
            }

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

            for (int i = 0; i < result.size(); i++) {
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

    public Item getItemByQuantity(int quantity) {
        int itemId;
        String itemName;
        String itemSku;
        int collection;
        String description;
        int  itemQuantity;

        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM items WHERE quantity = ?", quantity);

            Map<String, Object> item = result.getFirst();
            itemId = (int) item.get("itemId");
            itemSku = (String) item.get("sku");
            itemName = (String) item.get("name");
            collection = (int) item.get("collection");
            description = (String) item.get("description");
            itemQuantity = (int) item.get("quantity");

            return new Item(itemId, itemSku, itemName, itemQuantity, collection, description);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getItemByQuantity: " + e.getMessage());
            return null;
        }
    }

    public List<Item> getItemsByQuantity(int quantity) {

        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM items WHERE quantity = ?", quantity);
            List<Item> items = new ArrayList<>();

            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> item = result.get(i);
                int itemId = (int) item.get("itemId");
                String sku = (String) item.get("sku");
                String itemName = (String) item.get("name");
                int itemQuantity = (int) item.get("quantity");
                int collection = (int) item.get("collection");
                String description = (String) item.get("description");

                items.add(new Item(itemId, sku, itemName, itemQuantity, collection, description));
            }

            return items;
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getItemsByQuantity: " + e.getMessage());
            return null;
        }
    }

    public void deleteItemById(int id) {
        try {
            db.runQuery("DELETE FROM items WHERE id = ?", id);
            System.out.println("Deleted item with id " + id);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.deleteItemById: " + e.getMessage());
        }
    }

    public void deleteItemBySku(String sku) {
        try {
            db.runQuery("DELETE FROM items WHERE sku = ?", sku);
            System.out.println("Deleted item with sku " + sku);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.deleteItemBySku: " + e.getMessage());
        }
    }

    public void deleteItemsByName(String name) {
        try {
            db.runQuery("DELETE FROM items WHERE name = ?", name);
            System.out.println("Deleted ALL items with name " + name);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.deleteItemByName: " + e.getMessage());
        }
    }

    public void deleteItemsByCollection(int collection) {
        try {
            db.runQuery("DELETE FROM items WHERE collection = ?", collection);
            System.out.println("Deleted ALL items in collection " + collection);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.deleteItemByCollection: " + e.getMessage());
        }
    }

    public void createItem(int itemId, String sku, String name, int quantity, int collection, String description) {
        try {
            String sql = "INSERT INTO items VALUES (?, ?, ?, ?, ?, ?)";
            db.runQuery(sql, itemId, sku, name, quantity, collection, description);
            System.out.println("Created item with name " + name);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.createItem: " + e.getMessage());
        }
    }

    public Collection getCollectionById(int collId) {
        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM collections WHERE id = ?", collId);
            Map<String, Object> coll = result.getFirst();

            int collectionId = (int) coll.get("id");
            String collectionName = (String) coll.get("name");
            String type = (String) coll.get("type");
            int size = (int) coll.get("size");

            return new Collection(collectionId, collectionName, type, size);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getCollectionById: " + e.getMessage());
            return null;
        }
    }

    public List<Collection> getCollectionsByName(String name) {
        try {
            List<Map<String, Object>> result = db.runQuery("SELECT * FROM collections WHERE name = ?", name);
            List<Collection> collections = new ArrayList<>();

            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> coll = result.get(i);
                int id = (int) coll.get("id");
                String collName = (String) coll.get("name");
                String type = (String) coll.get("type");
                int size = (int) coll.get("size");

                collections.add(new Collection(id, collName, type, size));
            }

            return collections;
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.getCollectionsByName: " + e.getMessage());
            return null;
        }
    }

    public void createCollection(int collectionId, String name, String type, int size) {
        try {
            String sql = "INSERT INTO collections VALUES (?, ?, ?, ?)";
            db.runQuery(sql, collectionId, name, type, size);
            System.out.println("Created collection with name " + name);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.createCollection: " + e.getMessage());
        }
    }

    public void deleteCollectionById(int collectionId) {
        try {
            db.runQuery("DELETE FROM collections WHERE id = ?", collectionId);
            System.out.println("Deleted collection with id " + collectionId);
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.deleteCollectionById: " + e.getMessage());
        }
    }

    public void ExportTable(String table, String pathname) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        File path = new File( pathname + "/" + pathname + "-" + now + "-" + table + ".csv");

        System.out.println("Exporting " + table + " to " + path.getAbsolutePath());

        if (!path.exists()) {
            path.createNewFile();

            try {
                List<Map<String, Object>> results = db.runQuery("SELECT * FROM " +  table);

                if (results == null) {
                    return;
                }

                FileWriter csv = new FileWriter(path);
                Set<String> headers =  results.getFirst().keySet();
                ArrayList<String> headersList = new ArrayList<>();

                for (String header : headers) {
                    csv.append(header);
                    csv.append(",");
                    headersList.add(header);
                    System.out.println("Adding header " + header);
                }
                csv.append("\n");

                System.out.println(headersList.toString());
                System.out.println(results.size());

                for (int i = 0; i < results.size(); i++) {
                    for (int j = 0; j < headersList.size(); j++) {
                        System.out.println(results.get(i).get(headersList.get(j)));
                        Object cell = results.get(i).get(headersList.get(j));

                        if (cell == null) {
                            cell = "";
                        }

                        String cellString = cell.toString();

                        csv.append(cellString);
                        csv.append(",");
                    }
                    csv.append("\n");
                    System.out.println("---------------");
                }

                csv.flush();
                csv.close();
            }
            catch (SQLException e) {
                System.err.println("SQLException in Inventory.ExportTable: " + e.getMessage());
            }
        }
    }

    public void ExportInventory(String fileName) {
        LocalDateTime now = LocalDateTime.now();
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' AND name NOT LIKE 'users'";
        try {
            List<Map<String, Object>> tables = db.runQuery(sql);
            System.out.println(tables.toString());

            File outputFolder = new File("./" + fileName);

            if (!outputFolder.exists()) {
                outputFolder.mkdir();
            }

            for (Map<String, Object> table : tables) {
                String tableName = table.get("name").toString();
                try {
                    // + "/inventory-export-" + now + "/"
                    ExportTable(tableName, fileName);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e) {
            System.err.println("SQLException in Inventory.ExportInventory: " + e.getMessage());
        }
    }

    public void ImportTable(File fileName) {
        System.out.println(fileName.toString());
        String[] fileNameTokens = fileName.toString().split("-");
        String tableName = fileNameTokens[fileNameTokens.length - 1];
        int fileExt = tableName.lastIndexOf('.');

        tableName = tableName.substring(0, fileExt);

        System.out.println("Table name: " + tableName);

        if (tableName.equals("collections")) {
            String sql = "CREATE TABLE collections (id integer primary key, name varchar(20), type varchar(20), size integer);";
            try {
                db.runQuery(sql);

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String row;
                    reader.readLine(); // Skip line one (headers)

                    while ((row = reader.readLine()) != null) {
                        String[] rowVals = row.split(",");
                        int sizeVal = Integer.valueOf(rowVals[0]); // Unboxing Objects into primitives...
                        int idVal = Integer.valueOf(rowVals[2]);

                        String rowSql = "INSERT INTO collections (size, name, id, type) VALUES (?,?,?,?)";
                        db.runQuery(rowSql, sizeVal, rowVals[1], idVal, rowVals[3]);
                    }
                }
                catch (IOException e) {
                    System.out.println("IOException in ImportTable: " + e.getMessage());
                }
            } 
            catch (SQLException e) {
                System.out.println("SQLException in ImportTable, collections case: " + e.getMessage());
            }
        }
        else if (tableName.equals("items")) {
            String sql = "CREATE TABLE items (itemId integer primary key, sku varchar(10), name varchar(50), quantity integer, collection integer, description varchar(255));";
            try {
                db.runQuery(sql);

                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String row;
                    reader.readLine(); // Skip line one (headers)

                    while ((row = reader.readLine()) != null) {
                        String[] rowVals = row.split(",");
                        int idVal = Integer.valueOf(rowVals[0]); // Unboxing Objects into primitives...
                        int quantVal = Integer.valueOf(rowVals[1]);
                        int collectionVal = Integer.valueOf(rowVals[4]);

                        String rowSql = "INSERT INTO items (itemId, quantity, name, description, collection, sku) VALUES (?,?,?,?,?,?)";
                        db.runQuery(rowSql, idVal, quantVal, rowVals[2], rowVals[3], collectionVal, rowVals[5]);
                    }
                }
                catch (IOException e) {
                    System.out.println("IOException in ImportTable: " + e.getMessage());
                }
            } 
            catch (SQLException e) {
                System.out.println("SQLException in ImportTable, collections case: " + e.getMessage());
            }
        }

        System.out.println("Table " + tableName + " imported.");
    }

    public void ClearDatabase() {
        try {
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' AND name NOT LIKE 'users'";
            List<Map<String, Object>> oldTables = db.runQuery(sql);

            System.out.println("Old Table names: " + oldTables.toString());

            for (Map<String, Object> table : oldTables) {
                String tableName = (String) table.get("name");
                String dropSql = "DROP TABLE IF EXISTS " + tableName;

                db.runQuery(dropSql);
            }

            System.out.println("Original database successfully cleared");
        }
        catch (SQLException e) {
            System.out.println("SQLException in ClearDatabase: " + e.getMessage());
        }
    }

    public void ImportInventory(String folderName) {
        LocalDateTime now = LocalDateTime.now();
        String path = "./" + folderName;
        File importFolder = new File(path);

        if (!importFolder.exists()) {
            System.out.println("Import folder DNE");
            return;
        }
        
        if (!importFolder.isDirectory()) {
            System.out.println("Import must be a folder.");
            return;
        }

        File[] importFiles = importFolder.listFiles();

        try {
            ExportInventory("Backup-" + now);            
            ClearDatabase();

            for (File file : importFiles) {
                ImportTable(file);
            }
            System.out.println("Tables imported successfully.");
            
        } 
        catch (Exception e) {
            System.out.println("Exception in Inventory.ImportInventory: " + e.getMessage());
        }
        
    }
}
