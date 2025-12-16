package com.saha.amit.pattern.creational.builder;

/*
Pros:
    Allows creating complex objects step by step
    Can reuse the same construction code for different object representations
    Isolates complex construction code from business logic
Cons:
    Increases code complexity
    Requires creating multiple classes
When to use:
    When object creation involves multiple steps
    When you need different representations of an object
    When you want to prevent "telescoping constructors" (constructors with many parameters)
 */
public class Builder {
    public static void main(String[] args) {
        // Build a car with various options
        Car car = new Car.Builder("Toyota", "Camry", 2023)
                .withGPS()
                .withBluetooth()
                .build();

        System.out.println("Built car: " + car);
    }
}

// Supporting classes for Builder
class Car {
    private String make;
    private String model;
    private int year;
    private boolean hasGPS;
    private boolean hasBluetooth;

    private Car() {}

    @Override
    public String toString() {
        return "Car{make='" + make + "', model='" + model + "', year=" + year +
                ", hasGPS=" + hasGPS + ", hasBluetooth=" + hasBluetooth + "}";
    }

    public static class Builder {
        private String make;
        private String model;
        private int year;
        private boolean hasGPS = false;
        private boolean hasBluetooth = false;

        public Builder(String make, String model, int year) {
            this.make = make;
            this.model = model;
            this.year = year;
        }

        public Builder withGPS() {
            this.hasGPS = true;
            return this;
        }

        public Builder withBluetooth() {
            this.hasBluetooth = true;
            return this;
        }

        public Car build() {
            Car car = new Car();
            car.make = this.make;
            car.model = this.model;
            car.year = this.year;
            car.hasGPS = this.hasGPS;
            car.hasBluetooth = this.hasBluetooth;
            return car;
        }
    }
}