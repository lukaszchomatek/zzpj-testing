package pl.lodz.p.zzpj.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    User studentUser;

    User teacherUser;

    @BeforeEach
    void setUp() {
        studentUser = new User("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT);
        teacherUser = new User("Zenon", "Nowak", "zenon.nowak@example.com", "password", UserRole.TEACHER);
    }

    @ParameterizedTest()
    @ValueSource(strings = {"12345@edu.example.com","1234567@edu.example.com", "123456@edu.xample.com"})
    void changeEmailStudent(String email) {
        UserService userService = new UserService(null);
        assertThrows(IllegalArgumentException.class, () -> userService.changeEmail(studentUser, email));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"zenon.nowak@xample.com", "123456@edu.example.com", "123456@example.com"})
    void changeEmailTeacher(String email) {
        UserService userService = new UserService(null);
        assertThrows(IllegalArgumentException.class, () -> userService.changeEmail(teacherUser, email));
    }

    @ParameterizedTest()
    @EnumSource(value = UserRole.class, names = {"TEACHER", "ADMIN"})
    void changeRole(UserRole role) {
        UserService userService = new UserService(null);
        assertThrows(IllegalArgumentException.class, () -> userService.changeRole(studentUser, role));
    }

    @ParameterizedTest()
    @EnumSource(value = UserRole.class, names = {"STUDENT"}, mode = EnumSource.Mode.EXCLUDE)
    void changeRoleTeacher(UserRole role) {
        UserService userService = new UserService(null);
        userService.changeRole(teacherUser, role);
        assertEquals(role, teacherUser.getRole());
    }
    @ParameterizedTest()
    @MethodSource("provideUsersAndEmails")
    void changeEmail(User user, String email) {
        UserService userService = new UserService(null);
        assertThrows(IllegalArgumentException.class, () -> userService.changeEmail(user, email));
    }

    private static Object[] provideUsersAndEmails() {
        return new Object[]{
                new Object[]{new User("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT),
                                "12345@edu.example.com"},
                new Object[]{new User("Zenon", "Nowak", "zenon.nowak@example.com", "password", UserRole.TEACHER)
                                , "12345@example.com"}
        };
    }

    @Test
    void addUser() {
        List<User> users = new ArrayList<>();
        UserDatabase database = mock(UserDatabase.class);
        when(database.addUser(ArgumentMatchers.any(User.class))).thenAnswer(invocation -> {
            users.add(invocation.getArgument(0));
            return true;
        });

        UserService userService = new UserService(database);
        userService.addUser("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT);
        assertEquals(1, users.size());
        assertEquals("Jan", users.get(0).getName());
        verify(database, times(1)).addUser(ArgumentMatchers.any(User.class));
    }

    @Test
    void removeUser() {
        List<User> users = new ArrayList<>(
                List.of(new User("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT))
        );
        UserDatabase database = mock(UserDatabase.class);
        when(database.removeUser(ArgumentMatchers.anyString())).thenAnswer(invocation -> {
            users.removeIf(user -> user.getEmail().equals(invocation.getArgument(0)));
            return true;
        });

        UserService userService = new UserService(database);
        UserService serviceSpy = spy(userService);
        serviceSpy.removeUser("123456@edu.example.com");
        verify(database, times(1)).removeUser(ArgumentMatchers.anyString());
        assertEquals(0, users.size());
        verify(serviceSpy, times(1)).removeUser(ArgumentMatchers.anyString());
    }


    @Test
    void getUsersInRole() {
        UserDatabase database = mock(UserDatabase.class);
        when(database.getUsersInRole(UserRole.STUDENT)).thenReturn(List.of(
                new User("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT)));

        UserService userService = new UserService(database);
        List<User> users = userService.getUsersInRole(UserRole.STUDENT);
        assertEquals(1, users.size());
        assertEquals("Jan", users.get(0).getName());
        verify(database, times(1)).getUsersInRole(UserRole.STUDENT);
    }

    @Test
    void getUser() {
        UserDatabase database = mock(UserDatabase.class);

        when(database.getUser("123456@edu.example.com"))
                .thenReturn(new User("Jan", "Kowalski", "123456@edu.example.com", "password", UserRole.STUDENT));

        UserService userService = new UserService(database);
        User user = userService.getUser("123456@edu.example.com");
        assertEquals("Jan", user.getName());
        assertEquals("Kowalski", user.getSurname());
        verify(database, times(1)).getUser(ArgumentMatchers.anyString());
    }
}