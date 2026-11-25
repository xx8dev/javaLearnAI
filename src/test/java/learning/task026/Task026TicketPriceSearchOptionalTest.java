package learning.task026;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task026TicketPriceSearchOptionalTest {
    @Test
    void hasRequiredPublicStaticMethodReturningOptional() throws NoSuchMethodException {
        Method method = TicketPriceSearch.class.getMethod("findFirstAtMost", List.class, int.class);

        assertTrue(Modifier.isPublic(TicketPriceSearch.class.getModifiers()));
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(Optional.class, method.getReturnType());
    }

    @Test
    void returnsFirstMatchingPriceInListOrder() {
        Optional<Integer> result = TicketPriceSearch.findFirstAtMost(List.of(500, 250, 200), 300);

        assertEquals(Optional.of(250), result);
    }

    @Test
    void includesPriceEqualToMaximum() {
        Optional<Integer> result = TicketPriceSearch.findFirstAtMost(List.of(250), 250);

        assertEquals(Optional.of(250), result);
    }

    @Test
    void acceptsZeroPriceAtZeroMaximum() {
        Optional<Integer> result = TicketPriceSearch.findFirstAtMost(List.of(0), 0);

        assertEquals(Optional.of(0), result);
    }

    @Test
    void returnsEmptyOptionalWhenNothingMatches() {
        Optional<Integer> result = TicketPriceSearch.findFirstAtMost(List.of(500, 400), 300);

        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    void returnsEmptyOptionalForEmptyList() {
        Optional<Integer> result = TicketPriceSearch.findFirstAtMost(List.of(), 0);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void doesNotChangeInputList() {
        List<Integer> prices = new ArrayList<>(List.of(500, 250, 200));

        TicketPriceSearch.findFirstAtMost(prices, 300);

        assertEquals(List.of(500, 250, 200), prices);
    }

    @Test
    void rejectsNullList() {
        assertThrows(IllegalArgumentException.class,
                () -> TicketPriceSearch.findFirstAtMost(null, 300));
    }

    @Test
    void rejectsNullPriceInList() {
        List<Integer> prices = Arrays.asList(200, null, 100);

        assertThrows(IllegalArgumentException.class,
                () -> TicketPriceSearch.findFirstAtMost(prices, 300));
    }

    @Test
    void rejectsNegativePriceInList() {
        assertThrows(IllegalArgumentException.class,
                () -> TicketPriceSearch.findFirstAtMost(List.of(200, -1), 300));
    }

    @Test
    void rejectsNegativeMaximumPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> TicketPriceSearch.findFirstAtMost(List.of(200), -1));
    }
}
