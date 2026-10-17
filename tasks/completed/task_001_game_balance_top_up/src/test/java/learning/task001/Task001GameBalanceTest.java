package learning.task001;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task001GameBalanceTest {

    @Test
    void addsTopUpToExistingBalance() {
        assertEquals(1_450, Solution.calculateBalanceAfterTopUp(1_200, 250));
    }

    @Test
    void addsTopUpToEmptyBalance() {
        assertEquals(730, Solution.calculateBalanceAfterTopUp(0, 730));
    }

    @Test
    void zeroTopUpKeepsCurrentBalance() {
        assertEquals(900, Solution.calculateBalanceAfterTopUp(900, 0));
    }

    @Test
    void zeroBalanceAndZeroTopUpReturnZero() {
        assertEquals(0, Solution.calculateBalanceAfterTopUp(0, 0));
    }

    @Test
    void supportsMaximumDocumentedAmounts() {
        assertEquals(2_000_000_000, Solution.calculateBalanceAfterTopUp(1_000_000_000, 1_000_000_000));
    }

    @Test
    void doesNotReadFromOrWriteToTheConsole() {
        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try (PrintStream output = new PrintStream(capturedOutput, true, StandardCharsets.UTF_8)) {
            System.setIn(new InputStream() {
                @Override
                public int read() {
                    throw new AssertionError("The calculation must not read from the console");
                }
            });
            System.setOut(output);

            assertEquals(1_450, Solution.calculateBalanceAfterTopUp(1_200, 250));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
