package learning.task007;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task007TicketOrderTest {

    @Test
    void calculatesTotalCost() {
        TicketOrder order = new TicketOrder(3, 450);

        assertEquals(1_350, order.totalCost());
    }

    @Test
    void acceptsBudgetEqualToTotalCost() {
        TicketOrder order = new TicketOrder(3, 450);

        assertTrue(order.fitsBudget(1_350));
    }

    @Test
    void acceptsBudgetGreaterThanTotalCost() {
        TicketOrder order = new TicketOrder(3, 450);

        assertTrue(order.fitsBudget(1_500));
    }

    @Test
    void rejectsBudgetBelowTotalCost() {
        TicketOrder order = new TicketOrder(3, 450);

        assertFalse(order.fitsBudget(1_000));
    }

    @Test
    void rejectsNegativeBudget() {
        TicketOrder order = new TicketOrder(1, 100);

        assertFalse(order.fitsBudget(-1));
    }

    @Test
    void supportsZeroTickets() {
        TicketOrder order = new TicketOrder(0, 500);

        assertEquals(0, order.totalCost());
        assertTrue(order.fitsBudget(0));
    }

    @Test
    void supportsZeroTicketPrice() {
        TicketOrder order = new TicketOrder(5, 0);

        assertEquals(0, order.totalCost());
        assertTrue(order.fitsBudget(0));
    }

    @Test
    void handlesMaximumAllowedValues() {
        TicketOrder order = new TicketOrder(50, 10_000);

        assertEquals(500_000, order.totalCost());
    }

    @Test
    void eachObjectKeepsItsOwnOrderData() {
        TicketOrder firstOrder = new TicketOrder(3, 450);
        TicketOrder secondOrder = new TicketOrder(2, 900);

        assertEquals(1_350, firstOrder.totalCost());
        assertEquals(1_800, secondOrder.totalCost());
        assertTrue(firstOrder.fitsBudget(1_350));
        assertTrue(secondOrder.fitsBudget(1_800));
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
                    throw new AssertionError("The class must not read from the console");
                }
            });
            System.setOut(output);

            TicketOrder order = new TicketOrder(3, 450);
            assertEquals(1_350, order.totalCost());
            assertTrue(order.fitsBudget(1_500));
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
