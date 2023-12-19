package se.iths;

import jakarta.persistence.*;

import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        //CrudStudent.createStudent("Thomas", "Javaprogrammering");

        menu(em);


        em.close();
    }

    private static void printActionMainMenu() {
        System.out.println("\nMenu\n");
        System.out.println("""
                0. Exit
                1. Program
                2. Teacher
                3. Administration
                4. Show menu choices
                """);
    }

    static void menu(EntityManager em) {
        boolean running = true;
        printActionMainMenu();

        while (running) {
            System.out.print("\nChoose ('4' to show menu again): ");
            String choice = sc.nextLine();

            switch (choice) {
                case "0" -> {
                    System.out.println("\nExited");
                    running = false;
                }
                case "1" -> menuProgram(em);
                case "2" -> CrudStudent.crudTest();
                case "3" -> CrudStudent.crudTest();
                case "4" -> printActionMainMenu();
            }
        }
    }

    private static void printActionProgramMenu() {
        System.out.println("\nProgram menu\n");
        System.out.println("""
                0. Exit
                1. Statistics for specific course
                2. -
                3. -
                4. Go back to main menu
                """);
    }

    static void menuProgram(EntityManager em) {
        boolean running = true;
        printActionProgramMenu();

        while (running) {
            System.out.print("\nChoose ('4' to go back to main menu): ");
            String choice = sc.nextLine();

            switch (choice) {
                case "0" -> {
                    System.out.println("\nExited");
                    running = false;
                }
                case "1" -> StatisticsProgram.statisticsForSpecificCourse(em);
                //case "2" ->
                //case "3" ->
                case "4" -> menu(em);
            }
            ;

        }
    }

    static void inTransaction(Consumer<EntityManager> work) {
        try (EntityManager entityManager = JPAUtil.getEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                work.accept(entityManager);
                transaction.commit();
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }
}
