package learning.task024;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task024TransferDateCountdownTest {
    @Test
    void hasRequiredPublicStaticMethod() throws NoSuchMethodException {
        Method method = TransferDateCalculator.class.getMethod(
                "daysUntilTransfer", LocalDate.class, LocalDate.class);

        assertTrue(Modifier.isPublic(TransferDateCalculator.class.getModifiers()));
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(long.class, method.getReturnType());
    }

    @Test
    void countsDaysWithinSameMonth() {
        assertEquals(5L, TransferDateCalculator.daysUntilTransfer(
                LocalDate.of(2026, 11, 25), LocalDate.of(2026, 11, 30)));
    }

    @Test
    void returnsZeroWhenTransferDateIsToday() {
        LocalDate today = LocalDate.of(2026, 11, 25);

        assertEquals(0L, TransferDateCalculator.daysUntilTransfer(today, today));
    }

    @Test
    void countsAcrossMonthBoundary() {
        assertEquals(1L, TransferDateCalculator.daysUntilTransfer(
                LocalDate.of(2026, 11, 30), LocalDate.of(2026, 12, 1)));
    }

    @Test
    void countsAcrossYearBoundary() {
        assertEquals(1L, TransferDateCalculator.daysUntilTransfer(
                LocalDate.of(2026, 12, 31), LocalDate.of(2027, 1, 1)));
    }

    @Test
    void countsLeapDay() {
        assertEquals(2L, TransferDateCalculator.daysUntilTransfer(
                LocalDate.of(2024, 2, 28), LocalDate.of(2024, 3, 1)));
    }

    @Test
    void rejectsNullToday() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferDateCalculator.daysUntilTransfer(null, LocalDate.of(2026, 12, 1)));
    }

    @Test
    void rejectsNullTransferDate() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferDateCalculator.daysUntilTransfer(LocalDate.of(2026, 11, 25), null));
    }

    @Test
    void rejectsTransferDateInThePast() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferDateCalculator.daysUntilTransfer(
                        LocalDate.of(2026, 11, 25), LocalDate.of(2026, 11, 24)));
    }
}
