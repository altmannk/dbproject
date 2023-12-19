package se.iths;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class CrudStudent {

    public static void crudTest(){
        System.out.println("test");
    }

   /* public static void createStudent(String name, String cName){
        Main.inTransaction(em ->{
            Student student = new Student();
            student.setStudentName(name);
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName = :cName", Course.class).setParameter("cName", cName);
            Course course = query.getSingleResult();
            student.setStudentCourseID(course);
            em.persist(student);
        });

    }*/

    public static void updateStudent(String name, String courseName, String newGrade){
        Main.inTransaction(em ->{
            Student student = new Student();
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName = :name", Student.class);
        });
    }

    public static void deleteStudent(){

    }

    public static void readStudent(Object studentID){
        EntityManager em = JPAUtil.getEntityManager();
        Student student = em.find(Student.class, studentID);

    }

}
