package learning.task004;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task004CinemaTicketRevenueTest {

    @Test
    void sumsRevenueAcrossSessions() {
        assertEquals(30_500, Solution.totalTicketRevenue(new int[]{12_000, 0, 18_500}));
    }

    @Test
    void returnsZeroForNoSessions() {
        assertEquals(0, Solution.totalTicketRevenue(new int[]{}));
    }

    @Test
    void returnsRevenueForOneSession() {
        assertEquals(750, Solution.totalTicketRevenue(new int[]{750}));
    }

    @Test
    void returnsZeroWhenAllSessionsHaveZeroRevenue() {
        assertEquals(0, Solution.totalTicketRevenue(new int[]{0, 0, 0}));
    }

    @Test
    void rejectsNegativeRevenue() {
        assertEquals(-1, Solution.totalTicketRevenue(new int[]{2_000, -1, 3_000}));
    }

    @Test
    void handlesMaximumArrayLengthAndValues() {
        int[] sessionRevenue = new int[1_000];
        Arrays.fill(sessionRevenue, 1_000_000);

        assertEquals(1_000_000_000, Solution.totalTicketRevenue(sessionRevenue));
    }

    @Test
    void doesNotModifyTheInputArray() {
        int[] sessionRevenue = {300, 400, 0};
        int[] original = sessionRevenue.clone();

        Solution.totalTicketRevenue(sessionRevenue);

        assertArrayEquals(original, sessionRevenue);
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

            assertEquals(19, Solution.totalTicketRevenue(new int[]{7, 0, 12}));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
