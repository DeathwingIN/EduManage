package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.model.Program;
import com.deathWingIN.edumanage.model.Teacher;
import com.deathWingIN.edumanage.view.tm.ProgramTm;
import com.deathWingIN.edumanage.view.tm.TechAddTm;
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
import java.util.ArrayList;

public class ProgramsFormController {

    public AnchorPane context;
    public TextField txtId;
    public TextField txtName;
    public TextField txtSearch;
    public Button btn;
    public TableView<ProgramTm> tblPrograms;
    public TableColumn<?, ?> colId;
    public TableColumn<?, ?> colProgram;
    public TableColumn<?, ?> colTeacher;
    public TableColumn<?, ?> colTechnologies;
    public TableColumn<?, ?> colCost;
    public TableColumn<?, ?> colOption;
    public TextField txtCost;
    public ComboBox<String> cmbTeacher;
    public TextField txtTechnology;
    public TableView<TechAddTm> tblTechnologies;
    public TableColumn colTCode;
    public TableColumn colTName;
    public TableColumn colTRemove;


    public void initialize() {

        setProgramCode();
        setTeachers();
        loadPrograms();

        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        colTechnologies.setCellValueFactory(new PropertyValueFactory<>("btnTech"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        colTCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colTName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTRemove.setCellValueFactory(new PropertyValueFactory<>("btn"));


        //-----------------
        txtTechnology.textProperty().addListener((observable, oldValue, newValue) -> {


        });
    }


    ObservableList<TechAddTm> tmList = FXCollections.observableArrayList();

    ArrayList<String> teachersArray = new ArrayList<>();

    private void setTeachers() {

        for (Teacher t : Database.TeacherTable) {
            teachersArray.add(t.getCode() + " ." + t.getName());
        }

        ObservableList<String> obList = FXCollections.observableArrayList(teachersArray);
        cmbTeacher.setItems(obList);

    }

    private void setProgramCode() {

        if (!Database.ProgramTable.isEmpty()) {
            Program lastProgram = Database.ProgramTable.get(Database.ProgramTable.size() - 1);


            String lastId = lastProgram.getCode();
            String splitData[] = lastId.split("-");
            String lastIdIntegerNumberAsAString = splitData[1];
            int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
            lastIntegerIdAsInt++;
            String generatedTeacherId = "P-" + lastIntegerIdAsInt;
            txtId.setText(generatedTeacherId);

        } else {
            txtId.setText("P-1");
        }

    }


    public void backToHomeOnAction(ActionEvent event) throws IOException {
        setUI("DashboardForm");
    }


    public void newProgramOnAction(ActionEvent event) {

    }


    public void saveOnAction(ActionEvent event) {


        String[] selectedTechs = new String[tmList.size()];
        int pointer = 0;

        for (TechAddTm t : tmList
        ) {
            selectedTechs[pointer] = t.getName();
            pointer++;
        }

        if (btn.getText().equals("Save Program")) {
            Program program = new Program(
                    txtId.getText(),
                    txtName.getText(),
                    selectedTechs,
                    cmbTeacher.getValue().split("\\.")[0],
                    Double.parseDouble(txtCost.getText())

            );
            Database.ProgramTable.add(program);
            new Alert(Alert.AlertType.INFORMATION, "Program Saved!").show();
            loadPrograms();
        }


    }


    private void loadPrograms() {
        ObservableList<ProgramTm> programsTmList = FXCollections.observableArrayList();
        for (Program p : Database.ProgramTable) {
            Button techButton = new Button("Show Tech");
            Button removeButton = new Button("Delete");
            ProgramTm tm = new ProgramTm(
                    p.getCode(),
                    p.getName(),
                    p.getTeacherId(),
                    techButton,
                    removeButton,
                    p.getCost()
            );
            programsTmList.add(tm);
        }

        tblPrograms.setItems(programsTmList);


    }


    private void setUI(String location) throws IOException {

        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }

    public void addTechOnAction(ActionEvent actionEvent) {
        if (!isExists(txtTechnology.getText())) {
            Button btn = new Button("Remove");
            TechAddTm tm = new TechAddTm(
                    tmList.size() + 1, txtTechnology.getText().trim(), btn);
            tmList.add(tm);
            tblTechnologies.setItems(tmList);
            txtTechnology.clear();
        } else {
            txtTechnology.selectAll();
            new Alert(Alert.AlertType.WARNING, "Technology Already Exists").show();
        }


    }

    private boolean isExists(String techName) {

        for (TechAddTm t : tmList) {
            if (t.getName().equals(techName)) {
                return true;
            }
        }
        return false;
    }
}
