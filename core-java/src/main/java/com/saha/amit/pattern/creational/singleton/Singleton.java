package com.saha.amit.pattern.creational.singleton;
/*
Pros:
    Ensures a class has only one instance
    Provides a global access point to that instance
    The singleton object is initialized only when requested
Cons:
    Violates Single Responsibility Principle (controls its own creation and lifecycle)
    Can make unit testing difficult
    Requires special treatment in multi-threaded environments
When to use:
    When exactly one instance of a class is needed
    When you need stricter control over global variables
    For shared resources (database connections, thread pools)
 */
public class Singleton {
    public static void main(String[] args) {
        // Get the singleton instance
        CarFactory factory1 = CarFactory.getInstance();
        CarFactory factory2 = CarFactory.getInstance();

        // Verify both references point to the same instance
        System.out.println("Are factories the same instance? " + (factory1 == factory2));

        // Use the factory
        Car toyota = factory1.createCar("Toyota");
        toyota.buildCar();
    }
}

// Supporting classes for Singleton
class CarFactory {
    private static CarFactory instance;

    private CarFactory() {
        // Private constructor prevents direct instantiation
    }

    public static synchronized CarFactory getInstance() {
        if (instance == null) {
            instance = new CarFactory();
        }
        return instance;
    }

    public Car createCar(String type) {
        if (type.equalsIgnoreCase("Toyota")) {
            return new Toyota();
        }
        // Add more types as needed
        return new Toyota(); // Default
    }
}

interface Car {
    void buildCar();
}

class Toyota implements Car {
    @Override
    public void buildCar() {
        System.out.println("Building Toyota car");
    }
}