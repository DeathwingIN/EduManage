package com.deathWingIN.edumanage.model;

import java.util.Date;

public class Intake {


    private String intakeId;
    private String intakeName;
    private Date intakeDate;

    public Intake() {
    }

    public Intake(String intakeId, String intakeName, Date intakeDate, String programId) {
        this.intakeId = intakeId;
        this.intakeName = intakeName;
        this.intakeDate = intakeDate;
        this.programId = programId;
    }

    private String programId;


    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public Date getIntakeDate() {
        return intakeDate;
    }

    public void setIntakeDate(Date intakeDate) {
        this.intakeDate = intakeDate;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "Intake{" +
                "intakeId='" + intakeId + '\'' +
                ", intakeName='" + intakeName + '\'' +
                ", intakeDate=" + intakeDate +
                ", programId='" + programId + '\'' +
                '}';
    }
}
