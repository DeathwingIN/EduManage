package com.deathWingIN.edumanage.controller;

import com.deathWingIN.edumanage.db.Database;
import com.deathWingIN.edumanage.db.DbConnection;
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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


    String searchText = "";

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

        try {
            for (Student st : searchStudents(searchText)) {
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


                        try {
                            if (deleteStudent(st.getStudentId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Student Deleted").show();
                                setTableData(searchText);
                                setStudentId();

                            } else {
                                new Alert(Alert.AlertType.ERROR, "Try Again").show();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            new Alert(Alert.AlertType.ERROR, e.toString()).show();
                        }


                    }
                });

                obList.add(tm);


            }
            tbStudent.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }


    }

    private void setStudentId() {

        try {
            String lastId = getLastId();
            if (null != lastId) {
                String splitData[] = lastId.split("-");
                String lastIdIntegerNumberAsAString = splitData[1];
                int lastIntegerIdAsInt = Integer.parseInt(lastIdIntegerNumberAsAString);
                lastIntegerIdAsInt++;
                String generatedStudentId = "S-" + lastIntegerIdAsInt;
                txtId.setText(generatedStudentId);
            } else {
                txtId.setText("S-1");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void saveOnAction(ActionEvent actionEvent) {
        Student student = new Student(
                txtId.getText(),
                txtName.getText(),
                Date.from(txtDOB.getValue().atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant()),
                txtAddress.getText()
        );
        if (btn.getText().equalsIgnoreCase("Save Student")) {
            try {
                if (saveStudent(student)) {
                    setStudentId();
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Saved!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again !").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }

        } else {

            try {
                if (updateStudent(student)) {
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.CONFIRMATION, "Student Updated!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again !").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }

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


    private boolean saveStudent(Student student) throws ClassNotFoundException, SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?)");
        preparedStatement.setString(1, student.getStudentId());
        preparedStatement.setString(2, student.getFullName());
        preparedStatement.setObject(3, student.getDateOfBirth());
        preparedStatement.setString(4, student.getAddress());
        return preparedStatement.executeUpdate() > 0;

    }


    private String getLastId() throws ClassNotFoundException, SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT student_id FROM student ORDER BY CAST(SUBSTRING(student_id,3) AS SIGNED) DESC LIMIT 1");

        ResultSet resultSheet = preparedStatement.executeQuery();
        if (resultSheet.next()) {
            return resultSheet.getString(1);
        }
        return null;
    }


    private List<Student> searchStudents(String text) throws ClassNotFoundException, SQLException {

        text = "%" + text + "%";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE full_name LIKE ? OR address LIKE ? ");
        preparedStatement.setString(1, text);
        preparedStatement.setString(2, text);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Student> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getString(4)
            ));
        }
        return list;

    }


    private boolean deleteStudent(String id) throws ClassNotFoundException, SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE student_id=?");
        preparedStatement.setString(1, id);

        return preparedStatement.executeUpdate() > 0;

    }

    private boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE student SET full_name=?,dob=?,address=? WHERE student_id=?");
        preparedStatement.setString(1, student.getFullName());
        preparedStatement.setObject(2, student.getDateOfBirth());
        preparedStatement.setString(3, student.getAddress());
        preparedStatement.setString(4, student.getStudentId());
        return preparedStatement.executeUpdate() > 0;

    }


}

