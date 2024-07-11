package com.deathWingIN.edumanage.controller;

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

public class LoginFormController {

    public AnchorPane context;
    public TextField txtEmail;
    public PasswordField txtPassword;

    private Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/lms", "root", "1234");
    }

    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ForgotPasswordForm");
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignUpForm");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void loginOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText().trim().toLowerCase();
        String inputPassword = txtPassword.getText().trim();

        try (Connection connection = getDatabaseConnection()) {
            User user = getUserByEmail(email, connection);
            if (user != null && new PasswordManager().checkPassword(inputPassword, user.getPassword())) {
                setUI("DashboardForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Email or Password").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Database connection failed: " + e.getMessage()).show();
        }
    }

    private User getUserByEmail(String email, Connection connection) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString(4)
                    );
                }
            }
        }
        return null;
    }
}