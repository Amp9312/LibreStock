package com.shogrenjacobdev.librestock;

public class CurrentUser {

    private static int id;
    private static String username;
    private static String firstName;
    private static String lastName;
    private static boolean admin;

    public static void setUser(int _id, String _username, String _firstName, String _lastName, boolean _admin) {
        id = _id;
        username = _username;
        firstName = _firstName;
        lastName = _lastName;
        admin = _admin;
    }

    public static int getId() { return id; }
    public static String getUsername() { return username; }
    public static String getFirstName() { return firstName; }
    public static String getLastName() { return lastName; }
    public static boolean isAdmin() { return admin; }
}
