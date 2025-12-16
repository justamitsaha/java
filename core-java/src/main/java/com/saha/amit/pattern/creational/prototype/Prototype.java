package com.saha.amit.pattern.creational.prototype;

/*
Pros:
    Defers object creation until needed
    Reduces memory usage
    Can improve application startup time
Cons:
    Adds complexity
    Can create race conditions in multithreaded environments if not implemented correctly
When to use:
    When initial object creation is expensive
    When you're not sure if the object will be used
    To spread out resource utilization over time
 */
public class Prototype {
    public static void main(String[] args) {
        // Create original car
        Car originalCar = new Car("Toyota", "Camry");
        System.out.println("Original: " + originalCar);

        // Clone the car
        Car clonedCar = originalCar.clone();
        System.out.println("Clone: " + clonedCar);

        // Verify they're not the same instance but have same properties
        System.out.println("Are they the same instance? " + (originalCar == clonedCar));
        System.out.println("Do they have the same properties? " +
                (originalCar.toString().equals(clonedCar.toString())));
    }
}

// Supporting classes for Prototype
interface Cloneable {
    Car clone();
}

class Car implements Cloneable {
    private String make;
    private String model;

    public Car(String make, String model) {
        this.make = make;
        this.model = model;
    }

    @Override
    public Car clone() {
        return new Car(this.make, this.model);
    }

    @Override
    public String toString() {
        return "Car{make='" + make + "', model='" + model + "'}";
    }
}
