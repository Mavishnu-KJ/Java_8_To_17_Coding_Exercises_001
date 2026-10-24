package exercises0001;

//final class - Cannot be extended further
public final class Tesla extends ElectricVehicle {

    @Override
    public void start(){
        System.out.println("Tesla starts...");
    }

}
