package se.iths;

import jakarta.persistence.TypedQuery;
import org.hibernate.annotations.TypeRegistration;

import java.util.ArrayList;
import java.util.List;

public class CrudGrade {

    public static void createGrade(String studentName, String courseName, String grade) {

        try {
            Main.inTransaction(em -> {
                Grade g = new Grade();
                g.setGradeValue(grade);


                TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :studentName", Student.class).setParameter("studentName", studentName);
                Student student = query.getSingleResult();
                g.setGradeStudentID(student);

                TypedQuery<Course> query2 = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
                Course course = query2.getSingleResult();
                g.setGradeCourseID(course);

                TypedQuery<Grade> gradeQuery = em.createQuery("SELECT g FROM Grade g WHERE g.gradeStudentID= :student", Grade.class).setParameter("student", student);
                List<Grade> grades = gradeQuery.getResultList();
                List<String> courseNames = new ArrayList<>();
                for (Grade currGrade : grades) {
                    courseNames.add(currGrade.getGradeCourseID().getCourseName());
                }
                if (!courseNames.contains(courseName)) {
                    em.persist(g);
                } else System.out.println("Grade already exists for this course");

            });
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public static void updateGrade(String studentName, String courseName, String newGrade) {
        Main.inTransaction(em -> {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :studentName", Student.class).setParameter("studentName", studentName);
            Student student = query.getSingleResult();
            Integer studentID = student.getId();

            TypedQuery<Course> query2 = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
            Course course = query2.getSingleResult();
            Integer courseID = course.getId();

            TypedQuery<Grade> query3 = em.createQuery("SELECT g FROM Grade g WHERE g.gradeCourseID = :courseID AND g.gradeStudentID = :studentID", Grade.class);
            query3.setParameter("courseID", course).setParameter("studentID", student);
            Grade grade = query3.getSingleResult();
            grade.setGradeValue(newGrade);
        });
    }


}
