package tracker.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tracker.ui.InputValidators.isStudentDataInputValid;

public class InputValidatorsWithOutputTest { // these tests are grouped/separated for @BeforeEach and @AfterEach requirements
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
    void isStudentDataInputValidPrintOutTestInvalidFirstName() {
        isStudentDataInputValid("'name surname email@email.xyz");
        assertEquals("Incorrect first name.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void isStudentDataInputValidPrintOutTestInvalidLastName() {
        isStudentDataInputValid("John D. name@domain.com");
        assertEquals("Incorrect last name.", outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void isStudentDataInputValidPrintOutTestInvalidEmail() {
        isStudentDataInputValid("John Doe email");
        assertEquals("Incorrect email.", outputStreamCaptor.toString()
                .trim());
    }
}
