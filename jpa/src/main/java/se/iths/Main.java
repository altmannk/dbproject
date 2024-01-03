package se.iths;

import jakarta.persistence.*;

import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        menu(em);

        em.close();
    }

    public static void printActionMainMenu() {

        System.out.println("\nMenu\n");
        System.out.println("""
                0. Exit
                1. Statistics for specific course
                2. Statistics for all courses
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
                    System.out.println("\nExit");
                    running = false;
                }
                case "1" -> StatisticsProgram.statisticsForSpecificCourse();
                case "2" -> StatisticsProgram.statisticsForAllCourses();
                case "3" -> AdminMenu.crudMenu();
                case "4" -> printActionMainMenu();
            }
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
