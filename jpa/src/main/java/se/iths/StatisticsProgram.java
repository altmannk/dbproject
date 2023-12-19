package se.iths;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StatisticsProgram {

    static List<Grade> createListOfGrades(EntityManager em) {
        List<Grade> grades = new ArrayList(){};
        String jpql = "SELECT g FROM Grade g";
        TypedQuery<Grade> query = em.createQuery(jpql, Grade.class);
        List<Grade> resultList = query.getResultList();

        for (Grade grade : resultList) {
            grades.add(grade);
        }

        return grades;
    }

    public static void statisticsForAllCourses(EntityManager em){
        List<Grade> grades = createListOfGrades(em);
        // få in alla kurser
        for (Grade curGrade : grades){
            System.out.println(curGrade);
        }
        // ta ut alla betyg

        // räkna hur många av varje

        // räkna ut prcent för varje betyg IG, G, VG

        // printa ut dom

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
