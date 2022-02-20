package sample.carrentalsapp.utils;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.carrentalsapp.exceptions.AdminException;
import sample.carrentalsapp.exceptions.CustomerException;
import sample.carrentalsapp.model.Customer;
import sample.carrentalsapp.model.Vehicle;
import sample.carrentalsapp.jdbc.DatabaseDAO;
import sample.carrentalsapp.jdbc.DatabaseDAOImpl;

import java.sql.Date;
import java.time.LocalDate;

public class UtilsImpl implements Utils {

    private final DatabaseDAO databaseDAO = new DatabaseDAOImpl();

    @Override
    public void addRentButtonToTable(TableView tableView)  {
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Vehicle, Void> call(TableColumn<Vehicle, Void> vehicleVoidTableColumn) {
                final TableCell<Vehicle, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Rent");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            Stage stage = new Stage();
                            GridPane gridPane = new GridPane();

                            gridPane.setAlignment(Pos.TOP_LEFT);
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);
                            gridPane.setPadding(new Insets(25, 25, 25, 25));

                            Label firstName = new Label("First Name");
                            gridPane.add(firstName, 0, 0);
                            TextField firstNameField = new TextField();
                            gridPane.add(firstNameField,1,0);

                            Label lastName = new Label("Last Name");
                            gridPane.add(lastName, 0, 1);
                            TextField lastNameField = new TextField();
                            gridPane.add(lastNameField,1,1);

                            Label phoneNumber = new Label("Phone number");
                            gridPane.add(phoneNumber, 0, 2);
                            TextField phoneNumberField = new TextField();
                            gridPane.add(phoneNumberField, 1, 2);

                            Button rentButton = new Button("Rent Vehicle");
                            gridPane.add(rentButton, 0, 3);

                            Scene scene = new Scene(gridPane, 300, 300);
                            stage.setScene(scene);
                            stage.setTitle("Rent vehicle");
                            if (vehicle.isRented()) {
                                btn.setDisable(true);
                                stage.hide();
                            } else {
                                stage.show();
                            }

                            rentButton.setOnAction((ActionEvent actionEvent) -> {
                                Customer customer = new Customer();

                                String firstNameValidation = firstNameField.getText();
                                String lastNameValidation = lastNameField.getText();
                                String phoneNumberValidation = phoneNumberField.getText();

                                if (firstNameValidation.isEmpty() || lastNameValidation.isEmpty() || phoneNumberValidation.isEmpty()) {
                                    errorAlert("Wrong completion", ErrorMessages.WRONG_COMPLETE_FIELDS);
                                    throw new CustomerException(ErrorMessages.WRONG_COMPLETE_FIELDS.getMessage());
                                }

                                customer.setFirstName(firstNameField.getText());
                                customer.setLastName(lastNameField.getText());
                                customer.setPhoneNumber(phoneNumberField.getText());
                                customer.setVehicleId(vehicle.getId());
                                databaseDAO.addCustomer(customer);

                                Date date = Date.valueOf(LocalDate.now());
                                databaseDAO.updateRentedVehicle(vehicle.getId(), true, date, databaseDAO.countCustomerTableRows());
                                successAlert("Vehicle " + vehicle.getModel() + " was successfully rented.");
                                stage.close();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
                return cell;
            }
        };
        TableColumn<Vehicle, Void> button = new TableColumn<>("Rent vehicle");
        button.setCellFactory(cellFactory);
        tableView.getColumns().add(button);

    }

    @Override
    public void addDeleteButtonToTable(TableView tableView)  {
        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Vehicle, Void> call(TableColumn<Vehicle, Void> vehicleVoidTableColumn) {
                final TableCell<Vehicle, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            if (vehicle.isRented()) {
                                btn.setDisable(true);
                            } else {
                                databaseDAO.deleteVehicle(vehicle.getSerialNumber());
                                ((Node)event.getSource()).getScene().getWindow().hide();
                                successAlert("Vehicle " + vehicle.getModel() + " deleted successfully");
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
                return cell;
            }
        };
        TableColumn<Vehicle, Void> button = new TableColumn<>("Delete vehicle");
        button.setCellFactory(cellFactory);
        tableView.getColumns().add(button);

    }

