package com.comp3607.patterns;

/**
 * Example Singleton pattern implementation
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    /** @return Singleton instance */
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
