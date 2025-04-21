package one;

import java.util.regex.Pattern;

class User {
    String name;
    String userId;
    String phone;
    String password;
    
    public User(String name, String userId, String phone,String password) {
        this.name = name;
        this.userId = userId;
        this.phone = phone;
        this.password = password;
    }
       
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }


    public static boolean validateName(String name) {
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        if (name.length() > 20) {
            System.out.println("Name must be 20 characters or less.");
            return false;
        }
        if (!name.matches("[a-zA-Z\\s]+")) {
            System.out.println("Name can only contain letters and spaces.");
            return false;
        }
        return true;
    }

    public static boolean validatePhone(String phone) {
        if (!phone.matches("\\d+")) {
            System.out.println("Phone number must contain only digits.");
            return false;
        }
        if (phone.length() != 10) {
            System.out.println("Phone number must be exactly 10 digits.");
            return false;
        }
        if (phone.startsWith("0")) {
            System.out.println("Phone number cannot start with zero. Please enter a valid phone number.");
            return false;
        }
        return true;
    }
    
    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        }
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            System.out.println("Password must contain at least one digit.");
            return false;
        }
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            System.out.println("Password must contain at least one special character.");
            return false;
        }
        return true;
    }
}

