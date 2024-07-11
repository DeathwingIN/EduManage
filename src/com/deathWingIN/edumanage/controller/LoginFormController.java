package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.DbConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controls the login form, handling user authentication and navigation based on login success or failure.
 */
public class LoginFormController {

    public AnchorPane context; // The root pane of the login form UI.
    public TextField txtEmail; // Input field for the user's email.
    public PasswordField txtPassword; // Input field for the user's password.

    /**
     * Handles the action of clicking the "Forgot Password" button.
     * Navigates the user to the ForgotPasswordForm.
     */
    public void forgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
        setUI("ForgotPasswordForm");
    }

    /**
     * Handles the action of clicking the "Create an Account" button.
     * Navigates the user to the SignUpForm.
     */
    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUI("SignUpForm");
    }

    /**
     * Utility method for changing the UI based on the given FXML file name.
     * @param location The FXML file name to load.
     */
    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    /**
     * Handles the login action when the "Login" button is pressed.
     * Validates the user's credentials and navigates to the dashboard upon success.
     */
    public void loginOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText().trim().toLowerCase();
        String inputPassword = txtPassword.getText().trim();

        try {
            User user = getUserByEmail(email);
            if (user != null && PasswordManager.checkPassword(inputPassword, user.getPassword())) {
                setUI("DashboardForm");
            } else {
                new Alert(Alert.AlertType.WARNING, "Invalid Email or Password").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection failed: " + e.getMessage()).show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load the dashboard: " + e.getMessage()).show();
        }
    }

    /**
     * Retrieves a user by their email from the database.
     * @param email The email of the user to retrieve.
     * @return The User object if found, null otherwise.
     */
    private User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
    String sql = "SELECT * FROM user WHERE email = ?";
    Connection connection = DbConnection.getInstance().getConnection();
    if (connection.isClosed()) {
        connection = DbConnection.getInstance().getConnection();
    }
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, email);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return new User(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        }
    }
    return null;
}
}