package learning.task011;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task011CinemaScreeningPriceTest {

    @Test
    void standardFormatHasNoSurcharge() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.STANDARD, 700);

        assertEquals(700, price.getTotalPriceRubles());
    }

    @Test
    void threeDFormatAdds120Rubles() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.THREE_D, 700);

        assertEquals(820, price.getTotalPriceRubles());
    }

    @Test
    void imaxFormatAdds250Rubles() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.IMAX, 700);

        assertEquals(950, price.getTotalPriceRubles());
    }

    @Test
    void standardFormatAllowsZeroBasePrice() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.STANDARD, 0);

        assertEquals(0, price.getTotalPriceRubles());
    }

    @Test
    void imaxFormatAddsSurchargeToZeroBasePrice() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.IMAX, 0);

        assertEquals(250, price.getTotalPriceRubles());
    }

    @Test
    void supportsMaximumBasePrice() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.IMAX, 10_000);

        assertEquals(10_250, price.getTotalPriceRubles());
    }

    @Test
    void enumContainsExactlyTheSpecifiedFormats() {
        assertArrayEquals(
                new ProjectionFormat[]{ProjectionFormat.STANDARD, ProjectionFormat.THREE_D, ProjectionFormat.IMAX},
                ProjectionFormat.values());
    }

    @Test
    void repeatedCallsReturnTheSameTotal() {
        CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.THREE_D, 700);

        assertEquals(820, price.getTotalPriceRubles());
        assertEquals(820, price.getTotalPriceRubles());
    }

    @Test
    void differentObjectsKeepTheirOwnFormatAndPrice() {
        CinemaScreeningPrice standard = new CinemaScreeningPrice(ProjectionFormat.STANDARD, 700);
        CinemaScreeningPrice imax = new CinemaScreeningPrice(ProjectionFormat.IMAX, 1_000);

        assertEquals(700, standard.getTotalPriceRubles());
        assertEquals(1_250, imax.getTotalPriceRubles());
    }

    @Test
    void keepsInstanceFieldsPrivate() {
        boolean hasInstanceState = false;

        for (Field field : CinemaScreeningPrice.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                hasInstanceState = true;
                assertTrue(Modifier.isPrivate(modifiers), "Screening price instance fields must be private");
            }
        }

        assertTrue(hasInstanceState, "Each screening price must keep its own state");
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
                    throw new AssertionError("Screening price must not read from the console");
                }
            });
            System.setOut(output);

            CinemaScreeningPrice price = new CinemaScreeningPrice(ProjectionFormat.THREE_D, 700);
            assertEquals(820, price.getTotalPriceRubles());
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }

    @Test
    void exposesPublicEnumClassConstructorAndMethod() throws NoSuchMethodException {
        assertTrue(Modifier.isPublic(ProjectionFormat.class.getModifiers()));
        assertTrue(ProjectionFormat.class.isEnum());
        assertTrue(Modifier.isPublic(CinemaScreeningPrice.class.getModifiers()));
        assertTrue(Modifier.isPublic(CinemaScreeningPrice.class
                .getConstructor(ProjectionFormat.class, int.class).getModifiers()));
        assertTrue(Modifier.isPublic(CinemaScreeningPrice.class
                .getMethod("getTotalPriceRubles").getModifiers()));
    }
}
