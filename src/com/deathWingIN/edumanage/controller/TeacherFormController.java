package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.model.Teacher;
import com.deathWingIN.edumanage.view.tm.TeacherTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class TeacherFormController {

    public AnchorPane teacherContext;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSearch;
    public TextField txtContact;
    public Button btn;
    public TableView<TeacherTm> tblTeachers;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colOptions;
    public TableColumn colId;

    String searchText = "";

    public void initialize() {


        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));


        setTeacherId();
        setTableData(searchText);


        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            setTableData(searchText);
        });

        tblTeachers.getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {

                    if (null != newValue) {
                        setData(newValue);
                    }


                });
    }

    private void setData(TeacherTm tm) {

        txtId.setText(tm.getCode());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());
        btn.setText("Update Teacher");
    }

    private void setTableData(String searchText) {
        ObservableList<TeacherTm> obList = FXCollections.observableArrayList();

        for (Teacher t : Database.TeacherTable) {

            if (t.getName().contains(searchText)) {

                Button btn = new Button("Delete");
                TeacherTm tm = new TeacherTm(

                        t.getCode(),
                        t.getName(),
                        t.getAddress(),
                        t.getContact(),
                        btn
                );

                btn.setOnAction(e -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are you sure whether you want to delete this teacher?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();  // this will show the alert and wait for the user to click the button
                    if (buttonType.get().equals(ButtonType.YES)) {
                        Database.TeacherTable.remove(t);
                        new Alert(Alert.AlertType.INFORMATION, "Teacher Deleted").show();
                        setTableData(searchText);

                    }
                });

                obList.add(tm);

            }


//

        }

        tblTeachers.setItems(obList);
    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    public void newTeachertOnAction(ActionEvent actionEvent) {

        clear();
        setTeacherId();
        btn.setText("Save Teacher");

    }

    public void saveOnAction(ActionEvent actionEvent) {

        if (btn.getText().equalsIgnoreCase("Save Teacher")) {
            Teacher teacher = new Teacher(
                    txtId.getText(),
                    txtName.getText(),
                    txtContact.getText(),
                    txtAddress.getText()
            );

            Database.TeacherTable.add(teacher);
            setTeacherId();
            clear();
            setTableData(searchText);
            new Alert(Alert.AlertType.INFORMATION, "Teacher Saved!").show();
        } else {
            for (Teacher t : Database.TeacherTable) {
                if (t.getCode().equals(txtId.getText())) {
                    t.setAddress(txtAddress.getText());
                    t.setName(txtName.getText());
                    t.setContact(txtContact.getText());
                    setTableData(searchText);
                    clear();
                    setTeacherId();
                    btn.setText("Save Teacher");
                    return;
                }
            }
            new Alert(Alert.AlertType.ERROR, "Invalid Teacher ID").show();
        }


    }

    private void setTeacherId() {

        if (!Database.TeacherTable.isEmpty()) {
            Teacher lastTeacher = Database.TeacherTable.get(Database.TeacherTable.size() - 1);


            String lastId = lastTeacher.getCode();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedTeacherId = "T-" + lastIntegerIdAsInt;
            txtId.setText(generatedTeacherId);

        } else {
            txtId.setText("T-1");
        }
    }

    private void setUI(String location) throws IOException {

        Stage stage = (Stage) teacherContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }

    private void clear() {
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }


}
