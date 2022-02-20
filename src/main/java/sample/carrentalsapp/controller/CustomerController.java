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
import sample.carrentalsapp.exceptions.CustomerException;
import sample.carrentalsapp.model.Vehicle;
import sample.carrentalsapp.jdbc.DatabaseDAO;
import sample.carrentalsapp.jdbc.DatabaseDAOImpl;
import sample.carrentalsapp.utils.ErrorMessages;
import sample.carrentalsapp.utils.UtilsImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CustomerController {

    private final UtilsImpl utilsImpl = new UtilsImpl();

    private final DatabaseDAO databaseDAO = new DatabaseDAOImpl();

    public void availableVehicles() {

        List<Vehicle> vehicleList =  databaseDAO.getVehicles();
        TableView<Vehicle> tableView = new TableView<Vehicle>();
        Stage stage = new Stage();

        TableColumn<Vehicle, Long> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vehicle, Integer> serialNumber = new TableColumn<>("S/N");
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));

        TableColumn<Vehicle, String> model = new TableColumn<>("Model");
        model.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Vehicle, Double> price = new TableColumn<>("Price/Day");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Vehicle, Boolean> rentedStatus = new TableColumn<>("Rented(Yes/No)");
        rentedStatus.setCellValueFactory(new PropertyValueFactory<>("rented"));

        tableView.getColumns().add(id);
        tableView.getColumns().add(serialNumber);
        tableView.getColumns().add(model);
        tableView.getColumns().add(price);
        tableView.getColumns().add(rentedStatus);
        utilsImpl.addRentButtonToTable(tableView);

        vehicleList.forEach(vehicle -> tableView.getItems().add(vehicle));

        Scene scene = new Scene(tableView, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Available vehicles");
        stage.show();
    }

    public void returnVehicle() {
        Stage stage = new Stage();
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        Label serialNum = new Label("Serial Number");
        serialNum.setStyle("-fx-text-fill: white");
        gridPane.add(serialNum, 0, 0);
        TextField serialNumberField = new TextField();
        gridPane.add(serialNumberField,1,0);

//        Label returnDate = new Label("Return date");
//        gridPane.add(returnDate, 0, 1);
//        TextField returnDateField = new TextField();
//        gridPane.add(returnDateField,1,1);

        Button returnVehicleButton = new Button("Return vehicle");
        gridPane.add(returnVehicleButton, 0, 3);

        Scene scene = new Scene(gridPane, 350, 200);
        gridPane.setStyle("-fx-background-color:  #2D3447");
        stage.setScene(scene);
        stage.setTitle("Return vehicle");
        stage.show();

        returnVehicleButton.setOnAction((ActionEvent actionEvent) -> {

            String serialNumberValidation = serialNumberField.getText();

            if (serialNumberValidation.isEmpty()) {
                utilsImpl.errorAlert("Wrong completion", ErrorMessages.WRONG_COMPLETE_FIELDS);
                throw new CustomerException(ErrorMessages.WRONG_COMPLETE_FIELDS.getMessage());
            }

            int sn = databaseDAO.getSerialNumberFromDatabase(Integer.parseInt(serialNumberValidation));

            if (Integer.parseInt(serialNumberValidation) != sn) {
                utilsImpl.errorAlert("Invalid Serial Number", ErrorMessages.INVALID_SERIAL_NUMBER);
                throw new CustomerException(ErrorMessages.INVALID_SERIAL_NUMBER.getMessage());
            }

            if (!databaseDAO.getVehicleRentedStatus(Integer.parseInt(serialNumberValidation))) {
                utilsImpl.errorAlert("Vehicle not rented", ErrorMessages.INVALID_RENTED_STATUS);
                throw new CustomerException(ErrorMessages.INVALID_RENTED_STATUS.getMessage());
            }

            databaseDAO.returnVehicle(Integer.parseInt(serialNumberField.getText()), java.sql.Date.valueOf(LocalDate.now()));
            utilsImpl.successAlert("Vehicle was returned successfully");
            stage.hide();
        });
    }

    public void changeToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Login");
    }
}
