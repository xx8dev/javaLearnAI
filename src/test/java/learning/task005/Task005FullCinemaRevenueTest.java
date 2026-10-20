package learning.task005;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task005FullCinemaRevenueTest {

    @Test
    void calculatesRevenueForAllSeats() {
        assertEquals(5_400, Solution.calculateFullSeatRevenue(3, 4, 450));
    }

    @Test
    void returnsZeroWhenThereAreNoRows() {
        assertEquals(0, Solution.calculateFullSeatRevenue(0, 5, 700));
    }

    @Test
    void returnsZeroWhenThereAreNoSeatsPerRow() {
        assertEquals(0, Solution.calculateFullSeatRevenue(4, 0, 700));
    }

    @Test
    void returnsZeroWhenTicketPriceIsZero() {
        assertEquals(0, Solution.calculateFullSeatRevenue(4, 8, 0));
    }

    @Test
    void handlesMaximumAllowedValues() {
        assertEquals(10_000_000, Solution.calculateFullSeatRevenue(100, 100, 1_000));
    }

    @Test
    void rejectsNegativeRowCount() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(-1, 4, 450));
    }

    @Test
    void rejectsNegativeSeatsPerRow() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(3, -1, 450));
    }

    @Test
    void rejectsNegativeTicketPrice() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(3, 4, -1));
    }

    @Test
    void rejectsRowCountAboveTheLimit() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(101, 4, 450));
    }

    @Test
    void rejectsSeatsPerRowAboveTheLimit() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(3, 101, 450));
    }

    @Test
    void rejectsTicketPriceAboveTheLimit() {
        assertEquals(-1, Solution.calculateFullSeatRevenue(3, 4, 1_001));
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

            assertEquals(5_400, Solution.calculateFullSeatRevenue(3, 4, 450));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
