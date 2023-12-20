package se.iths;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CrudStudent {

    public static void crudTest() {
        System.out.println("test");
    }


    public static void createStudent(String name) {

        Main.inTransaction(em -> {
            Student student = new Student();
            student.setStudentName(name);
            em.persist(student);
        });

    }

    public static void updateStudent(String name, String newName) {
        Main.inTransaction(em -> {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :name", Student.class).setParameter("name", name);
            Student student = query.getSingleResult();
            student.setStudentName(newName);
        });
    }


    public static void deleteStudent(String name) {
        Main.inTransaction(em -> {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :name", Student.class).setParameter("name", name);
            List<Student> students = query.getResultList();
            for (Student s : students) {

                TypedQuery<Grade> query2 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeStudentID = :id", Grade.class).setParameter("id", s);
                List<Grade> grades = query2.getResultList();
                for (Grade g : grades) {
                    em.remove(g);
                }
                em.remove(s);
            }

        });
    }

    public static void readStudent(String name) {
        Main.inTransaction(em -> {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :name", Student.class).setParameter("name", name);
            Student student = query.getSingleResult();
            System.out.println(student.getStudentName());

            TypedQuery<Grade> query2 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeStudentID = :id", Grade.class).setParameter("id", student);
            List<Grade> grades = query2.getResultList();
            for (Grade g : grades) {
                System.out.println(g.getGradeCourseID().getCourseName() + ": " + g.getGradeValue());
            }
        });
    }
}
