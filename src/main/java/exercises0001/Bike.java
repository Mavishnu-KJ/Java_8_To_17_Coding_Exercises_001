package exercises0001;

//final class - Cannot be extended further
public final class Bike extends Vehicle{
    @Override
    public void start(){
        System.out.println("Bike starts...");
    }
}
