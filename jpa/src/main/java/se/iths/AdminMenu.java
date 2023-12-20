package se.iths;

import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void crudMenu(){
        boolean running = true;
        printCrudAction();

        while (running){
            String choice = sc.nextLine();

            switch (choice){
                case "0" -> {
                    System.out.println("\nExited");
                    running = false;
                    Main.printActionMainMenu();
                }
                case "1" -> {
                    addStudent();
                }
                case "2" -> {
                    System.out.println("\nEnter student name to show grades for.");
                    String name = sc.nextLine();
                    CrudStudent.readStudent(name);
                    printCrudAction();
                } //readStudent
                case "3" -> {
                    addGrade();
                }//addGrade
                case "4" -> {
                    addGrade();//ska bytas
                }//changeGrade
                case "5" -> {
                    System.out.println("\nEnter student name to change.");
                    String name = sc.nextLine();
                    System.out.println("\nEnter new name.");
                    String newName = sc.nextLine();
                    CrudStudent.updateStudent(name, newName);
                    printCrudAction();
                }//changeStudentName
                case "6" -> {
                    System.out.println("\nEnter student name to delete.");
                    String name = sc.nextLine();
                    CrudStudent.deleteStudent(name);
                    printCrudAction();
                }//deleteStudent
            };



        }

    }

    private static void addGrade(){
        Main.inTransaction(em ->{
            boolean done = false;
            List<Student> students;
            String name ="";
            while(!done){
                System.out.println("\nEnter student name to add grade for.");
                name = sc.nextLine();
                TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.studentName =: name", Student.class).setParameter("name",name);
                students  = query.getResultList();
                if (!students.isEmpty())
                    done=true;
            }

            done = false;
            List<Course> courses;
            String course="";

            while(!done){
                System.out.println("\nEnter course name to add grade for:");
                course = sc.nextLine();
                TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.courseName =: course", Course.class).setParameter("course", course);
                 courses = query.getResultList();
                if (!courses.isEmpty())
                    done=true;
            }

            done = false;
            String grade="";
            while (!done){
                System.out.println("\nEnter grade value:");
                grade = sc.nextLine();
                if (grade.equals("G")||grade.equals("VG")||grade.equals("IG"))
                    done=true;
            }
            CrudGrade.createGrade(name,course,grade);
        });
printCrudAction();
    }

    private static void addStudent(){

        System.out.println("\nEnter student name for new student.");
        String name = sc.nextLine();

        //gör en version med loop och felchecking

        CrudStudent.createStudent(name);
        System.out.println("\nEnter course name.");
        String cName = sc.nextLine();
        System.out.println("\nEnter grade.");
        String grade = sc.nextLine();
        CrudGrade.createGrade(name, cName,grade);


        printCrudAction();
    }




    private static void printCrudAction() {
        System.out.println("\nAdministration\n");
        System.out.println("""
                0. Back
                1. Add Student
                2. Check Student grades
                3. Add Student grade
                4. Change Student grade
                5. Change Student name
                6. Delete Student
                """);
    }

}
