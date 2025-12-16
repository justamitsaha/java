package com.saha.amit.pattern.creational.abstractfactory;

/*
Pros:
    Creates families of related objects without specifying concrete classes
    Promotes consistency among products
    Isolates concrete classes from client
Cons:
    Complexity increases with each new variant
    Difficult to support new kinds of products
When to use:
    When your system needs to create multiple families of products
    When you want to provide a library of products without revealing implementation
    When you want to enforce creation of products that work together
 */
public class AbstractFactory {
    public static void main(String[] args) {
        // Create a Toyota factory
        CarFactory toyotaFactory = new ToyotaFactory();
        Car toyotaCar = toyotaFactory.createCar();
        Engine toyotaEngine = toyotaFactory.createEngine();
        toyotaCar.buildCar();
        toyotaEngine.createEngine();

        // Create a Honda factory
        CarFactory hondaFactory = new HondaFactory();
        Car hondaCar = hondaFactory.createCar();
        Engine hondaEngine = hondaFactory.createEngine();
        hondaCar.buildCar();
        hondaEngine.createEngine();
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

class Honda implements Car {
    @Override
    public void buildCar() {
        System.out.println("Building Honda car");
    }
}

interface Engine {
    void createEngine();
}

class ToyotaEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("Creating Toyota engine");
    }
}

class HondaEngine implements Engine {
    @Override
    public void createEngine() {
        System.out.println("Creating Honda engine");
    }
}

interface CarFactory {
    Car createCar();
    Engine createEngine();
}

class ToyotaFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new Toyota();
    }

    @Override
    public Engine createEngine() {
        return new ToyotaEngine();
    }
}

class HondaFactory implements CarFactory {
    @Override
    public Car createCar() {
        return new Honda();
    }

    @Override
    public Engine createEngine() {
        return new HondaEngine();
    }
}
