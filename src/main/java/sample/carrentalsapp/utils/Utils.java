package sample.carrentalsapp.utils;

import javafx.scene.control.TableView;

public interface Utils {

    void addRentButtonToTable(TableView tableView);

    void addDeleteButtonToTable(TableView tableView);

    void addUpdateButtonToTable(TableView tableView);

    void addDeleteButtonToCustomerTable(TableView tableView);

    void errorAlert(String title, ErrorMessages choseError);

    void successAlert(String content);
}
