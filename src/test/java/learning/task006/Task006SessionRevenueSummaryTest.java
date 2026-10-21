package learning.task006;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task006SessionRevenueSummaryTest {

    @Test
    void sumsRevenueAcrossSessions() {
        assertEquals(2_700, Solution.calculateTotalRevenue(new int[]{4, 2, 0}, 450));
    }

    @Test
    void returnsZeroForNoSessions() {
        assertEquals(0, Solution.calculateTotalRevenue(new int[]{}, 900));
    }

    @Test
    void calculatesRevenueForOneSession() {
        assertEquals(1_250, Solution.calculateTotalRevenue(new int[]{5}, 250));
    }

    @Test
    void returnsZeroWhenNoTicketsWereSold() {
        assertEquals(0, Solution.calculateTotalRevenue(new int[]{0, 0, 0}, 500));
    }

    @Test
    void returnsZeroWhenTicketPriceIsZero() {
        assertEquals(0, Solution.calculateTotalRevenue(new int[]{10, 20}, 0));
    }

    @Test
    void handlesMaximumArrayLengthAndValues() {
        int[] ticketsSold = new int[100];
        Arrays.fill(ticketsSold, 1_000);

        assertEquals(1_000_000_000, Solution.calculateTotalRevenue(ticketsSold, 10_000));
    }

    @Test
    void rejectsNullArray() {
        assertEquals(-1, Solution.calculateTotalRevenue(null, 500));
    }

    @Test
    void rejectsArrayLongerThanTheLimit() {
        assertEquals(-1, Solution.calculateTotalRevenue(new int[101], 500));
    }

    @Test
    void rejectsNegativeTicketCount() {
        assertEquals(-1, Solution.calculateTotalRevenue(new int[]{3, -1}, 500));
    }

    @Test
    void rejectsTicketCountAboveTheLimit() {
        assertEquals(-1, Solution.calculateTotalRevenue(new int[]{1_001}, 500));
    }

    @Test
    void rejectsNegativeTicketPrice() {
        assertEquals(-1, Solution.calculateTotalRevenue(new int[]{3}, -1));
    }

    @Test
    void rejectsTicketPriceAboveTheLimit() {
        assertEquals(-1, Solution.calculateTotalRevenue(new int[]{3}, 10_001));
    }

    @Test
    void doesNotModifyTheInputArray() {
        int[] ticketsSold = {4, 2, 0};
        int[] original = ticketsSold.clone();

        Solution.calculateTotalRevenue(ticketsSold, 450);

        assertArrayEquals(original, ticketsSold);
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

            assertEquals(2_700, Solution.calculateTotalRevenue(new int[]{4, 2, 0}, 450));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
