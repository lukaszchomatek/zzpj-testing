package pl.lodz.p.zzpj.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

class DummiesTest {

    @ParameterizedTest
    @ValueSource(ints = {1900, 1950, 1999})
    void isValidYearInTwentiethCentury(int year) {
        assertTrue(Dummies.isValidYearInTwentiethCentury(year));
    }

    @ParameterizedTest
    @ValueSource(ints = {1899, 2000, 2020})
    void isNotValidYearInTwentiethCentury(int year) {
        assertFalse(Dummies.isValidYearInTwentiethCentury(year));
    }
    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abc", "abcd"})
    void isNotEmptyString(String string) {
        assertTrue(Dummies.isNotEmptyString(string));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n", "\r", "\r\n", "\t\n", "\t\r", "\t\r\n"})
    void isEmptyString(String string) {
        assertFalse(Dummies.isNotEmptyString(string));
    }

    @ParameterizedTest
    @CsvSource({
            "123456@edu.example.com, STUDENT",
            "jan.kowalski@example.com, TEACHER",
            "jan.kowalski@example.com, ADMIN"})
    void isValidEmailForUserRole(String email, UserRole role) {
        assertTrue(Dummies.isValidEmailForUserRole(email, role));
    }

    @ParameterizedTest
    @CsvSource({
            "123456@edu.example.com, TEACHER",
            "123456@edu.example.com, ADMIN",
            "jan.kowalski@example.com, STUDENT"})
    void isNotValidEmailForUserRole(String email, UserRole role) {
        assertFalse(Dummies.isValidEmailForUserRole(email, role));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/email.csv", numLinesToSkip = 1)
    void fileBasedNotValidEmailForUserRole(String email, UserRole role) {
        assertFalse(Dummies.isValidEmailForUserRole(email, role));
    }
    @Test
    void isValidEmailForUserRole() {
    }
}