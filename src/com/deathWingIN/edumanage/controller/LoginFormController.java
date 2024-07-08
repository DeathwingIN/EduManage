package com.deathWingIN.edumanage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {

    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;


    public void forgotPasswordOnAction(ActionEvent actionEvent) {
    }

    public void loginOnAction(ActionEvent actionEvent) {
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {

        setUI("SignUpForm");
    }


    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }


}
