package com.shogrenjacobdev.librestock;

import java.sql.*;
import java.util.*;

public class DbAccess {
    private final String url = "jdbc:sqlite:librestock.db";
    private final Connection connection;

    public DbAccess() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public Connection getConnection() {
        return connection;
    }

    /*
    * Returns a list of Hashmaps based on the SQL query given in the parameters
    * INPUT:
    *   String query: SQL query to run, can use "?" as placeholder for SQL params or give a raw query
    *   Object params: Any amount of parameters you need for SQL query, first param will replace first "?" and so on
    * OUTPUT:
    *   List<Map<String, Object>>: An ArrayList of Hashmaps representing the database rows returned by the query
    * OR:
    *   null, if query returns empty
    * EXAMPLE: runQuery("SELECT * FROM items WHERE itemId = ?", 1)
    * RETURNS: [{itemId=1, quantity=1, name=Test Item, description=null, collection=1, sku=ABC-DEF-GH}]
    */
    public List<Map<String,Object>> runQuery(String query, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            boolean isResultSet = stmt.execute();

            if (isResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    List<Map<String, Object>> result = new ArrayList<>();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= columnCount; i++) {
                            row.put(metaData.getColumnName(i), rs.getObject(i));
                        }
                        result.add(row);
                    }

                    return result;
                }
            }

            return new ArrayList<Map<String,Object>>();
        }
        catch(SQLException e) {
            System.out.println("SQLException in DBAccess.runQuery:" + e.getMessage());
            return null;
        }
    }

    /*
     * Returns a specific row from the database as a generic hashmap
     * INPUT:
     *   Integer id: id of row you want
     *   String table: name of the table to query from
     * OUTPUT:
     *   Map<String, Object>: A hashmap representing the row with given id in given table
     * OR: null, if row not found
     * EXAMPLE: getRowById(1, "collections")
     * RETURNS: {size=1, name=test collection, id=1, type=test}
     */
    public Map<String, Object> getRowById(Integer id, String table) {
        if (table.equals("items")) {
            String sql = "SELECT * FROM " + table + " WHERE itemId = " + id.toString();

            try {
                List<Map<String, Object>> result = runQuery(sql);
                return result.getFirst();
            }
            catch (SQLException e) {
                System.out.println("SQLException in DBAccess.getRowById, items table case:" + e.getMessage());
            }
        }
        else if (table.equals("collections")) {
            String sql = "SELECT * FROM " + table + " WHERE id = " + id.toString();

            try {
                List<Map<String, Object>> result = runQuery(sql);
                return result.getFirst();
            }
            catch (SQLException e) {
                System.out.println("SQLException in DBAccess.getRowById, collections table case:" + e.getMessage());
            }
        }
        else if (table.equals("users")) {
            String sql = "SELECT * FROM " + table + " WHERE userId = " + id.toString();

            try {
                List<Map<String, Object>> result = runQuery(sql);
                return result.getFirst();
            }
            catch (SQLException e) {
                System.out.println("SQLException in DBAccess.getRowById, users table case:" + e.getMessage());
            }
        }
        else {
            throw new IllegalArgumentException("IllegalArgumentException in DbAccess.getRowById: Invalid table: " + table);
        }

        return null;
    }
}
