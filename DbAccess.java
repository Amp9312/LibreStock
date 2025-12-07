package com.shogrenjacobdev.librestock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public List<Map<String, Object>> runQuery(String query, Object... params) throws SQLException {
        List<Map<String, Object>> result = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set SQL parameters
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }

            boolean isResultSet = stmt.execute();

            // INSERT / UPDATE / DELETE → isResultSet = false
            // SELECT → isResultSet = true
            if (isResultSet) {
                try (ResultSet rs = stmt.getResultSet()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();

                    while (rs.next()) {
                        Map<String, Object> row = new HashMap<>();
                        for (int i = 1; i <= colCount; i++) {
                            String colName = meta.getColumnName(i);
                            Object value = rs.getObject(i);
                            row.put(colName, value);
                        }
                        result.add(row);
                    }
                }

                return result.isEmpty() ? null : result;
            }

            // Non-SELECT statements (INSERT/UPDATE/DELETE) – caller doesn’t expect rows
            return null;
        }
    }

/*
<<<<<<< HEAD
            return new ArrayList<>();
        } catch (SQLException e) {
            System.out.println("SQLException in DBAccess.runQuery: " + e.getMessage());
//=======
            return null;
        }
        catch(SQLException e) {
            System.out.println("SQLException in DBAccess.runQuery:" + e.getMessage());
//>>>>>>> upstream/master
            return null;
        }
     }*/

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
        String sql;
        if (table.equals("items")) {
            sql = "SELECT * FROM items WHERE itemId = ?";
        } else if (table.equals("collections")) {
            sql = "SELECT * FROM collections WHERE id = ?";
        } else if (table.equals("users")) {
            sql = "SELECT * FROM users WHERE userId = ?";
        } else {
            throw new IllegalArgumentException("Invalid table: " + table);
        }

        try {
            List<Map<String, Object>> result = runQuery(sql, id);
            return result.getFirst();
        } catch (SQLException e) {
            System.out.println("SQLException in DBAccess.getRowById: " + e.getMessage());
        }

        return null;
    }

    // ==============================================================
    //                       USER LOGIN METHODS
    // ==============================================================

    // Look up a user by username + password
    public Map<String, Object> findUserByCredentials(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            List<Map<String, Object>> rows = runQuery(sql, username, password);
            if (rows != null && !rows.isEmpty()) {
                return rows.get(0);
            }
        } catch (SQLException e) {
            System.out.println("SQLException in DbAccess.findUserByCredentials: " + e.getMessage());
        }
        return null;
    }

    // Get the next available userId
    public int getNextUserId() {
        String sql = "SELECT COALESCE(MAX(userId), 0) + 1 AS nextId FROM users";
        try {
            List<Map<String, Object>> rows = runQuery(sql);
            if (rows != null && !rows.isEmpty()) {
                Object val = rows.get(0).get("nextId");
                return ((Number) val).intValue();
            }
        } catch (SQLException e) {
            System.out.println("SQLException in DbAccess.getNextUserId: " + e.getMessage());
        }
        return 1;
    }

    // Insert a new user
    public boolean insertUser(int userId,
                              int accountId,
                              String firstName,
                              String lastName,
                              String role,
                              String username,
                              String password) {

        String sql = "INSERT INTO users (userId, accountId, firstName, lastName, role, username, password) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            runQuery(sql, userId, accountId, firstName, lastName, role, username, password);
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException in DbAccess.insertUser: " + e.getMessage());
            return false;
        }
    }

}
