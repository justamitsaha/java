package com.saha.amit.pattern.creational.dependencyInjection;

/*
Pros:
    Reduces class coupling
    Makes testing easier through mocking dependencies
    Increases code reusability
Cons:
    Can be complex to set up in large projects without a framework
    Might be overkill for small applications
When to use:
    When you want to decouple object creation from usage
    When you need to make your code more testable
    When working with frameworks that support IoC (Inversion of Control)
 */
public class DependencyInjection {
    public static void main(String[] args) {
        // Create dependencies
        Car toyota = new Toyota();
        Car honda = new Honda();

        // Inject dependencies
        CarMaker toyotaMaker = new CarMaker(toyota);
        CarMaker hondaMaker = new CarMaker(honda);

        // Use the objects
        toyotaMaker.assembleCar();
        hondaMaker.assembleCar();
    }
}

// Supporting classes for Dependency Injection
interface Car {
    void buildCar();
}

class Toyota implements Car {
    @Override
    public void buildCar() {
        System.out.println("Building Toyota car");
    }
}

class Honda implements Car {
    @Override
    public void buildCar() {
        System.out.println("Building Honda car");
    }
}

class CarMaker {
    private final Car car;

    // Constructor injection
    public CarMaker(Car car) {
        this.car = car;
    }

    public void assembleCar() {
        System.out.println("Starting assembly process...");
        car.buildCar();
        System.out.println("Assembly complete!");
    }
}
