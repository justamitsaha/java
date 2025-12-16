package com.saha.amit.pattern.creational.lazyInitialization;

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
public class LazyInitialization {
    public static void main(String[] args) {
        CarMaker carMaker = new CarMaker();

        System.out.println("CarMaker created, but car not yet initialized");

        // Car is created only when needed
        Car car = carMaker.getCar();
        car.buildCar();

        // Second call uses the same instance
        Car sameCar = carMaker.getCar();
        System.out.println("Are car instances the same? " + (car == sameCar));
    }
}

// Supporting classes for Lazy Initialization
interface Car {
    void buildCar();
}

class Toyota implements Car {
    public Toyota() {
        System.out.println("Toyota instance created");
    }

    @Override
    public void buildCar() {
        System.out.println("Building Toyota car");
    }
}

class CarMaker {
    private Car car;

    public Car getCar() {
        if (car == null) {
            System.out.println("Initializing car for the first time");
            car = new Toyota();
        } else {
            System.out.println("Returning existing car instance");
        }
        return car;
    }
}
