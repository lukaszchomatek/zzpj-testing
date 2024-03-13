package pl.lodz.p.zzpj.testing;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    List<User> users = new ArrayList<>();

    public boolean addUser(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public User getUser(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean removeUser(String email) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean updateUser(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<User> getUsersInRole(UserRole role) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
