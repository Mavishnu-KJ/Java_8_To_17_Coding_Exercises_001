package exercises0001;

//non-sealed class - Can be extended further
public non-sealed class ElectricVehicle extends Vehicle{
    @Override
    public void start(){
        System.out.println("Electric vehicle starts...");
    }
}
