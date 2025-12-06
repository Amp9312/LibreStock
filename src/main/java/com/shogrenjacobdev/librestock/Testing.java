package com.shogrenjacobdev.librestock;

public class Testing {
    
    public static void TestPass(String funcName, String message) {
        System.out.println("====================");
        System.out.println("Test Successful for " + funcName);
        System.out.println("--------------------");
        System.out.println(message);
        System.out.println("====================");
    }

    public static void TestFail(String funcName, String message) {
        System.out.println("====================");
        System.out.println("Test Failed for " + funcName);
        System.out.println("--------------------");
        System.out.println(message);
        System.out.println("====================");
    }

}
