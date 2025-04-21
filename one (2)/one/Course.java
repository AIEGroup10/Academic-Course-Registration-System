package one;

import java.util.LinkedList;

class Course {
    Integer courseId;
    String courseName;
    int maxSeats;
    LinkedList<Student> students;
    LinkedList<Student> waitingList;

    public Course(Integer courseId, String courseName, int maxSeats) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxSeats = maxSeats;
        this.students = new LinkedList<>();
        this.waitingList = new LinkedList<>();
    }

    public int availableSeats() {
        return maxSeats - students.size();
    }

    public boolean addStudent(Student student) {
        if (students.size() < maxSeats) {
            students.add(student);
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        if (students.remove(student)) {
            return true;
        }
        return false;
    }

    public boolean addToWaitingList(Student student) {
        if (!waitingList.contains(student)) {
            waitingList.add(student);
            return true;
        }
        return false;
    }

    public int getWaitingListPosition(Student student) {
        return waitingList.indexOf(student) + 1;
    }

    public void processWaitingList() {
        if (!waitingList.isEmpty() && availableSeats() > 0) {
            Student nextStudent = waitingList.remove(0);
            if (addStudent(nextStudent)) {
                nextStudent.registeredCourses.add(this);
                System.out.println(nextStudent.name +" has been enrolled from the waiting list.");
            }
        }
    }
}
