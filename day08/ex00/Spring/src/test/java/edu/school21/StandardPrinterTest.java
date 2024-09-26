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

public class StandardPrinterTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private final String TEST_WORD = "Hello!";

    private final String[] BEAN_NAMES = {"PrinterWithPrefixStandardToUpper",
                                         "PrinterWithPrefixStandardToLower",
                                         "PrinterWithDateTimeStandardToUpper",
                                         "PrinterWithDateTimeStandardToLower"};

    private final String[] RESULT_LINES = {
        "HEY, HONOURABLE, HELLO!", "hey, honourable, hello!",
        "HELLO!"
            + " " + getDateTime().toUpperCase(),
        "hello!"
            + " " + getDateTime().toLowerCase()};

    @BeforeEach
    public void init() {
        System.setOut(new PrintStream(outStream));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void standardPrinterTest(int index) {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("test_context.xml");
        Printer printer = context.getBean(BEAN_NAMES[index], Printer.class);
        printer.print(TEST_WORD);
        assertEquals(RESULT_LINES[index], outStream.toString().trim());
    }

    @AfterEach
    public void clear() {
        System.setOut(standardOut);
    }
}
