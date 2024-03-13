package pl.lodz.p.zzpj.testing;

import java.util.List;

public class UserService {

    private  UserDatabase userDatabase;

    public UserService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean addUser(String name, String surname, String email, String password, UserRole role) {
        if (!Dummies.isNotEmptyString(name)) {
            throw new IllegalArgumentException("Invalid name");
        }
        if (!Dummies.isNotEmptyString(surname)) {
            throw new IllegalArgumentException("Invalid surname");
        }
        if (!Dummies.isValidEmailForUserRole(email, role)) {
            throw new IllegalArgumentException("Invalid email for user role");
        }
        if (!Dummies.isNotEmptyString(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        User user = new User(name, surname, email, password, role);
        return userDatabase.addUser(user);
    }

    public boolean removeUser(String email) {
        return userDatabase.removeUser(email);
    }

    public List<User> getUsersInRole(UserRole role) {
        return userDatabase.getUsersInRole(role);

    }

    public boolean updateUser(User user) {
        return userDatabase.updateUser(user);
    }

    public User getUser(String email) {
        return userDatabase.getUser(email);
    }

    public void changeRole(User user, UserRole role) {
        String studentPattern = "^\\d{6}@edu\\.example\\.com$";
        if (role != UserRole.STUDENT && user.getEmail().matches(studentPattern)) {
            throw new IllegalArgumentException("Invalid role for student email");
        }
        user.setRole(role);
    }

    public void changeEmail(User user, String email) {
        String studentPattern = "^\\d{6}@edu\\.example\\.com$";
        String employeePattern = "^[a-z]+\\.[a-z]+@example\\.com$";
        if (user.getRole() == UserRole.STUDENT && email.matches(studentPattern)) {
            user.setEmail(email);
        } else if (user.getRole() != UserRole.STUDENT && email.matches(employeePattern)) {
            user.setEmail(email);
        } else if (user.getRole() == UserRole.STUDENT && email.matches(employeePattern)) {
            user.setEmail(email);
            user.setRole(UserRole.TEACHER);
        } else {
            throw new IllegalArgumentException("Invalid email for user role");
        }

    }
}
