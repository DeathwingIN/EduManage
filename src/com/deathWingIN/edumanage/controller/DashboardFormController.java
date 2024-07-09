package com.deathWingIN.edumanage.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {

    public AnchorPane context;
    public Label lblDate;
    public Label lblTime;


    public void initialize() {
        setData();
    }

    private void setData() {

        /*
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String textDate = dateFormat.format(date);
        lblDate.setText(textDate);
        */

        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        lblTime.setText(new SimpleDateFormat("hh:mm:ss a").format(new Date()));

        Timeline timeLine = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        e -> {
                            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
                            lblTime.setText(java.time.LocalTime.now().format(dateFormatter));
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();


    }


    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");

    }

    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }

    public void openStudentFormOnAction(ActionEvent actionEvent) throws IOException {
        setUI("StudentForm");
    }

    public void openProgramFormOnActiojn(ActionEvent actionEvent) {
    }

    public void openIntakeFormOnAction(ActionEvent actionEvent) {
    }

    public void openTeacherFormOnAction(ActionEvent actionEvent) throws IOException {
        setUI("TeacherForm");
    }

    public void openRegistrationFormOnAction(ActionEvent actionEvent) {
    }
}
