package one;

import java.util.List;

import java.util.ArrayList;

class Student extends User {
    List<Course> registeredCourses;

    public Student(String name, String userId, String phone, String password) {
        super(name, userId,phone, password);
        this.registeredCourses = new ArrayList<>();
    }

    public boolean registerCourse(Course course) {
        if (registeredCourses.size() < 6 && !registeredCourses.contains(course)) {
            if (course.availableSeats() > 0) {
                registeredCourses.add(course);
                course.addStudent(this);
                System.out.println("Successfully registered for " + course.courseName);
                return true;
            } else {
                System.out.println("Course is full.");
                if (course.addToWaitingList(this)) {
                    System.out.println("Added to waiting list. Position: " + course.getWaitingListPosition(this));
                }
                return false;
            }
        } else {
            System.out.println("Maximum course limit reached (6 courses) or already registered.");
            return false;
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent(this);
            System.out.println("Successfully dropped " + course.courseName);
            course.processWaitingList();
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void viewRegisteredCourses() {
        System.out.println("\nYour Registered Courses:");
        for (Course course : registeredCourses) {
            System.out.println("Course ID: " + course.courseId + ", Name: " + course.courseName);
        }
    }

	
}
