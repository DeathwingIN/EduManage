package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpFormController {


    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtFirstName;
    public TextField txtLastName;
    public PasswordField txtPassword;

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {

        setUI("LoginForm");

    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {

        String email = txtEmail.getText().trim().toLowerCase();
        String firstName = txtFirstName.getText().toLowerCase();
        String lastName = txtLastName.getText().toLowerCase();
        String password = txtPassword.getText().trim();
        Database.UserTable.add(new User(firstName, lastName, email, password));
        new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully").show();
        setUI("LoginForm");



    }


    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
