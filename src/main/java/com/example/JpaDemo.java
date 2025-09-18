// src/main/java/com/example/JpaDemo.java
package com.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class JpaDemo {
    public static void main(String[] args) {
        System.out.println("--- Running JPA Demo ---");

        // Step 1: Create an EntityManagerFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("employee-pu");
        // Step 2: Obtain an EntityManager
        EntityManager em = emf.createEntityManager();

        try {
            // Step 3: Start a Transaction
            em.getTransaction().begin();

            // Create a new Employee object
            Employee newEmployee = new Employee("Jane", "Smith", "Marketing");

            // Step 4: Persist the Entity
            em.persist(newEmployee);
            System.out.println("✅ Persisted new employee: Jane Smith");

            // Step 5: Commit the Transaction
            em.getTransaction().commit();
            System.out.println("✅ Transaction committed!");

            // Now, let's query for all employees using JPA's Query Language (JPQL)
            List<Employee> employees = em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();

            System.out.println("\n--- All Employees (from JPA) ---");
            for (Employee emp : employees) {
                System.out.println(emp.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            // Step 6: Close resources
            em.close();
            emf.close();
            System.out.println("\n✅ EntityManager and Factory closed.");
        }
    }
}
