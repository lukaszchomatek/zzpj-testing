package pl.lodz.p.zzpj.testing;

public class Dummies {
    public static boolean isValidYearInTwentiethCentury(int year) {
        return year >= 1900 && year <= 1999;
    }

    public static boolean isNotEmptyString(String string) {
        return string != null && !string.isEmpty() && !string.isBlank();
    }

    public static boolean isValidEmailForUserRole(String email, UserRole role) {
        String studentPattern = "^\\d{6}@edu\\.example\\.com$";
        String employeePattern = "^[a-z]+\\.[a-z]+@example\\.com$";
        if (role == UserRole.STUDENT && email.matches(studentPattern)) {
            return true;
        } else if (role != UserRole.STUDENT && email.matches(employeePattern)) {
            return true;
        } else {
            return false;
        }
    }
}
