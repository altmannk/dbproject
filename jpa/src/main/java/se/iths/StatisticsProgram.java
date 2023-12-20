package se.iths;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class StatisticsProgram {
    private static final Scanner fetch = new Scanner(System.in);


    public static List<String> getListOfCourses() {
        List<String> listOfCourses = new ArrayList<>();
        Main.inTransaction(em -> {
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c", Course.class);
            List<Course > course = query.getResultList();

            for (Course curCourse : course) {
                listOfCourses.add(curCourse.getCourseName());
            }
        });
        return listOfCourses;
    }

    public static List<String> getGradesForAllCourses() {
        List<String> listOfGrades = new ArrayList<>();
                Main.inTransaction(em -> {
                    TypedQuery<Grade> query = em.createQuery("SELECT g FROM Grade g", Grade.class);
                    List<Grade > grades = query.getResultList();

                    for (Grade curGrade : grades) {
                        listOfGrades.add(curGrade.getGradeValue());
                    }
                });
        return listOfGrades;
    }

    /**
     * returns a list of strings containing all the grades for specified course
     */
    public static List<String> getGradesForCourse(String courseName) {
        List<String> listOfGrades = new ArrayList<>();
        Main.inTransaction(em -> {
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
            Course course = query.getSingleResult();
            System.out.println(course.getCourseName());

            TypedQuery<Grade> query2 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeCourseID = :id", Grade.class).setParameter("id", course);
            List<Grade> grades = query2.getResultList();

            for (Grade curGrade : grades) {
                listOfGrades.add(curGrade.getGradeValue());
            }
        });
        return listOfGrades;
    }

    public static void statisticsForAllCourses() {
        List<String> listOfGrades = getGradesForAllCourses();

        // räkna ut hur många som har ig g vg
        double igCounter = 0.0;
        double gCounter = 0.0;
        double vgCounter = 0.0;
        for (String currentGrade : listOfGrades) {
            if (currentGrade.equals("IG"))
                igCounter++;
            if (currentGrade.equals("G"))
                gCounter++;
            if (currentGrade.equals("VG"))
                vgCounter++;
        }

        // ig / 3 = procent ... samma med g vg
        System.out.println("\nJavautvecklare, all courses:\n");
        System.out.println("Percentage of IG: " + (igCounter / listOfGrades.size()) * 100 + "%");
        System.out.println("Percentage of G: " + (gCounter / listOfGrades.size()) * 100 + "%");
        System.out.println("Percentage of VG: " + (vgCounter / listOfGrades.size()) * 100 + "%");
    }

    /**
     * gets all grades for a specified course then prints average values for each grade
     */
    public static void statisticsForSpecificCourse() {

        // fråga vilken kurs
        String courseName = "";
        boolean running = true;
        List<String> listOfCourses = getListOfCourses();
        System.out.println("Enter course: ");

        while(running){
            courseName = fetch.nextLine();
            // om coursename finns i lista med databaser
            if (listOfCourses.contains(courseName)){
                running = false;
            }else {
                System.out.println("Invalid entry, enter again: ");
            }
        }

        // ta ut betyg för enskild kurs
        List<String> listOfGrades = getGradesForCourse(courseName);

        // räkna ut hur många som har ig g vg
        double igCounter = 0.0;
        double gCounter = 0.0;
        double vgCounter = 0.0;
        for (String currentGrade : listOfGrades) {
            if (currentGrade.equals("IG"))
                igCounter++;
            if (currentGrade.equals("G"))
                gCounter++;
            if (currentGrade.equals("VG"))
                vgCounter++;
        }

        // ig / 3 = procent ... samma med g vg
        System.out.println("\n" + courseName + ":\n");
        System.out.println("Percentage of IG: " + (igCounter / listOfGrades.size()) * 100 + "%");
        System.out.println("Percentage of G: " + (gCounter / listOfGrades.size()) * 100 + "%");
        System.out.println("Percentage of VG: " + (vgCounter / listOfGrades.size()) * 100 + "%");
    }


    /**
     * ???????
     */
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
