package com.deathWingIN.edumanage.view.tm;

import javafx.scene.control.Button;

public class StudentTm {


    private String id;
    private String fullName;
    private String address;
    private String dob;
    private Button btn;



    public StudentTm() {
    }

    public StudentTm(String id, String fullName, String address, String dob, Button btn) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.dob = dob;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "StudentTm{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", btn=" + btn +
                '}';
    }
}



