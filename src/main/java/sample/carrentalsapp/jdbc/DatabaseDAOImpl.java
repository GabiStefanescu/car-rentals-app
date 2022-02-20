package sample.carrentalsapp.jdbc;

import sample.carrentalsapp.model.Customer;
import sample.carrentalsapp.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAOImpl implements DatabaseDAO {

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sqlQuery = "SELECT *FROM vehicles";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Vehicle vehicle = Vehicle.builder()
                        .id(resultSet.getInt("id"))
                        .serialNumber(resultSet.getInt("serial_number"))
                        .model(resultSet.getString("model"))
                        .isRented(resultSet.getBoolean("rented_status"))
                        .price(resultSet.getDouble("price"))
                        .rentedDate(String.valueOf(resultSet.getDate("rented_date")))
                        .returnDate(String.valueOf(resultSet.getDate("returned_date")))
                        .customerId(resultSet.getInt("customer_id"))
                        .build();
                vehicleList.add(vehicle);
            }

            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return vehicleList;
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sqlQuery = "SELECT *FROM customers";

        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Customer customer = Customer.builder()
                        .id(resultSet.getInt("id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .vehicleId(resultSet.getInt("vehicle_id"))
                        .build();
                customerList.add(customer);
            }

            try {
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return customerList;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "INSERT INTO vehicles (id, serial_number, model, rented_status, price, rented_date, returned_date, customer_id) VALUES \n" +
                    "(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, vehicle.getId());
            preparedStatement.setInt(2, vehicle.getSerialNumber());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setBoolean(4, vehicle.isRented());
            preparedStatement.setDouble(5, vehicle.getPrice());
            preparedStatement.setDate(6, null);
            preparedStatement.setDate(7, null);
            preparedStatement.setInt(8, vehicle.getCustomerId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void returnVehicle(int serialNumber, Date returnDate) {
        int customerId = 0;
        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT customer_id FROM vehicles WHERE serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, serialNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customerId = resultSet.getInt(1);
            }
            deleteCustomer(customerId);
        } catch (SQLException e) {
            System.out.println(e + "delete customer from return vehicle");
        }

        try (Connection connection = getConnection()) {
            String sqlQuery = "UPDATE vehicles SET rented_status = ?, returned_date = ?, customer_id = ? WHERE serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setDate(2, returnDate);
            preparedStatement.setInt(3,0);
            preparedStatement.setInt(4, serialNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public int getSerialNumberFromDatabase(int serialNumber) {
        int returnedSerialNumber = 0;
        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT serial_number FROM vehicles WHERE serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, serialNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                returnedSerialNumber = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e + "getSerialNumberFromDatabase");
        }
        return returnedSerialNumber;
    }

    @Override
    public boolean getVehicleRentedStatus(int serialNumber) {
        boolean isRented = false;
        try (Connection connection = getConnection()) {
            String sqlQuery = "SELECT rented_status FROM vehicles WHERE serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, serialNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isRented = resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            System.out.println(e + "getSerialNumberFromDatabase");
        }
        return isRented;
    }

    @Override
    public void addCustomer(Customer customer) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "INSERT INTO customers (id, first_name, last_name, phone_number, vehicle_id) VALUES \n" +
                    "(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getPhoneNumber());
            preparedStatement.setInt(5, customer.getVehicleId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteVehicle(int serialNumber) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "DELETE FROM vehicles WHERE serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, serialNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "DELETE FROM customers WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void updateVehicle(int id, int serialNumber, String model, double price) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "UPDATE vehicles SET serial_number = ?, model = ?, price = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, serialNumber);
            preparedStatement.setString(2, model);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void updateRentedVehicle(int id, boolean rentedStatus, Date rentedDate, int customerId) {
        try (Connection connection = getConnection()) {
            String sqlQuery = "UPDATE vehicles SET rented_status = ?, rented_date = ?, customer_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setBoolean(1, rentedStatus);
            preparedStatement.setDate(2, rentedDate);
            preparedStatement.setInt(3, customerId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e + " updateRentedVehicle() method");
        }
    }

    @Override
    public int countCustomerTableRows() {
        int count = 0;
        try (Connection connection = getConnection()) {
            String sqlQuery = "Select count(*) from customers";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e + " countCustomerTableRows()");
        }
        return count;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rentals_javafx",
                    "root",
                    "mjcomeback15");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
