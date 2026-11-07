package learning.task016;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task016GenericArraySearchTest {
    @Test
    void searchIsPublicStaticAndGenericAtMethodLevel() throws NoSuchMethodException {
        Method method = CatalogSearch.class.getMethod("findFirstIndex", Object[].class, Object.class);

        assertEquals(0, CatalogSearch.class.getTypeParameters().length);
        assertEquals(0, CatalogSearch.class.getDeclaredFields().length);
        assertEquals(1, method.getTypeParameters().length);
        assertEquals("T", method.getTypeParameters()[0].getName());
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(int.class, method.getReturnType());
        assertEquals("T[]", method.getGenericParameterTypes()[0].getTypeName());
        assertEquals("T", method.getGenericParameterTypes()[1].getTypeName());
    }

    @Test
    void findsStringAtFirstIndex() {
        assertEquals(0, CatalogSearch.findFirstIndex(new String[]{"Фильм", "Игра"}, "Фильм"));
    }

    @Test
    void findsStringAtLastIndex() {
        assertEquals(2, CatalogSearch.findFirstIndex(new String[]{"Фильм", "Сериал", "Игра"}, "Игра"));
    }

    @Test
    void returnsFirstIndexForRepeatedValue() {
        assertEquals(1, CatalogSearch.findFirstIndex(new Integer[]{10, 20, 20, 30}, 20));
    }

    @Test
    void returnsMinusOneWhenValueIsMissing() {
        assertEquals(-1, CatalogSearch.findFirstIndex(new String[]{"Фильм"}, "Сериал"));
    }

    @Test
    void returnsMinusOneForEmptyArray() {
        assertEquals(-1, CatalogSearch.findFirstIndex(new String[]{}, "Фильм"));
    }

    @Test
    void worksWithAnotherReferenceType() {
        assertEquals(1, CatalogSearch.findFirstIndex(new Integer[]{3, 8, 13}, 8));
    }

    @Test
    void comparesObjectsByEqualsRatherThanIdentity() {
        CatalogItem[] items = {new CatalogItem("film-1"), new CatalogItem("film-2")};
        CatalogItem target = new CatalogItem("film-2");

        assertEquals(1, CatalogSearch.findFirstIndex(items, target));
    }

    @Test
    void findsNullValue() {
        assertEquals(0, CatalogSearch.findFirstIndex(new String[]{null, "Игра", null}, null));
    }

    @Test
    void skipsNullElementsWhenSearchingForNonNullValue() {
        assertEquals(1, CatalogSearch.findFirstIndex(new String[]{null, "Игра", null}, "Игра"));
    }

    @Test
    void returnsMinusOneWhenNullIsNotInArray() {
        assertEquals(-1, CatalogSearch.findFirstIndex(new String[]{"Фильм", "Игра"}, null));
    }

    @Test
    void rejectsNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> CatalogSearch.findFirstIndex((String[]) null, "Фильм"));
    }

    @Test
    void doesNotModifyArray() {
        String[] items = {"Фильм", "Игра"};
        String[] original = items.clone();

        CatalogSearch.findFirstIndex(items, "Игра");

        assertArrayEquals(original, items);
    }

    @Test
    void searchDoesNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            CatalogSearch.findFirstIndex(new String[]{"Фильм"}, "Фильм");
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }

    private record CatalogItem(String code) {
    }
}
