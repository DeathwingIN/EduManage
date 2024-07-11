package com.deathWingIN.edumanage.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbConnectionTest {

    @Test
    void getInstance() {
        try {
            DbConnection dbConnection1 = DbConnection.getInstance();
            DbConnection dbConnection2 = DbConnection.getInstance();
            assertEquals(dbConnection1, dbConnection2);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void getConnection() {
        try {
            DbConnection dbConnection = DbConnection.getInstance();
            assertNotNull(dbConnection.getConnection());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}