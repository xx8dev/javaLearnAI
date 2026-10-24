package learning.task009;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task009CinemaTicketTest {

    @Test
    void formatsTicketWithTheRequiredText() {
        CinemaTicket ticket = new CinemaTicket("Интерстеллар", 3, 12, 650);

        assertEquals("Фильм: Интерстеллар | Ряд: 3 | Место: 12 | Цена: 650 руб.", ticket.toString());
    }

    @Test
    void preservesMovieTitleExactly() {
        CinemaTicket ticket = new CinemaTicket("Планета Земля: Возвращение!", 4, 7, 980);

        assertEquals("Фильм: Планета Земля: Возвращение! | Ряд: 4 | Место: 7 | Цена: 980 руб.", ticket.toString());
    }

    @Test
    void supportsMinimumRowSeatAndFreeTicketPrice() {
        CinemaTicket ticket = new CinemaTicket("Амели", 1, 1, 0);

        assertEquals("Фильм: Амели | Ряд: 1 | Место: 1 | Цена: 0 руб.", ticket.toString());
    }

    @Test
    void supportsMaximumRowSeatAndTicketPrice() {
        CinemaTicket ticket = new CinemaTicket("Планета Земля", 30, 50, 100_000);

        assertEquals("Фильм: Планета Земля | Ряд: 30 | Место: 50 | Цена: 100000 руб.", ticket.toString());
    }

    @Test
    void returnsTheSameDescriptionOnRepeatedCalls() {
        CinemaTicket ticket = new CinemaTicket("Амели", 1, 1, 0);
        String firstDescription = ticket.toString();

        assertEquals(firstDescription, ticket.toString());
    }

    @Test
    void keepsDescriptionsOfDifferentTicketsIndependent() {
        CinemaTicket firstTicket = new CinemaTicket("Интерстеллар", 3, 12, 650);
        CinemaTicket secondTicket = new CinemaTicket("Амели", 1, 1, 0);

        assertEquals("Фильм: Интерстеллар | Ряд: 3 | Место: 12 | Цена: 650 руб.", firstTicket.toString());
        assertEquals("Фильм: Амели | Ряд: 1 | Место: 1 | Цена: 0 руб.", secondTicket.toString());
    }

    @Test
    void overridesToStringWhenTicketIsHeldAsObject() {
        Object ticket = new CinemaTicket("Интерстеллар", 3, 12, 650);

        assertEquals("Фильм: Интерстеллар | Ряд: 3 | Место: 12 | Цена: 650 руб.", ticket.toString());
    }

    @Test
    void keepsInstanceDataPrivate() {
        boolean hasInstanceState = false;

        for (Field field : CinemaTicket.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                hasInstanceState = true;
                assertTrue(Modifier.isPrivate(modifiers), "Ticket instance fields must be private");
            }
        }

        assertTrue(hasInstanceState, "Each ticket must keep its own state");
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
                    throw new AssertionError("CinemaTicket must not read from the console");
                }
            });
            System.setOut(output);

            CinemaTicket ticket = new CinemaTicket("Интерстеллар", 3, 12, 650);
            assertEquals("Фильм: Интерстеллар | Ряд: 3 | Место: 12 | Цена: 650 руб.", ticket.toString());
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
