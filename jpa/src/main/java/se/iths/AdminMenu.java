package se.iths;

import java.util.Scanner;

public class AdminMenu {

    private static Scanner sc = new Scanner(System.in);

    public static void crudMenu(){
        boolean running = true;
        printCrudAction();

        while (running){
            System.out.print("\nChoose ('4' to show menu again): ");
            String choice = sc.nextLine();

            switch (choice){
                case "0" -> {
                    System.out.println("\nExited");
                    running = false;
                    Main.printActionMainMenu();
                }
                case "1" -> {
                    System.out.println("\nEnter student name.");
                    String name = sc.nextLine();
                    CrudStudent.createStudent(name);
                    printCrudAction();
                }
                case "2" -> CrudStudent.crudTest();
                case "3" -> CrudStudent.crudTest();
                //case "4" -> printAction();
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
