package sample.carrentalsapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.carrentalsapp.exceptions.LoginException;
import sample.carrentalsapp.model.Admin;
import sample.carrentalsapp.utils.ErrorMessages;
import sample.carrentalsapp.utils.UtilsImpl;

import java.io.IOException;

public class LoginController {

    private final UtilsImpl utilsImpl = new UtilsImpl();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    public void setAdminScene(ActionEvent event) throws IOException {
            Admin admin = createAdmin();
            if (usernameField.getText().equals(admin.getEmail()) && passwordField.getText().equals(admin.getPassword())) {
                Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root));
                window.setTitle("Admin panel");

            } else {
                utilsImpl.errorAlert("Invalid login", ErrorMessages.INVALID_USERNAME_OR_PASSWORD);
                throw new LoginException(ErrorMessages.INVALID_USERNAME_OR_PASSWORD.getMessage());

            }
    }

    public void setCustomerScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("customer.fxml"));
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        window.setTitle("Customer panel");
    }

        private Admin createAdmin() {
        Admin admin = new Admin("gabi", "123");
        return admin;
    }
}