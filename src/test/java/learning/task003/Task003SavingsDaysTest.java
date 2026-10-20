package learning.task003;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task003SavingsDaysTest {

    @Test
    void returnsZeroWhenBalanceIsGreaterThanPrice() {
        assertEquals(0, Solution.daysUntilAffordable(1_200, 900, 0));
    }

    @Test
    void returnsZeroWhenBalanceEqualsPrice() {
        assertEquals(0, Solution.daysUntilAffordable(500, 500, 10));
    }

    @Test
    void returnsZeroForFreeItem() {
        assertEquals(0, Solution.daysUntilAffordable(0, 0, 0));
    }

    @Test
    void countsDaysForExactRewardMultiple() {
        assertEquals(2, Solution.daysUntilAffordable(500, 1_100, 300));
    }

    @Test
    void countsFinalDayWhenRewardExceedsRemainingPrice() {
        assertEquals(1, Solution.daysUntilAffordable(850, 1_000, 300));
    }

    @Test
    void countsOneDayAtTheLowerBoundary() {
        assertEquals(1, Solution.daysUntilAffordable(0, 1, 1));
    }

    @Test
    void returnsMinusOneWhenNoRewardCanReachThePrice() {
        assertEquals(-1, Solution.daysUntilAffordable(0, 1, 0));
    }

    @Test
    void handlesMaximumPriceAndReward() {
        assertEquals(1, Solution.daysUntilAffordable(0, 100_000, 100_000));
    }

    @Test
    void handlesMaximumNumberOfDays() {
        assertEquals(100_000, Solution.daysUntilAffordable(0, 100_000, 1));
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

            assertEquals(2, Solution.daysUntilAffordable(400, 1_000, 300));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
