module sample.carrentalsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires mysql.connector.java;
    requires com.oracle.database.jdbc;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires static lombok;
    requires org.mapstruct.processor;
    requires org.mapstruct;

    opens sample.carrentalsapp to javafx.fxml;
    exports sample.carrentalsapp;
    exports sample.carrentalsapp.controller;
    opens sample.carrentalsapp.controller to javafx.fxml;
    exports sample.carrentalsapp.model;
    opens sample.carrentalsapp.model to javafx.fxml;
    exports sample.carrentalsapp.utils;
    opens sample.carrentalsapp.utils to javafx.fxml;
}