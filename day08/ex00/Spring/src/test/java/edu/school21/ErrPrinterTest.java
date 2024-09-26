package edu.school21;

import static edu.school21.printer.PrinterWithDateTimeImpl.getDateTime;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.school21.printer.Printer;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ErrPrinterTest {

    private final PrintStream errorOut = System.err;
    private final ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private final String TEST_WORD = "Hello!";

    private final String[] BEAN_NAMES = {
        "PrinterWithPrefixErrToUpper", "PrinterWithPrefixErrToLower",
        "PrinterWithDateTimeErrToUpper", "PrinterWithDateTimeErrToLower"};

    private final String[] RESULT_LINES = {
        "HEY, HONOURABLE, HELLO!", "hey, honourable, hello!",
        "HELLO!"
            + " " + getDateTime().toUpperCase(),
        "hello!"
            + " " + getDateTime().toLowerCase()};

    @BeforeEach
    public void init() {
        System.setErr(new PrintStream(errStream));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void standardPrinterTest(int index) {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("test_context.xml");
        Printer printer = context.getBean(BEAN_NAMES[index], Printer.class);
        printer.print(TEST_WORD);
        assertEquals(RESULT_LINES[index], errStream.toString().trim());
    }

    @AfterEach
    public void clear() {
        System.setErr(errorOut);
    }
}
