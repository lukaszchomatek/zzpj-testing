package pl.lodz.p.zzpj;

import pl.lodz.p.zzpj.testing.User;
import pl.lodz.p.zzpj.testing.UserRole;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Class userClass = User.class;
        for (var method : userClass.getDeclaredMethods()) {
            System.out.println(method.getModifiers() + " " + method.getName() + " " + method.getReturnType() + " " + method.getParameterCount());

        }

        try {
            User user = new User("John", "Doe",
                    "123456@edu.example.com", "STUDENT", UserRole.STUDENT);
            Class clazz = user.getClass();
            Method setPassword = clazz.getDeclaredMethod("setPassword", String.class);
            setPassword.setAccessible(true);
            System.out.println(user.getPassword());
            setPassword.invoke(user, "1234");
            System.out.println(user.getPassword());

        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            User user = new User("John", "Doe",
                    "123456@edu.example.com", "STUDENT", UserRole.STUDENT);
            Class clazz = user.getClass();
            Field email = clazz.getDeclaredField("email");
            email.setAccessible(true);
            email.set(user, "234567@edu.example.com");
            System.out.println(user.getEmail());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}