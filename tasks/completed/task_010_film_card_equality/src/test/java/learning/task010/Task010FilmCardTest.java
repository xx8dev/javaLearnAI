package learning.task010;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task010FilmCardTest {

    @Test
    void cardsWithTheSameTitleAndYearAreEqual() {
        FilmCard first = new FilmCard("Дюна: Часть вторая", 2024);
        FilmCard second = new FilmCard("Дюна: Часть вторая", 2024);

        assertTrue(first.equals(second));
    }

    @Test
    void overridesEqualsForReferencesDeclaredAsObject() {
        Object first = new FilmCard("Дюна: Часть вторая", 2024);
        Object second = new FilmCard("Дюна: Часть вторая", 2024);

        assertTrue(first.equals(second));
    }

    @Test
    void differentYearsMakeCardsUnequal() {
        FilmCard first = new FilmCard("Дюна", 2024);
        FilmCard second = new FilmCard("Дюна", 1984);

        assertFalse(first.equals(second));
    }

    @Test
    void differentTitlesMakeCardsUnequal() {
        FilmCard first = new FilmCard("Дюна", 2024);
        FilmCard second = new FilmCard("Бегущий по лезвию", 2024);

        assertFalse(first.equals(second));
    }

    @Test
    void titleComparisonIsCaseSensitive() {
        FilmCard first = new FilmCard("Дюна", 2024);
        FilmCard second = new FilmCard("дюна", 2024);

        assertFalse(first.equals(second));
    }

    @Test
    void equalsIsReflexiveSymmetricAndTransitive() {
        FilmCard first = new FilmCard("Амели", 2001);
        FilmCard second = new FilmCard("Амели", 2001);
        FilmCard third = new FilmCard("Амели", 2001);

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertTrue(second.equals(first));
        assertTrue(second.equals(third));
        assertTrue(first.equals(third));
    }

    @Test
    void nullIsNotEqualToAFilmCard() {
        FilmCard card = new FilmCard("Амели", 2001);

        assertFalse(card.equals(null));
    }

    @Test
    void anObjectOfAnotherClassIsNotEqualToAFilmCard() {
        FilmCard card = new FilmCard("Амели", 2001);

        assertFalse(card.equals("Амели"));
    }

    @Test
    void equalCardsHaveTheSameHashCode() {
        FilmCard first = new FilmCard("Дюна: Часть вторая", 2024);
        FilmCard second = new FilmCard("Дюна: Часть вторая", 2024);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void hashCodeIsStableForAnUnchangedCard() {
        FilmCard card = new FilmCard("Амели", 2001);
        int firstHash = card.hashCode();

        assertEquals(firstHash, card.hashCode());
    }

    @Test
    void supportsTheMinimumAndMaximumReleaseYears() {
        FilmCard oldest = new FilmCard("Короткометражный фильм", 1888);
        FilmCard latest = new FilmCard("Будущий фильм", 2100);

        assertTrue(oldest.equals(new FilmCard("Короткометражный фильм", 1888)));
        assertTrue(latest.equals(new FilmCard("Будущий фильм", 2100)));
    }

    @Test
    void keepsInstanceDataPrivate() {
        boolean hasInstanceState = false;

        for (Field field : FilmCard.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers)) {
                hasInstanceState = true;
                assertTrue(Modifier.isPrivate(modifiers), "FilmCard instance fields must be private");
            }
        }

        assertTrue(hasInstanceState, "Each film card must keep its own state");
    }

    @Test
    void exposesThePublicConstructorAndObjectOverrides() throws NoSuchMethodException {
        assertTrue(Modifier.isPublic(FilmCard.class.getModifiers()));
        assertTrue(Modifier.isPublic(FilmCard.class.getConstructor(String.class, int.class).getModifiers()));

        Method equalsMethod = FilmCard.class.getDeclaredMethod("equals", Object.class);
        Method hashCodeMethod = FilmCard.class.getDeclaredMethod("hashCode");
        assertTrue(Modifier.isPublic(equalsMethod.getModifiers()));
        assertTrue(Modifier.isPublic(hashCodeMethod.getModifiers()));
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
                    throw new AssertionError("FilmCard must not read from the console");
                }
            });
            System.setOut(output);

            FilmCard first = new FilmCard("Амели", 2001);
            FilmCard second = new FilmCard("Амели", 2001);
            assertTrue(first.equals(second));
            assertEquals(first.hashCode(), second.hashCode());
        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
