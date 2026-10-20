package learning.task002;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task002PurchaseAffordabilityTest {

    @Test
    void allowsPurchaseWhenBalanceIsGreaterThanPrice() {
        assertTrue(Solution.canMakePurchase(1_200, 800));
    }

    @Test
    void allowsPurchaseWhenBalanceExactlyMatchesPrice() {
        assertTrue(Solution.canMakePurchase(500, 500));
    }

    @Test
    void rejectsPurchaseWhenBalanceIsOneRubleShort() {
        assertFalse(Solution.canMakePurchase(499, 500));
    }

    @Test
    void rejectsPositivePriceForEmptyBalance() {
        assertFalse(Solution.canMakePurchase(0, 1));
    }

    @Test
    void allowsFreePurchaseAtZeroBalance() {
        assertTrue(Solution.canMakePurchase(0, 0));
    }

    @Test
    void allowsFreePurchaseAtMaximumBalance() {
        assertTrue(Solution.canMakePurchase(1_000_000_000, 0));
    }

    @Test
    void allowsPurchaseAtMaximumBalanceAndPrice() {
        assertTrue(Solution.canMakePurchase(1_000_000_000, 1_000_000_000));
    }

    @Test
    void rejectsMaximumPriceForEmptyBalance() {
        assertFalse(Solution.canMakePurchase(0, 1_000_000_000));
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
                    throw new AssertionError("The check must not read from the console");
                }
            });
            System.setOut(output);

            assertTrue(Solution.canMakePurchase(1_200, 800));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
