package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.model.Student;
import com.deathWingIN.edumanage.view.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;


public class StudentFormController {

    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public DatePicker txtDOB;
    public TableView<StudentTm> tbStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colOptions;
    public TableColumn colDOB;
    public Button btn;


    String searchText="";

    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));


        setStudentId();
        setTableData(searchText);


        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tbStudent.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {

                    if (null != newValue) {
                        setData(newValue);
                    }


                });
    }

    private void setData(StudentTm tm) {

        txtId.setText(tm.getId());
        txtName.setText(tm.getFullName());
        txtAddress.setText(tm.getAddress());
        txtDOB.setValue(LocalDate.parse(tm.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        btn.setText("Update Student");

    }

    private void setTableData(String searchText) {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();

        for (Student st : Database.StudentTable) {

            if (st.getFullName().contains(searchText)) {

                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(

                        st.getStudentId(),
                        st.getFullName(),
                        st.getAddress(),
                        new SimpleDateFormat("yyyy-MM-dd").format(st.getDateOfBirth()),
                        btn
                );

                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure whether you want to delete this student?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();  // this will show the alert and wait for the user to click the button
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.StudentTable.remove(st);
                        new Alert(Alert.AlertType.CONFIRMATION, "Student Deleted").show();
                        setTableData(searchText);

                    }
                });

                obList.add(tm);

            }


//

        }

        tbStudent.setItems(obList);
    }

    private void setStudentId() {

        if (!Database.StudentTable.isEmpty()) {
            Student lastStudent = Database.StudentTable.get(Database.StudentTable.size() - 1);


            String lastId = lastStudent.getStudentId();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedStudentId = "S-" + lastIntegerIdAsInt;
            txtId.setText(generatedStudentId);

        } else {
            txtId.setText("S-1");
        }

    }


    public void saveOnAction(ActionEvent actionEvent) {


        if (btn.getText().equalsIgnoreCase("Save Student")) {
            Student student = new Student(
                    txtId.getText(),
                    txtName.getText(),
                    Date.from(txtDOB.getValue().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()),
                    txtAddress.getText()
            );

            Database.StudentTable.add(student);
            setStudentId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.CONFIRMATION, "Student Saved!").show();
        } else {
            for (Student st : Database.StudentTable) {
                if (st.getStudentId().equals(txtId.getText())) {
                    st.setFullName(txtName.getText());
                    st.setAddress(txtAddress.getText());
                    st.setDateOfBirth(Date.from(txtDOB.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                    setTableData(searchText);
                    clear();
                    setStudentId();
                    btn.setText("Save Student");
                    return;
                }
            }
            new Alert(Alert.AlertType.ERROR, "Invalid Student ID").show();
        }
    }


    private void clear() {
        txtDOB.setValue(null);
        txtName.clear();
        txtAddress.clear();
    }

    public void newStudentOnAction(ActionEvent actionEvent) {

        clear();
        setStudentId();
        btn.setText("Save Student");

    }


    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");

    }
}

