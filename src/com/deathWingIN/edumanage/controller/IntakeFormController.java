package com.deathWingIN.edumanage.controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class IntakeFormController {
    public AnchorPane context;
    public TextField txtId;
    public TextField txtSearch;
    public Button btn;
    public TableView<?> tblIntake;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colIntake;
    public TableColumn<?, ?> colStartDate;
    public TableColumn<?, ?> colProgram;
    public TableColumn<?, ?> colCompleteState;
    public TableColumn<?, ?> colOption;
    public TextField txtName;
    public DatePicker txtDate;
    public ComboBox<?> cmbProgram;


    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUI("DashboardForm");
    }

    public void newIntakeOnAction(ActionEvent event) {

    }

    public void saveOnAction(ActionEvent event) {

    }

    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }


}
