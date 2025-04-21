package one;

public class BST {
    private class BSTNode {
        Course course;
        BSTNode left, right;

        BSTNode(Course course) {
            this.course = course;
        }
    }

    private BSTNode root;

    // Insert method
    public void insert(Course course) {
        root = insert(root, course);
    }

    private BSTNode insert(BSTNode node, Course course) {
        if (node == null) return new BSTNode(course);

        if (course.courseId < node.course.courseId)
            node.left = insert(node.left, course);
        else if (course.courseId > node.course.courseId)
            node.right = insert(node.right, course);
        else
            System.out.println("Course ID already exists!");

        return node;
    }

    // Search method
    public Course search(int courseId) {
        return search(root, courseId);
    }

    private Course search(BSTNode node, int courseId) {
        if (node == null) return null;

        if (courseId < node.course.courseId)
            return search(node.left, courseId);
        else if (courseId > node.course.courseId)
            return search(node.right, courseId);
        else
            return node.course;
    }

    // Delete method
    public void delete(int courseId) {
        root = delete(root, courseId);
    }

    private BSTNode delete(BSTNode node, int courseId) {
        if (node == null) return null;

        if (courseId < node.course.courseId)
            node.left = delete(node.left, courseId);
        else if (courseId > node.course.courseId)
            node.right = delete(node.right, courseId);
        else {
            // Node to delete found
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Replace with smallest in right subtree
            BSTNode temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }

        return node;
    }

    private BSTNode min(BSTNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private BSTNode deleteMin(BSTNode node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    // Display method (in-order traversal)
    public void display() {
        display(root);
    }

    private void display(BSTNode node) {
        if (node == null) return;
        display(node.left);
        System.out.println("- " + node.course.courseId + ": " + node.course.courseName +
                " (" + node.course.availableSeats() + "/" + node.course.maxSeats + ")");
        display(node.right);
    }
}
