package sample.carrentalsapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.carrentalsapp.exceptions.AdminException;
import sample.carrentalsapp.model.Customer;
import sample.carrentalsapp.model.Vehicle;
import sample.carrentalsapp.jdbc.DatabaseDAO;
import sample.carrentalsapp.jdbc.DatabaseDAOImpl;
import sample.carrentalsapp.utils.ErrorMessages;
import sample.carrentalsapp.utils.UtilsImpl;

import java.io.IOException;
import java.util.List;

public class AdminController {

    private final UtilsImpl utilsImpl = new UtilsImpl();
    private final DatabaseDAO databaseDAO = new DatabaseDAOImpl();

    public void addVehicle() {

        Stage stage = new Stage();
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label serialNum = new Label("Serial Number");
        gridPane.add(serialNum, 0, 0);
        serialNum.setStyle("-fx-text-fill: white");
        TextField serialNumberField = new TextField();
        gridPane.add(serialNumberField, 1, 0);

        Label model = new Label("Model");
        gridPane.add(model, 0, 1);
        model.setStyle("-fx-text-fill: white");
        TextField modelField = new TextField();
        gridPane.add(modelField, 1, 1);

        Label price = new Label("Price");
        gridPane.add(price, 0, 2);
        price.setStyle("-fx-text-fill: white");
        TextField priceField = new TextField();
        gridPane.add(priceField, 1, 2);

        Button addVehicleButton = new Button("Add");
        gridPane.add(addVehicleButton, 0, 3);

        Scene scene = new Scene(gridPane, 300, 300);
        gridPane.setStyle("-fx-background-color:  #2D3447");
        stage.setScene(scene);
        stage.setTitle("Add vehicle");
        stage.show();

        addVehicleButton.setOnAction((ActionEvent actionEvent) -> {

            Vehicle vehicle = new Vehicle();
            String serialNumberValidation = serialNumberField.getText();
            String modelValidation = modelField.getText();
            String priceValidation = priceField.getText();

            if (serialNumberValidation.isEmpty() || modelValidation.isEmpty() || priceValidation.isEmpty()) {
                utilsImpl.errorAlert("Wrong completion", ErrorMessages.WRONG_COMPLETE_FIELDS);
                throw new AdminException(ErrorMessages.WRONG_COMPLETE_FIELDS.getMessage());
            }

            int sn = databaseDAO.getSerialNumberFromDatabase(Integer.parseInt(serialNumberValidation));

            if (Integer.parseInt(serialNumberValidation) == sn) {
                utilsImpl.errorAlert("Add vehicle failed", ErrorMessages.INVALID_SERIAL_NUMBER);
                throw new AdminException(ErrorMessages.INVALID_SERIAL_NUMBER.getMessage());
            }

            vehicle.setSerialNumber(Integer.parseInt(serialNumberField.getText()));
            vehicle.setModel(modelField.getText());
            vehicle.setPrice(Double.parseDouble(priceField.getText()));
            vehicle.setRented(false);
            vehicle.setRentedDate(null);
            vehicle.setReturnDate(null);

            databaseDAO.addVehicle(vehicle);
            utilsImpl.successAlert("Vehicle " + vehicle.getModel() + " added successfully");
            stage.hide();

        });
    }

    public void getVehicles() {

        List<Vehicle> vehicleList = databaseDAO.getVehicles();
        TableView<Vehicle> tableView = new TableView<Vehicle>();

        Stage stage = new Stage();

        TableColumn<Vehicle, Long> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vehicle, Integer> serialNumber = new TableColumn<>("S/N");
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        TableColumn<Vehicle, String> model = new TableColumn<>("Model");
        model.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, Boolean> rented = new TableColumn<>("Rented Status");
        rented.setCellValueFactory(new PropertyValueFactory<>("rented"));

        TableColumn<Vehicle, Double> price = new TableColumn<>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Vehicle, String> rentedDate = new TableColumn<>("Rented on");
        rentedDate.setCellValueFactory(new PropertyValueFactory<>("rentedDate"));

        TableColumn<Vehicle, String> returnDate = new TableColumn<>("Returned on");
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        TableColumn<Vehicle, Integer> customerId = new TableColumn<>("Customer Id");
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        tableView.getColumns().add(id);
        tableView.getColumns().add(serialNumber);
        tableView.getColumns().add(model);
        tableView.getColumns().add(price);
        tableView.getColumns().add(rented);
        tableView.getColumns().add(rentedDate);
        tableView.getColumns().add(returnDate);
        tableView.getColumns().add(customerId);
        utilsImpl.addUpdateButtonToTable(tableView);
        utilsImpl.addDeleteButtonToTable(tableView);

        vehicleList.forEach(vehicle -> tableView.getItems().add(vehicle));

        Scene scene = new Scene(tableView, 750, 500);
        stage.setScene(scene);
        stage.setTitle("All vehicles");
        stage.show();

    }

    public void showCustomers(ActionEvent event) {

        List<Customer> customerList = databaseDAO.getCustomers();
        TableView<Customer> tableView = new TableView<Customer>();

        Stage stage = new Stage();

        TableColumn<Customer, Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Customer, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Customer, String> phoneNumber = new TableColumn<>("Phone number");
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Customer, Integer> vehicleId = new TableColumn<>("Vehicle Id");
        vehicleId.setCellValueFactory(new PropertyValueFactory<>("vehicleId"));

        tableView.getColumns().add(id);
        tableView.getColumns().add(firstName);
        tableView.getColumns().add(lastName);
        tableView.getColumns().add(phoneNumber);
        tableView.getColumns().add(vehicleId);
        utilsImpl.addDeleteButtonToCustomerTable(tableView);

        customerList.forEach(customer -> tableView.getItems().add(customer));

        Scene scene = new Scene(tableView, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Customers");
        stage.show();

    }

    public void changeToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Login");

//        System.out.println("java version: "+System.getProperty("java.version"));
//        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
    }

}
