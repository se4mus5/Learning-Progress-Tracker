package tracker.ui;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.ui.InputValidators.*;

class InputValidatorsTest {

    @Test
    void isStudentDataInputStructureValidTestTrue() {
        assertTrue(isStudentDataInputStructureValid("Kis Kata kiskata@cmail.com"));
    }

    @Test
    void isStudentDataInputStructureValidTestFalse() {
        assertFalse(isStudentDataInputStructureValid("~D0MInAt0R~"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jean-Claude", "O'Neill", "Pista"})
    void isNameValidTestValid(String arg) {
        assertTrue(isNameValid(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"'name", "'name"})
    void isNameValidTestInvalid(String arg) {
        assertFalse(isNameValid(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jean-Claude"})
    void isValidFirstNameTestTrue(String arg) {
        assertTrue(isValidFirstName(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Stanisław", "S", "'name", "name'", "na--me'", "nam-'e'", "nam'-e'"})
    void isValidFirstNameTestFalse(String arg) {
        assertFalse(isValidFirstName(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"O'Neill", "Jemison Van de Graaff"})
    void isValidLastNameTestTrue(String arg) {
        assertTrue(isValidLastName(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Oğuz", "B", "name'"})
    void isValidLastNameTestFalse(String arg) {
        assertFalse(isValidLastName(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"kiskata@cmail.com", "anny.md@mail.edu"})
    void isValidEmailTestTrue(String arg) {
        assertTrue(isValidEmail(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"kiskata#cmailcom", "kiskatacmailcom", "John D. name@domain.com", "John Doe email",
            "kiskata@c@mail.com", "kiskata@mail..com"})
    void isValidEmailTestFalse(String arg) {
        assertFalse(isValidEmail(arg));
    }

    @Test
    void isStudentDataInputValidTest() {
        assertFalse(isStudentDataInputValid("'name surname email@email.xyz"));
        assertFalse(isStudentDataInputValid("John D. name@domain.com"));
        assertFalse(isStudentDataInputValid("John Doe email"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111 1 1 1 1", "2 2 2 2 2", "somestring 1 1 1 1"})
    void isPointsInputValidTrue(String arg) {
        assertTrue(isPointsInputValid(arg));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11111 1 -1 1 1", "somestring somestring 1 1 1"})
    void isPointsInputValidFalse(String arg) {
        assertFalse(isPointsInputValid(arg));
    }

    @Test
    void parseStudentDataInputTestWithValid() {
        assertArrayEquals(new String[] {"kiskata", "cica", "kiskata@cmail.com"},
                parseStudentDataInput("kiskata cica kiskata@cmail.com"));
    }

    @Test
    void parseStudentDataInputTestWithInvalidLastName() {
        assertArrayEquals(new String[] {"John", "D.", "name@domain.com"},
                parseStudentDataInput("John D. name@domain.com"));
    }

    @Test
    void parseStudentDataInputTestWithInvalidFirstName() {
        assertArrayEquals(new String[] {"'name", "surname", "email@email.xyz"},
                parseStudentDataInput("'name surname email@email.xyz"));
    }
}