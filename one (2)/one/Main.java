package one;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Admin> adminUsers = new ArrayList<>();
    static List<Student> studentUsers = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Course Registration System!");

        Admin defaultAdmin = new Admin("System Admin", "admin001", "1234567890", "Admin@123");
        Student student1 = new Student("Student1","student1","1212121212","Student@123");
        Course course1 = new Course(101, "Introduction to Programming", 10);
        Course course2 = new Course(102, "Calculus", 10);
        Course course3 = new Course(103,"Basic Linear ALgebra",10);
        Course course4 = new Course(201,"Computational physics",10);
        Course course5 = new Course(202,"Quantum Physics",10);
        Course course6 = new Course(301,"Introduction to C - Language",10);
        Course course7 = new Course(302,"Data structures and algorithms",10);
        Course course8 = new Course(401,"Foundations of Indian heritage",10);
        Course course9 = new Course(402,"Glimpse of Glorius India",10);
        Course course10 = new Course(303,"System Design",10);
        
        defaultAdmin.addCourse(course1);
        defaultAdmin.addCourse(course2);
        defaultAdmin.addCourse(course3);
        defaultAdmin.addCourse(course4);
        defaultAdmin.addCourse(course5);
        defaultAdmin.addCourse(course6);
        defaultAdmin.addCourse(course7);
        defaultAdmin.addCourse(course8);
        defaultAdmin.addCourse(course9);
        defaultAdmin.addCourse(course10);
        
        adminUsers.add(defaultAdmin);
        studentUsers.add(student1);
        users.add(defaultAdmin);
        users.add(student1);

        while (true) {
            System.out.println("\nMain Menu:\n1. Login\n2. Register as Student\n3.Search for a course\n4. Register as Admin\n5. Exit");
            String choice = scanner.nextLine();
			switch (choice) {
                case "1":
                    User user = login();
                    if (user instanceof Admin) adminMenu((Admin) user);
                    else if (user instanceof Student) studentMenu((Student) user, defaultAdmin);
                    break;
                case "2":
                    users.add(registerUser("student"));
                    break;
                case "3":
                    System.out.print("Enter course ID: ");
                    Integer id = scanner.nextInt();
                    Course course_1;
                    course_1 = defaultAdmin.search(id);
                    if (course_1 != null) {
                        System.out.println("\nCourse ID: " + course_1.courseId);
                        System.out.println("Course Name: " + course_1.courseName);
                        System.out.println("Available Seats: " + course_1.availableSeats() + "/" + course_1.maxSeats);

                        if (course_1.waitingList.size()> 0) {
                            System.out.println("Students on waiting list: " + course_1.waitingList.size());
                        }
                    } else {
                        System.out.println("Course not found");
                    }


                	
                case "4":
                    users.add(registerUser("admin"));
                    break;
                case "5":
                    System.out.println("Thank you for using our Course Registration System.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static User login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (Admin admin : adminUsers) {
            if (admin.userId.equals(userId) && admin.verifyPassword(password)) {
                System.out.println("Welcome, Admin " + admin.name + "!");
                return admin;
            }
        }
        for (Student student : studentUsers) {
            if (student.userId.equals(userId) && student.verifyPassword(password)) {
                System.out.println("Welcome, Student " + student.name + "!");
                return student;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }
 
    public static User registerUser(String userType) {
        System.out.println("Registering as " + userType);
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        String userId;
        while (true) {
            System.out.print("Enter user ID: ");
            String tempId = scanner.nextLine();
            boolean exists = users.stream().anyMatch(u -> u.userId.equals(tempId));
            if (!exists){
                userId = tempId;
                break;
            }
            System.out.println("User ID already exists. Try another.");
        }
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userType.equals("admin")) {
            Admin admin = new Admin(name, userId, phone, password);
            adminUsers.add(admin);
            return admin;
        } else {
            Student student = new Student(name, userId, phone, password);
            studentUsers.add(student);
            return student;
        }
    }

    public static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\nAdmin Menu:\n1. Add New Course\n2. Remove Course\n3. View All Courses\n4. Logout");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter course ID: ");
                    Integer id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter course name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter max seats: ");
                    int seats = Integer.parseInt(scanner.nextLine());
                    admin.addCourse(new Course(id, name, seats));
                    break;
                case "2":
                    System.out.print("Enter course ID to remove: ");
                    Integer removeId = scanner.nextInt();
                    admin.removeCourse(removeId);
                    break;
                case "3":
                    admin.viewAllCourses();
                    break;
                case "4":
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void studentMenu(Student student, Admin defaultAdmin) {
        while (true) {
            System.out.println("\nStudent Menu:\n1. Register for a Course\n2. Drop a Course\n3. View Registered Courses\n4. Logout");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Enter course ID: ");
                    Integer regId = scanner.nextInt();
                    Course course = defaultAdmin.search(regId);
                    if (course != null) student.registerCourse(course);
                    else System.out.println("Course not found.");
                    break;
                case "2":
                    System.out.print("Enter course ID to drop: ");
                    Integer dropId = scanner.nextInt();
                    Course dropCourse = defaultAdmin.search(dropId);
                    if (dropCourse != null) student.dropCourse(dropCourse);
                    else System.out.println("Course not found.");
                    break;
                case "3":
                    student.viewRegisteredCourses();
                    break;
                case "4":
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

