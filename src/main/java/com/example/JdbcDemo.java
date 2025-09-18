// src/main/java/com/example/JdbcDemo.java
package com.example;

import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        // Database connection details for Gitpod's PostgreSQL
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "gitpod";
        String password = ""; // Gitpod's default user has no password
        Connection con = null;

        System.out.println("--- Running JDBC Demo ---");

        try {
            // Step 1: Register the Driver (optional in modern JDBC)
            Class.forName("org.postgresql.Driver");

            // Step 2: Create a Connection
            con = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connection established!");

            // Step 3: Create a Statement
            Statement stmt = con.createStatement();
            
            // Create a table (if it doesn't exist from the JPA run)
            stmt.execute("CREATE TABLE IF NOT EXISTS Employee (id SERIAL PRIMARY KEY, firstName VARCHAR(50), lastName VARCHAR(50), department VARCHAR(50))");
            
            // Execute an INSERT statement
            String insertSql = "INSERT INTO Employee(firstName, lastName, department) VALUES ('John', 'Doe', 'Engineering')";
            stmt.executeUpdate(insertSql);
            System.out.println("✅ Inserted new employee: John Doe");

            // Step 4: Execute a Query
            ResultSet rs = stmt.executeQuery("SELECT * FROM Employee");
            System.out.println("\n--- All Employees (from JDBC) ---");
            while (rs.next()) {
                // Retrieve by column name
                int id = rs.getInt("id");
                String first = rs.getString("firstName");
                String last = rs.getString("lastName");
                String dept = rs.getString("department");
                System.out.println("ID: " + id + ", Name: " + first + " " + last + ", Dept: " + dept);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the Connection
            if (con != null) {
                try {
                    con.close();
                    System.out.println("\n✅ Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
