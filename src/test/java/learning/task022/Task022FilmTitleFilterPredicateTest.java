package learning.task022;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task022FilmTitleFilterPredicateTest {
    @Test
    void hasRequiredPublicStaticMethod() throws NoSuchMethodException {
        Method filter = FilmTitleFilter.class.getMethod("filter", List.class, Predicate.class);

        assertTrue(Modifier.isPublic(FilmTitleFilter.class.getModifiers()));
        assertTrue(Modifier.isPublic(filter.getModifiers()));
        assertTrue(Modifier.isStatic(filter.getModifiers()));
        assertEquals(List.class, filter.getReturnType());
    }

    @Test
    void selectsTitlesMatchingConditionAndKeepsTheirOrder() {
        List<String> titles = List.of("Dune", "Solaris", "It", "Arrival");

        List<String> result = FilmTitleFilter.filter(titles, title -> title.length() >= 5);

        assertEquals(List.of("Solaris", "Arrival"), result);
    }

    @Test
    void selectsEveryTitleWhenConditionAlwaysMatches() {
        List<String> titles = List.of("Dune", "Solaris");

        List<String> result = FilmTitleFilter.filter(titles, title -> true);

        assertEquals(titles, result);
    }

    @Test
    void acceptsDifferentConditionsWithoutChangingFilterMethod() {
        List<String> titles = List.of("Dune", "Solaris", "It", "Arrival");

        List<String> result = FilmTitleFilter.filter(titles, title -> title.startsWith("A"));

        assertEquals(List.of("Arrival"), result);
    }

    @Test
    void returnsEmptyListWhenNoTitleMatches() {
        List<String> titles = List.of("Dune", "It");

        List<String> result = FilmTitleFilter.filter(titles, title -> title.startsWith("Z"));

        assertTrue(result.isEmpty());
    }

    @Test
    void returnsEmptyListForEmptyInput() {
        List<String> result = FilmTitleFilter.filter(List.of(), title -> true);

        assertTrue(result.isEmpty());
    }

    @Test
    void returnsSeparateListAndDoesNotChangeInput() {
        List<String> titles = new ArrayList<>(List.of("Dune", "Solaris", "Arrival"));

        List<String> result = FilmTitleFilter.filter(titles, title -> title.length() > 4);

        assertEquals(List.of("Dune", "Solaris", "Arrival"), titles);
        assertEquals(List.of("Solaris", "Arrival"), result);
        assertNotSame(titles, result);
    }

    @Test
    void rejectsNullTitleList() {
        assertThrows(IllegalArgumentException.class,
                () -> FilmTitleFilter.filter(null, title -> true));
    }

    @Test
    void rejectsNullCondition() {
        assertThrows(IllegalArgumentException.class,
                () -> FilmTitleFilter.filter(List.of("Dune"), null));
    }

    @Test
    void rejectsNullTitleInList() {
        List<String> titles = Arrays.asList("Dune", null, "Arrival");

        assertThrows(IllegalArgumentException.class,
                () -> FilmTitleFilter.filter(titles, title -> true));
    }
}
