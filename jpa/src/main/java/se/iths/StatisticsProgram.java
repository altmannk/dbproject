package se.iths;

import jakarta.persistence.*;

import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class StatisticsProgram {

    static List<Grade> createListOfGrades(EntityManager em) {
        List<Grade> grades = new ArrayList() {
        };
        String jpql = "SELECT c FROM Course c";
        TypedQuery<Grade> query = em.createQuery(jpql, Grade.class);
        List<Grade> resultList = query.getResultList();

        for (Grade grade : resultList) {
            grades.add(grade);
        }

        return grades;
    }

    static List<Grade> createListOfGradesForSpecificCourse(EntityManager em, String courseName) {
        List<Grade> grades = new ArrayList() {
        };
        TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c JOIN Grade g ON c = g.gradeCourseID WHERE c.courseName = :courseName", Course.class)
                .setParameter("courseName", courseName);

        List<Course> resultList = query.getResultList();

        for (Course curCourse : resultList) {
            System.out.println(curCourse);
        }

        return null;
    }

    public static List<String> getGradesForCourse(String courseName) {
        try {
            return Main.inTransaction(em -> {
                TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
                Course course = query.getSingleResult();
                System.out.println(course.getCourseName());

                TypedQuery<String> query2 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeCourseID = :id", String.class).setParameter("id", course);
                List<String> grades = query2.getResultList();
                //System.out.println(grades);
                for(String grade : grades) {
                    System.out.println(grade);
                }
                return grades;



            });

        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return Collections.emptyList();
        }
    }



    public static List<String> getGradesForCourse2(String courseName) {
        try {
            return Main.inTransaction(em -> {
                TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
                Course course = query.getSingleResult();
                System.out.println(course.getCourseName());

                TypedQuery<Grade> query2 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeCourseID = :id", Grade.class).setParameter("id", course);
                List<Grade> grades = query2.getResultList();
                //System.out.println(grades);
                List<String> listOfGrades = new ArrayList<>();
                for(Grade curGrade : grades) {
                    listOfGrades.add(curGrade.getGradeValue());
                }

                return listOfGrades;
            });
        } catch (NoResultException | NonUniqueResultException e) {
            e.printStackTrace(); // Log the exception for debugging purposes
            return Collections.emptyList();
        }
    }





    /**
     * snittbetyg för alla kurser
     */
    public static void statisticsForSpecificCourse(EntityManager em) {
        //List<String> grades = createListOfGradesForSpecificCourse(em, "javaprogrammering");

        List<String> grades = getGradesForCourse("Javaprogrammering");

        // ta ut betyg för enskild kurs

        // räkna ut hur många som har ig g vg

        // ig / 3 = procent ... samma med g vg

        // printa ut var och en

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
