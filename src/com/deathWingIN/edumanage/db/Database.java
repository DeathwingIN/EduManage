package com.deathWingIN.edumanage.db;

import com.deathWingIN.edumanage.model.Student;
import com.deathWingIN.edumanage.model.Teacher;
import com.deathWingIN.edumanage.model.User;
import com.deathWingIN.edumanage.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {


    public static ArrayList<User> UserTable = new ArrayList<>();
    public static ArrayList<Student> StudentTable = new ArrayList<>();
    public static ArrayList<Teacher> TeacherTable = new ArrayList<>();

    static {
        UserTable.add(new User(
                "admin",
                "admin",
                "a@a.com",
                new PasswordManager().encrypt("123"))
        );

    }


}
