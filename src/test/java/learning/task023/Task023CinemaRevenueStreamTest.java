package learning.task023;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task023CinemaRevenueStreamTest {
    @Test
    void hasRequiredPublicStaticMethod() throws NoSuchMethodException {
        Method sum = CinemaRevenueSummary.class.getMethod("sumTicketsAtLeast", List.class, int.class);

        assertTrue(Modifier.isPublic(CinemaRevenueSummary.class.getModifiers()));
        assertTrue(Modifier.isPublic(sum.getModifiers()));
        assertTrue(Modifier.isStatic(sum.getModifiers()));
        assertEquals(long.class, sum.getReturnType());
    }

    @Test
    void sumsPricesAtOrAboveThreshold() {
        assertEquals(850L, CinemaRevenueSummary.sumTicketsAtLeast(List.of(250, 500, 350), 300));
    }

    @Test
    void includesPriceExactlyAtThreshold() {
        assertEquals(700L, CinemaRevenueSummary.sumTicketsAtLeast(List.of(300, 400), 300));
    }

    @Test
    void returnsZeroWhenNoPricePassesThreshold() {
        assertEquals(0L, CinemaRevenueSummary.sumTicketsAtLeast(List.of(100, 200), 500));
    }

    @Test
    void returnsZeroForEmptyList() {
        assertEquals(0L, CinemaRevenueSummary.sumTicketsAtLeast(List.of(), 0));
    }

    @Test
    void sumCanExceedIntRange() {
        List<Integer> prices = List.of(Integer.MAX_VALUE, Integer.MAX_VALUE);

        assertEquals(4_294_967_294L, CinemaRevenueSummary.sumTicketsAtLeast(prices, 0));
    }

    @Test
    void doesNotChangeInputList() {
        List<Integer> prices = new ArrayList<>(List.of(250, 500, 350));

        assertEquals(850L, CinemaRevenueSummary.sumTicketsAtLeast(prices, 300));
        assertEquals(List.of(250, 500, 350), prices);
    }

    @Test
    void rejectsNullList() {
        assertThrows(IllegalArgumentException.class,
                () -> CinemaRevenueSummary.sumTicketsAtLeast(null, 0));
    }

    @Test
    void rejectsNegativeThreshold() {
        assertThrows(IllegalArgumentException.class,
                () -> CinemaRevenueSummary.sumTicketsAtLeast(List.of(100), -1));
    }

    @Test
    void rejectsNegativeTicketPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> CinemaRevenueSummary.sumTicketsAtLeast(List.of(100, -1), 0));
    }

    @Test
    void rejectsNullTicketPrice() {
        List<Integer> prices = Arrays.asList(100, null);

        assertThrows(IllegalArgumentException.class,
                () -> CinemaRevenueSummary.sumTicketsAtLeast(prices, 0));
    }
}
