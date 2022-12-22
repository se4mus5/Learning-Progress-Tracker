package tracker.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.ui.InputValidators.*;

class InputValidatorsTest {

    @Test
    void isInputStructureValidTest() {
        assertFalse(isInputStructureValid("~D0MInAt0R~"));
        assertTrue(isInputStructureValid("Kis Kata kiskata@cmail.com"));
    }

    @Test
    void isNameValidTest() {
        assertTrue(isNameValid("Jean-Claude"));
        assertTrue(isNameValid("O'Neill"));
        assertTrue(isNameValid("Pista"));
        assertFalse(isNameValid("'name"));
        assertFalse(isNameValid("name'"));
    }

    @Test
    void isValidFirstNameTest() {
        assertTrue(isValidFirstName("Jean-Claude"));
        assertFalse(isValidFirstName("Stanisław"));
        assertFalse(isValidFirstName("S"));
        assertFalse(isValidFirstName("'name"));
        assertFalse(isValidFirstName("name'"));
        assertFalse(isValidFirstName("na--me'"));
        assertFalse(isValidFirstName("nam-'e'"));
        assertFalse(isValidFirstName("nam'-e'"));
    }

    @Test
    void isValidLastNameTest() {
        assertTrue(isValidLastName("O'Neill"));
        assertTrue(isValidLastName("Jemison Van de Graaff"));
        assertFalse(isValidLastName("Oğuz"));
        assertFalse(isValidLastName("B"));
        assertFalse(isValidLastName("name'"));
    }

    @Test
    void isValidEmailTest() {
        assertTrue(isValidEmail("kiskata@cmail.com"));
        assertTrue(isValidEmail("anny.md@mail.edu"));
        assertFalse(isValidEmail("kiskata#cmailcom"));
        assertFalse(isValidEmail("kiskatacmailcom"));
        assertFalse(isValidEmail("John D. name@domain.com"));
        assertFalse(isValidEmail("John Doe email"));
        assertFalse(isValidEmail("kiskata@c@mail.com"));
        assertFalse(isValidEmail("kiskata@mail..com"));
    }
}