    @Override
    public void addUpdateButtonToTable(TableView tableView) {

        Callback<TableColumn<Vehicle, Void>, TableCell<Vehicle, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Vehicle, Void> call(TableColumn<Vehicle, Void> vehicleVoidTableColumn) {
                final TableCell<Vehicle, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Update");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vehicle vehicle = getTableView().getItems().get(getIndex());
                            Stage stage = new Stage();
                            GridPane gridPane = new GridPane();

                            gridPane.setAlignment(Pos.TOP_LEFT);
                            gridPane.setHgap(10);
                            gridPane.setVgap(10);
                            gridPane.setPadding(new Insets(25, 25, 25, 25));

                            Label serialNum = new Label("Serial Number");
                            gridPane.add(serialNum, 0, 0);
                            TextField serialNumberField = new TextField();
                            gridPane.add(serialNumberField,1,0);

                            Label model = new Label("Model");
                            gridPane.add(model, 0, 1);
                            TextField modelField = new TextField();
                            gridPane.add(modelField,1,1);

                            Label price = new Label("Price");
                            gridPane.add(price, 0, 2);
                            TextField priceField = new TextField();
                            gridPane.add(priceField, 1, 2);

                            Button updateButton = new Button("Update");
                            gridPane.add(updateButton, 0, 3);

                            Scene scene = new Scene(gridPane, 300, 300);
                            stage.setScene(scene);
                            stage.setTitle("Update vehicle");
                            if (vehicle.isRented()) {
                                btn.setDisable(true);
                                stage.hide();
                            } else {
                                stage.show();
                            }


                            updateButton.setOnAction((ActionEvent actionEvent) -> {

                                String serialNumberValidation = serialNumberField.getText();
                                String modelValidation = modelField.getText();
                                String priceValidation = priceField.getText();

                                if (serialNumberValidation.isEmpty() || modelValidation.isEmpty() || priceValidation.isEmpty()) {
                                    errorAlert("Wrong completion", ErrorMessages.WRONG_COMPLETE_FIELDS);
                                    throw new AdminException(ErrorMessages.WRONG_COMPLETE_FIELDS.getMessage());
                                }

                                databaseDAO.updateVehicle(
                                        vehicle.getId(),
                                        Integer.parseInt(serialNumberField.getText()),
                                        modelField.getText(),
                                        Double.parseDouble(priceField.getText())
                                );
                                successAlert("Vehicle " + vehicle.getId() + " updated successfully");
                                stage.hide();
                            });
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        TableColumn<Vehicle, Void> button = new TableColumn<>("Update vehicle");
        button.setCellFactory(cellFactory);
        tableView.getColumns().add(button);
    }

    @Override
    public void addDeleteButtonToCustomerTable(TableView tableView)  {

        Callback<TableColumn<Customer,Void>,TableCell<Customer,Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Customer, Void> call(TableColumn<Customer, Void> customerVoidTableColumn) {
                final TableCell<Customer, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Customer customer = getTableView().getItems().get(getIndex());
                            databaseDAO.deleteCustomer(customer.getId());
                            ((Node)event.getSource()).getScene().getWindow().hide();
                            successAlert("Customer " + customer.getFirstName() + " " + customer.getLastName() + " was successfully deleted");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
                return cell;
            }
        };
        TableColumn<Customer, Void> button = new TableColumn<>("Delete customer");
        button.setCellFactory(cellFactory);
        tableView.getColumns().add(button);
    }

    @Override
    public void errorAlert(String title, ErrorMessages choseError) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(choseError.getMessage());
        alert.show();
    }

    @Override
    public void successAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText(content);
        alert.show();
    }

}
