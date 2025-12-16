package com.saha.amit.pattern.creational.templateMethod;

public class TemplateMethod {
    public static void main(String[] args) {
        CarManufacturer toyotaManufacturer = new ToyotaManufacturer();
        CarManufacturer hondaManufacturer = new HondaManufacturer();

        System.out.println("=== Toyota Manufacturing Process ===");
        toyotaManufacturer.manufactureCar();

        System.out.println("\n=== Honda Manufacturing Process ===");
        hondaManufacturer.manufactureCar();
    }
}

// Supporting classes for Template Method
abstract class CarManufacturer {
    // Template method
    public final void manufactureCar() {
        createChassis();
        installEngine();
        assembleParts();
        paint();
    }

    // Common steps with default implementation
    private void createChassis() {
        System.out.println("Creating standard chassis");
    }

    private void paint() {
        System.out.println("Painting the car");
    }

    // Steps that subclasses must implement
    protected abstract void installEngine();
    protected abstract void assembleParts();
}

class ToyotaManufacturer extends CarManufacturer {
    @Override
    protected void installEngine() {
        System.out.println("Installing Toyota engine");
    }

    @Override
    protected void assembleParts() {
        System.out.println("Assembling Toyota-specific parts");
    }
}

class HondaManufacturer extends CarManufacturer {
    @Override
    protected void installEngine() {
        System.out.println("Installing Honda VTEC engine");
    }

    @Override
    protected void assembleParts() {
        System.out.println("Assembling Honda-specific parts");
    }
}
