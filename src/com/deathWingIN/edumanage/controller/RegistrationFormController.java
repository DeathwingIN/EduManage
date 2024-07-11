package com.deathWingIN.edumanage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationFormController {


    public TextField txtId;
    public AnchorPane context;
    public Button btn;
    public TextField txtName;
    public RadioButton rBtnPaid;
    public RadioButton rBtnPending;
    public ComboBox<?> cmbProgram;


    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUI("DashboardForm");
    }


    public void saveOnAction(ActionEvent event) {

    }

    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }


}
