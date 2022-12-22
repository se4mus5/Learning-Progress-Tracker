package tracker.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static tracker.ui.TextUserInterface.isInputValid;
import static tracker.ui.TextUserInterface.parseInput;

class TextUserInterfaceTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor)); // needed for printout test
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void parseInputTestWithValid() {
        assertArrayEquals(new String[] {"kiskata", "cica", "kiskata@cmail.com"},
                parseInput("kiskata cica kiskata@cmail.com"));
    }

    @Test
    void parseInputTestWithInvalidLastName() {
        assertArrayEquals(new String[] {"John", "D.", "name@domain.com"},
                parseInput("John D. name@domain.com"));
    }

    @Test
    void parseInputTestWithInvalidFirstName() {
        assertArrayEquals(new String[] {"'name", "surname", "email@email.xyz"},
                parseInput("'name surname email@email.xyz"));
    }

    @Test
    void isInputValidReturnValueTest() {
        assertFalse(isInputValid("'name surname email@email.xyz"));
        assertFalse(isInputValid("John D. name@domain.com"));
        assertFalse(isInputValid("John Doe email"));
    }

    @Test
    void isInputValidPrintOutTestInvalidFirstName() {
        isInputValid("'name surname email@email.xyz");
        assertEquals("Incorrect first name.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void isInputValidPrintOutTestInvalidLastName() {
        isInputValid("John D. name@domain.com");
        assertEquals("Incorrect last name.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void isInputValidPrintOutTestInvalidEmail() {
        isInputValid("John Doe email");
        assertEquals("Incorrect email.", outputStreamCaptor.toString()
                .trim());
    }
}