package exercises0001;

//sealed class with permitted subclasses
//NOTE : All subclasses must be in same package
//Or We can use fully qualified names ie permits java_8_To_17_Exercises.Car, java_8_To_17_Exercises.Bike
public sealed class Vehicle permits Car, Bike, ElectricVehicle {

    //Optional: Common method
    public void start(){
        System.out.println("Vehicle starts...");
    }
}
