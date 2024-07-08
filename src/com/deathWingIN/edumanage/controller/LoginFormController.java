package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.model.User;
import com.deathWingIN.edumanage.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        String email = txtEmail.getText().trim().toLowerCase();
        String password = txtPassword.getText().trim();
        boolean emailFound = false;

        for (User user : Database.UserTable) {
            if (user.getEmail().equals(email)) {
                emailFound = true;
                if (new PasswordManager().checkPassword(password, user.getPassword())) {
                    setUI("DashboardForm");
                    break; // Exit the loop as soon as a match is found
                } else {
                    new Alert(Alert.AlertType.WARNING, "Wrong Password").show();
                    return; // Exit the method if the password is wrong
                }
            }
        }

        if (!emailFound) {
            new Alert(Alert.AlertType.WARNING, "User Not Found").show();
        }
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
