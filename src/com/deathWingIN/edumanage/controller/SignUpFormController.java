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
import java.sql.*;

public class SignUpFormController {


    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtFirstName;
    public TextField txtLastName;
    public PasswordField txtPassword;

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {

        setUI("LoginForm");

    }


    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {

        String email = txtEmail.getText().trim().toLowerCase();
        String firstName = txtFirstName.getText().toLowerCase();
        String lastName = txtLastName.getText().toLowerCase();
        String password = new PasswordManager().encrypt(txtPassword.getText().trim());
        User createUser = new User(firstName, lastName, email, password);

        try {
            boolean isSaved = signup(createUser);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Registered Successfully").show();
                setUI("LoginForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }

        } catch (SQLException | ClassNotFoundException e1) {
            new Alert(Alert.AlertType.ERROR, e1.toString()).show();
        }

    }


    //==============

    public boolean signup(User user) throws ClassNotFoundException, SQLException {

        //load driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Create connection

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");


        //Write SQL
        String sql = "INSERT INTO user VALUES (?,?,?,?)";

        //create statement
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        //set sql into the statement
        return statement.executeUpdate(sql) > 0;


    }

}
