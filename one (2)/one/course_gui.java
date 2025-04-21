package one;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class course_gui{
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private List<User> users = new ArrayList<>();
    private List<Admin> adminUsers = new ArrayList<>();
    private List<Student> studentUsers = new ArrayList<>();
    private Admin defaultAdmin;
    private Student currentStudent;
    
    private Color primaryColor = new Color(30, 144, 255);
    private Color secondaryColor = new Color(255, 255, 255);
    private Color accentColor = new Color(255, 165, 0);

    public course_gui() {
        initializeDefaultData();
        initializeGUI();
    }

    private void initializeDefaultData() {
        defaultAdmin = new Admin("System Admin", "admin001", "1234567890", "Admin@123");
        adminUsers.add(defaultAdmin);
        users.add(defaultAdmin);
        defaultAdmin.addCourse(new Course(101, "Introduction to Programming", 10));
        defaultAdmin.addCourse(new Course(102, "Calculus", 10));
        defaultAdmin.addCourse(new Course(103, "Basic Linear Algebra", 10));
        defaultAdmin.addCourse(new Course(201, "Computational physics", 10));
        defaultAdmin.addCourse(new Course(202, "Quantum physics", 10));
        defaultAdmin.addCourse(new Course(301, "Introduction to C - Language", 10));
        defaultAdmin.addCourse(new Course(302, "Data structures and algorithms", 10));
        defaultAdmin.addCourse(new Course(401, "Foundations of Indian heritage", 10));
        defaultAdmin.addCourse(new Course(402, "Glimpse of Glorius India", 10));
        defaultAdmin.addCourse(new Course(303, "System designing", 10));
        
        Student student1 = new Student("john","8899","9988001122","John@123");
        users.add(student1);
        studentUsers.add(student1);
    }

    private void initializeGUI() {
        frame = new JFrame("Course Registration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(secondaryColor);

        mainPanel.add(createWelcomePanel(), "welcome");
        mainPanel.add(createRegistrationPanel(), "register");
        mainPanel.add(createLoginPanel(), "login");
        mainPanel.add(createAdminPanel(), "admin");
        mainPanel.add(createStudentPanel(), "student");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(primaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel titleLabel = new JLabel("Course Registration System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(secondaryColor);
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        buttonPanel.setOpaque(false);

        JButton registerButton = createStyledButton("Register Now", accentColor);
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        JButton loginButton = createStyledButton("Existing User Login", primaryColor);
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        gbc.gridy = 1;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        String[] fields = {"Full Name:", "User ID:", "Phone:", "Password:"};
        JTextField[] inputs = {new JTextField(20), new JTextField(20), 
                          new JTextField(20), new JPasswordField(20)};
        
        for(int i=0; i<fields.length; i++) {
            gbc.gridy = i+1;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(fields[i]);
            label.setForeground(primaryColor);
            panel.add(label, gbc);
            
            gbc.gridx = 1;
            panel.add(inputs[i], gbc);
            gbc.gridx = 0;
        }

        JButton registerButton = createStyledButton("Complete Registration", accentColor);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Student", "Admin"});
        
        gbc.gridy = fields.length+1;
        gbc.gridwidth = 2;
        panel.add(roleCombo, gbc);
        
        gbc.gridy++;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String name = inputs[0].getText();
            String userId = inputs[1].getText();
            String phone = inputs[2].getText();
            String password = inputs[3].getText();
            String role = (String) roleCombo.getSelectedItem();

            if(validateRegistration(name, userId, phone, password)) {
                createUser(role, name, userId, phone, password);
                JOptionPane.showMessageDialog(frame, "Registration Successful!");
                clearFields(inputs);
                cardLayout.show(mainPanel, "login");
            }
        });

        JButton backButton = createStyledButton("Back to Main", primaryColor);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        gbc.gridy++;
        panel.add(backButton, gbc);

        return panel;
    }

    private boolean validateRegistration(String name, String userId, String phone, String password) {
        List<String> errors = new ArrayList<>();

        if (name.isEmpty()) {
            errors.add("• Name cannot be empty");
        } else if (name.length() > 20) {
            errors.add("• Name must be ≤20 characters");
        } else if (!name.matches("[a-zA-Z\\s]+")) {
            errors.add("• Name can only contain letters and spaces");
        }

        if (!phone.matches("\\d{10}")) {
            errors.add("• Phone must be 10 digits");
        } else if (phone.startsWith("0")) {
            errors.add("• Phone cannot start with 0");
        }

        if (password.length() < 8) {
            errors.add("• Password must be ≥8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            errors.add("• Password needs 1 uppercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            errors.add("• Password needs 1 digit");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            errors.add("• Password needs 1 special character");
        }

        if (users.stream().anyMatch(u -> u.userId.equals(userId))) {
            errors.add("• User ID already exists");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "<html>" + String.join("<br>", errors) + "</html>";
            JOptionPane.showMessageDialog(frame, errorMessage, "Registration Errors", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(primaryColor);
        tabbedPane.setForeground(secondaryColor);

        // Add Course Panel
        JPanel addCoursePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField courseIdField = new JTextField(10);
        courseIdField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField)input).getText();
                try {
                    Integer.parseInt(text);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        
        JTextField courseNameField = new JTextField(20);
        JTextField seatsField = new JTextField(5);

        gbc.gridy = 0;
        addCoursePanel.add(createInputLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(courseIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addCoursePanel.add(createInputLabel("Course Name:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(courseNameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        addCoursePanel.add(createInputLabel("Max Seats:"), gbc);
        gbc.gridx = 1;
        addCoursePanel.add(seatsField, gbc);

        JButton addButton = createStyledButton("Add Course", accentColor);
        addButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                int seats = Integer.parseInt(seatsField.getText());
                defaultAdmin.addCourse(new Course(
                    courseId,
                    courseNameField.getText(),
                    seats
                ));
                JOptionPane.showMessageDialog(frame, "Course added successfully!");
                courseIdField.setText("");
                courseNameField.setText("");
                seatsField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid course ID or seat number");
            }
        });

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        addCoursePanel.add(addButton, gbc);

        // Manage Courses Panel
        JPanel managePanel = new JPanel(new BorderLayout());
        JTextArea coursesArea = new JTextArea(15, 40);
        coursesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(coursesArea);
        
        // Remove Course Components
        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JTextField removeField = new JTextField(15);
        removeField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField)input).getText();
                try {
                    Integer.parseInt(text);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        
        JButton removeButton = createStyledButton("Remove Course", accentColor);
        removeButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(removeField.getText().trim());
                defaultAdmin.removeCourse(courseId);
                removeField.setText("");
                refreshCoursesArea(coursesArea);
                JOptionPane.showMessageDialog(frame, 
                    "Course " + courseId + " removed successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid course ID");
            }
        });

        removePanel.add(new JLabel("Course ID to Remove:"));
        removePanel.add(removeField);
        removePanel.add(removeButton);

        JButton refreshButton = createStyledButton("Refresh List", accentColor);
        refreshButton.addActionListener(e -> refreshCoursesArea(coursesArea));

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(removePanel, BorderLayout.NORTH);
        buttonPanel.add(refreshButton, BorderLayout.SOUTH);

        managePanel.add(scrollPane, BorderLayout.CENTER);
        managePanel.add(buttonPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Add Course", addCoursePanel);
        tabbedPane.addTab("Manage Courses", managePanel);
        panel.add(tabbedPane, BorderLayout.CENTER);

        panel.add(createLogoutButton(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(secondaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        gbc.gridwidth = 2;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        JTextField userIdField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createInputLabel("User ID:"), gbc);
        gbc.gridx = 1;
        panel.add(userIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createInputLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = createStyledButton("Login", accentColor);
        loginButton.addActionListener(e -> {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());
            
            User user = users.stream()
                .filter(u -> u.userId.equals(userId) && u.verifyPassword(password))
                .findFirst()
                .orElse(null);

            if(user != null) {
                if(user instanceof Admin) {
                    cardLayout.show(mainPanel, "admin");
                } else {
                    currentStudent = (Student) user;
                    cardLayout.show(mainPanel, "student");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JButton backButton = createStyledButton("Back to Main", primaryColor);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        gbc.gridy++;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(secondaryColor);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(primaryColor);
        tabbedPane.setForeground(secondaryColor);

        // Registration Panel
        JPanel regPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField courseIdField = new JTextField(10);
        courseIdField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JTextField)input).getText();
                try {
                    Integer.parseInt(text);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        
        JTextArea registeredArea = new JTextArea(10, 30);
        registeredArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(registeredArea);

        gbc.gridy = 0;
        regPanel.add(createInputLabel("Course ID:"), gbc);
        gbc.gridx = 1;
        regPanel.add(courseIdField, gbc);
        
        JButton searchButton = createStyledButton("Search Course", accentColor);
        searchButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                Course c = defaultAdmin.search(courseId);
                if(c != null) {
                    String info = String.format("%d: %s\nAvailable Seats: %d/%d",
                        c.courseId, c.courseName, c.availableSeats(), c.maxSeats);
                    JOptionPane.showMessageDialog(frame, info);
                } else {
                    JOptionPane.showMessageDialog(frame, "Course not found");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid course ID");
            }
        });
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        regPanel.add(searchButton, gbc);

        JButton regButton = createStyledButton("Register", accentColor);
        regButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                Course c = defaultAdmin.search(courseId);
                if(c != null && currentStudent.registerCourse(c)) {
                    registeredArea.append(c.courseId + " - " + c.courseName + "\n");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid course ID");
            }
        });
        gbc.gridy++;
        regPanel.add(regButton, gbc);

        JButton dropButton = createStyledButton("Drop Course", primaryColor);
        dropButton.addActionListener(e -> {
            try {
                int courseId = Integer.parseInt(courseIdField.getText());
                Course c = defaultAdmin.search(courseId);
                if(c != null) {
                    currentStudent.dropCourse(c);
                    updateRegisteredCourses(registeredArea);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid course ID");
            }
        });
        gbc.gridy++;
        regPanel.add(dropButton, gbc);

        gbc.gridy++;
        regPanel.add(new JLabel("Your Courses:"), gbc);
        gbc.gridy++;
        regPanel.add(scrollPane, gbc);

        // Course Catalog Panel
        JPanel catalogPanel = new JPanel(new BorderLayout());
        JTextArea catalogArea = new JTextArea(15, 40);
        catalogArea.setEditable(false);
        JScrollPane catalogScroll = new JScrollPane(catalogArea);
        
        JButton viewCatalogButton = createStyledButton("View Catalog", accentColor);
        viewCatalogButton.addActionListener(e -> {
            catalogArea.setText("");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            PrintStream old = System.out;
            System.setOut(ps);
            
            defaultAdmin.viewAllCourses();
            
            System.out.flush();
            System.setOut(old);
            catalogArea.setText(baos.toString());
        });
        
        catalogPanel.add(catalogScroll, BorderLayout.CENTER);
        catalogPanel.add(viewCatalogButton, BorderLayout.SOUTH);

        tabbedPane.addTab("Course Registration", regPanel);
        tabbedPane.addTab("Course Catalog", catalogPanel);
        panel.add(tabbedPane, BorderLayout.CENTER);

        panel.add(createLogoutButton(), BorderLayout.SOUTH);

        return panel;
    }

    private void refreshCoursesArea(JTextArea area) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        
        defaultAdmin.viewAllCourses();
        
        System.out.flush();
        System.setOut(old);
        area.setText(baos.toString());
    }

    private JButton createLogoutButton() {
        JButton button = createStyledButton("Logout", primaryColor);
        button.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));
        return button;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(secondaryColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private JLabel createInputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(primaryColor);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void createUser(String role, String name, String userId, String phone, String password) {
        if(role.equals("Admin")) {
            Admin admin = new Admin(name, userId, phone, password);
            adminUsers.add(admin);
            users.add(admin);
        } else {
            Student student = new Student(name, userId, phone, password);
            studentUsers.add(student);
            users.add(student);
        }
    }

    private void clearFields(JComponent[] fields) {
        for(JComponent field : fields) {
            if(field instanceof JTextField) {
                ((JTextField)field).setText("");
            }
        }
    }

    private void updateRegisteredCourses(JTextArea area) {
        area.setText("");
        currentStudent.registeredCourses.forEach(c -> 
            area.append(c.courseId + " - " + c.courseName + "\n")
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new course_gui());
    }
}