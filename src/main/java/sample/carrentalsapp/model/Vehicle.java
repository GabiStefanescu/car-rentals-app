package sample.carrentalsapp.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Vehicle {

    private int id;

    private int serialNumber;

    private String model;

    private boolean isRented;

    private double price;

    private String rentedDate;

    private String returnDate;

    private int customerId;


//    public Vehicle() {
//
//    }
//
//    public Vehicle(Long id, int serialNumber, String model, boolean isRented, double price, LocalDateTime rentedDate, LocalDateTime returnDate) {
//        this.id = id;
//        this.serialNumber = serialNumber;
//        this.model = model;
//        this.isRented = isRented;
//        this.price = price;
//        this.rentedDate = rentedDate;
//        this.returnDate = returnDate;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public int getSerialNumber() {
//        return serialNumber;
//    }
//
//    public void setSerialNumber(int serialNumber) {
//        this.serialNumber = serialNumber;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public void setModel(String model) {
//        this.model = model;
//    }
//
//    public boolean isRented() {
//        return isRented;
//    }
//
//    public void setRented(boolean rented) {
//        isRented = rented;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public LocalDateTime getRentedDate() {
//        return rentedDate;
//    }
//
//    public void setRentedDate(LocalDateTime rentedDate) {
//        this.rentedDate = rentedDate;
//    }
//
//    public LocalDateTime getReturnDate() {
//        return returnDate;
//    }
//
//    public void setReturnDate(LocalDateTime returnDate) {
//        this.returnDate = returnDate;
//    }
//
//    @Override
//    public String toString() {
//        return "Vehicle{" +
//                "id=" + id +
//                ", serialNumber=" + serialNumber +
//                ", model='" + model + '\'' +
//                ", isRented=" + isRented +
//                ", price=" + price +
//                ", rentedDate=" + rentedDate +
//                ", returnDate=" + returnDate +
//                '}';
//    }
}
