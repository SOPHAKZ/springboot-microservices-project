public class Car extends Vehicle{
    private int numberOfSeats;

    public Car(String brand, String model,int numberOfSeats){
        super(brand, model);
        this.numberOfSeats = numberOfSeats;
    }
//
//    public int getNumberOfSeats() {
//        return numberOfSeats;
//    }

    public void displayCarInfo(){
        System.out.println("Number of Seats : "+numberOfSeats);
        displayInfo();
        System.out.println();
    }
    public static void main(String[] args){
        Car car = new Car("CAMARY","TOYOTA",2);

        car.displayCarInfo();
    }


}
