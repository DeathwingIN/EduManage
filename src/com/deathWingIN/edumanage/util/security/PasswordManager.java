package com.deathWingIN.edumanage.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {

 public String encrypt(String rowPassword) {
     return BCrypt.hashpw(rowPassword, BCrypt.gensalt(10));
 }

 public static boolean checkPassword(String rowPassword, String hashPassword) {
     return BCrypt.checkpw(rowPassword, hashPassword);
 }

}
