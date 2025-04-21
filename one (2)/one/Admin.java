package one;

class Admin extends User {
    private static BST courseCatalog = new BST();

    Admin(String name,String userId, String phone, String password) {
        super(name, userId, phone, password);
    }

    public void addCourse(Course course) {
        courseCatalog.insert(course);
        System.out.println("Course '" + course.courseName + "' added successfully.");
    }

    public void removeCourse(Integer courseId) {
        courseCatalog.delete(courseId);
        System.out.println("Course with ID " + courseId + " deleted successfully.");
    }

    public Course search(Integer courseId){
        return courseCatalog.search(courseId);
    }

    public void viewAllCourses() {
        courseCatalog.display();
    }
    
}