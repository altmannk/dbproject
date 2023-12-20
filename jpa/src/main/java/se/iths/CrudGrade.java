package se.iths;

import jakarta.persistence.TypedQuery;

public class CrudGrade {

    public static void createGrade(String studentName, String courseName, String grade){
        Grade g = new Grade();
        g.setGradeValue(grade);
        Main.inTransaction(em ->{
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :studentName", Student.class).setParameter("studentName", studentName);
            Student student = query.getSingleResult();
            g.setGradeStudentID(student);

            TypedQuery<Course> query2 = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :courseName", Course.class).setParameter("courseName", courseName);
            Course course = query2.getSingleResult();
            g.setGradeCourseID(course);

            em.persist(g);
        });
    }

    public static void updateGrade(String studentName, String courseName, String newGrade){
        Main.inTransaction(em ->{
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
