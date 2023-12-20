package se.iths;

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
                    System.out.println("\nEnter student name for new student.");
                    String name = sc.nextLine();
                    CrudStudent.createStudent(name);
                    printCrudAction();
                }
                case "2" -> {
                    System.out.println("\nEnter student name to show grades for.");
                    String name = sc.nextLine();
                    CrudStudent.readStudent(name);
                    printCrudAction();
                } //readStudent
                case "3" -> {
                    System.out.println("\nEnter student name to add grade to.");
                    String name = sc.nextLine();
                    System.out.println("\nEnter course name.");
                    String cName = sc.nextLine();
                    System.out.println("\nEnter grade.");
                    String grade = sc.nextLine();
                    CrudGrade.createGrade(name, cName,grade);
                    printCrudAction();
                }//addGrade
                case "4" -> {
                    System.out.println("\nEnter student name to update grade for.");
                    String name = sc.nextLine();
                    System.out.println("\nEnter course name.");
                    String cName = sc.nextLine();
                    System.out.println("\nEnter new grade.");
                    String newGrade = sc.nextLine();
                    CrudGrade.updateGrade(name, cName, newGrade);
                    printCrudAction();
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
