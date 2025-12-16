package com.saha.amit.pattern.creational.factory;


/*
Pros:
    Hides implementation details of product creation
    Single Responsibility Principle - moves product creation to one place
    Open/Closed Principle - easy to add new types without modifying existing code
Cons:
    May lead to large factory classes
    Requires creation of a new subclass for each product type
When to use:
    When you don't know the exact types of objects your code will need
    When you want to provide users of your library a way to extend its internal components
    To decouple object creation from its usage
 */
public class Factory {

    public Car createCar(String type) {
        if (type == null || type.isEmpty()) {
            return new Toyota(); // Default implementation
        }

        if (type.equalsIgnoreCase("Toyota")) {
            return new Toyota();
        } else if (type.equalsIgnoreCase("Honda")) {
            return new Honda();
        }


        return new Toyota(); // Fallback to default
    }

    public static void main(String[] args) {
        Factory carMaker = new Factory();
        Car car = carMaker.createCar("Toyota");
        car.buildCar();
    }
}

interface Car {
    void buildCar();
}

class Honda implements Car {
    @Override
    public void buildCar() {
        System.out.println("Toyota Corolla");
    }
}

class Toyota implements Car {
    @Override
    public void buildCar() {
        System.out.println("Toyota Corolla");
    }
}