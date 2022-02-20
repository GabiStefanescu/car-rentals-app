package sample.carrentalsapp.jdbc;

import sample.carrentalsapp.model.Customer;
import sample.carrentalsapp.model.Vehicle;

import java.sql.Date;
import java.util.List;

public interface DatabaseDAO {
    List<Vehicle> getVehicles();

    List<Customer> getCustomers();

    void addVehicle(Vehicle vehicle);

    void returnVehicle(int serialNumber, Date returnDate);

    int getSerialNumberFromDatabase(int serialNumber);

    boolean getVehicleRentedStatus(int serialNumber);

    void addCustomer(Customer customer);

    void deleteVehicle(int serialNumber);

    void deleteCustomer(int id);

    void updateVehicle(int id, int serialNumber, String model, double price);

    void updateRentedVehicle(int id, boolean rentedStatus, Date rentedDate, int customerId);

    int countCustomerTableRows();
}
