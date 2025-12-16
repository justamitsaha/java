package com.saha.amit.pattern.creational.strategy;

public class Strategy {
    public static void main(String[] args) {
        // Create builder with different strategies
        CarBuilder builder = new CarBuilder();

        // Use standard strategy
        builder.setStrategy(new StandardBuildStrategy());
        builder.buildCar();

        // Switch to luxury strategy
        builder.setStrategy(new LuxuryBuildStrategy());
        builder.buildCar();
    }
}

// Supporting classes for Strategy
interface BuildStrategy {
    void build();
}

class StandardBuildStrategy implements BuildStrategy {
    @Override
    public void build() {
        System.out.println("Building standard car");
    }
}

class LuxuryBuildStrategy implements BuildStrategy {
    @Override
    public void build() {
        System.out.println("Building luxury car with premium features");
    }
}

class CarBuilder {
    private BuildStrategy strategy;

    public void setStrategy(BuildStrategy strategy) {
        this.strategy = strategy;
    }

    public void buildCar() {
        if (strategy == null) {
            System.out.println("No build strategy set!");
            return;
        }
        System.out.println("Starting car construction...");
        strategy.build();
        System.out.println("Car construction complete!");
    }
}
