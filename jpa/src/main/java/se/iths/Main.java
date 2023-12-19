package se.iths;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {

    private static final Scanner sc =  new Scanner(System.in);

    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();


        CrudStudent.createStudent("Jultomten");
        CrudStudent.updateStudent("Jultomten", "Santa");
        CrudGrade.createGrade("Santa", "Javaprogrammering", "IG");
        CrudGrade.updateGrade("Santa", "Javaprogrammering", "G");
        CrudStudent.readStudent("Santa");
        //CrudStudent.deleteStudent("Santa");
        menu(em);

        em.close();
    }

    private static void printAction() {
        System.out.println("\nMenu\n");
        System.out.println("""
                0. Exit
                1. Program
                2. Teacher
                3. Administration
                4. Show menu choices
                """);
    }

    static void menu(EntityManager em){
        boolean running = true;
        printAction();

        while (running){
            System.out.print("\nChoose ('4' to show menu again): ");
            String choice = sc.nextLine();

            switch (choice){
                case "0" -> {
                    System.out.println("\nExited");
                    running = false;
                }
                case "1" -> StatisticsProgram.statisticsForAllCourses(em);
                case "2" -> CrudStudent.crudTest();
                case "3" -> CrudStudent.crudTest();
                case "4" -> printAction();
            };



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
