package learning.task017;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task017DigitalCartArrayListTest {
    @Test
    void classHasPublicConstructorAndPublicMethodsWithRequiredTypes() throws NoSuchMethodException {
        Constructor<DigitalBasket> constructor = DigitalBasket.class.getConstructor();
        Method addPrice = DigitalBasket.class.getMethod("addPrice", int.class);
        Method getPriceCount = DigitalBasket.class.getMethod("getPriceCount");
        Method getPriceAt = DigitalBasket.class.getMethod("getPriceAt", int.class);
        Method removePriceAt = DigitalBasket.class.getMethod("removePriceAt", int.class);
        Method getTotalRubles = DigitalBasket.class.getMethod("getTotalRubles");

        assertTrue(Modifier.isPublic(DigitalBasket.class.getModifiers()));
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertTrue(Modifier.isPublic(addPrice.getModifiers()));
        assertTrue(Modifier.isPublic(getPriceCount.getModifiers()));
        assertTrue(Modifier.isPublic(getPriceAt.getModifiers()));
        assertTrue(Modifier.isPublic(removePriceAt.getModifiers()));
        assertTrue(Modifier.isPublic(getTotalRubles.getModifiers()));
        assertEquals(void.class, addPrice.getReturnType());
        assertEquals(int.class, getPriceCount.getReturnType());
        assertEquals(int.class, getPriceAt.getReturnType());
        assertEquals(int.class, removePriceAt.getReturnType());
        assertEquals(long.class, getTotalRubles.getReturnType());
    }

    @Test
    void usesOnePrivateFinalArrayListOfIntegerPrices() {
        Field[] fields = DigitalBasket.class.getDeclaredFields();

        assertEquals(1, fields.length);
        Field prices = fields[0];
        assertEquals(ArrayList.class, prices.getType());
        assertTrue(Modifier.isPrivate(prices.getModifiers()));
        assertTrue(Modifier.isFinal(prices.getModifiers()));
        assertTrue(prices.getGenericType() instanceof ParameterizedType);
        ParameterizedType genericType = (ParameterizedType) prices.getGenericType();
        assertEquals(Integer.class, genericType.getActualTypeArguments()[0]);
    }

    @Test
    void addsPriceAndUpdatesCount() {
        DigitalBasket basket = new DigitalBasket();

        basket.addPrice(350);

        assertEquals(1, basket.getPriceCount());
    }

    @Test
    void keepsPricesInInsertionOrder() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(350);
        basket.addPrice(700);

        assertEquals(350, basket.getPriceAt(0));
        assertEquals(700, basket.getPriceAt(1));
    }

    @Test
    void emptyBasketHasZeroCountAndTotal() {
        DigitalBasket basket = new DigitalBasket();

        assertEquals(0, basket.getPriceCount());
        assertEquals(0L, basket.getTotalRubles());
    }

    @Test
    void totalIsTheSumOfAllPrices() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(350);
        basket.addPrice(700);
        basket.addPrice(50);

        assertEquals(1_100L, basket.getTotalRubles());
    }

    @Test
    void zeroPriceIsAllowed() {
        DigitalBasket basket = new DigitalBasket();

        basket.addPrice(0);

        assertEquals(0, basket.getPriceAt(0));
        assertEquals(0L, basket.getTotalRubles());
    }

    @Test
    void maximumPriceIsAllowed() {
        DigitalBasket basket = new DigitalBasket();

        basket.addPrice(1_000_000);

        assertEquals(1_000_000, basket.getPriceAt(0));
    }

    @Test
    void negativePriceIsRejectedWithoutChangingBasket() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(100);

        assertThrows(IllegalArgumentException.class, () -> basket.addPrice(-1));
        assertEquals(1, basket.getPriceCount());
        assertEquals(100L, basket.getTotalRubles());
    }

    @Test
    void priceAboveMaximumIsRejectedWithoutChangingBasket() {
        DigitalBasket basket = new DigitalBasket();

        assertThrows(IllegalArgumentException.class, () -> basket.addPrice(1_000_001));
        assertEquals(0, basket.getPriceCount());
    }

    @Test
    void removingPriceReturnsItAndShiftsFollowingPrices() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(100);
        basket.addPrice(250);
        basket.addPrice(400);

        assertEquals(250, basket.removePriceAt(1));
        assertEquals(2, basket.getPriceCount());
        assertEquals(100, basket.getPriceAt(0));
        assertEquals(400, basket.getPriceAt(1));
    }

    @Test
    void removalUpdatesTheTotal() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(100);
        basket.addPrice(250);

        basket.removePriceAt(0);

        assertEquals(250L, basket.getTotalRubles());
    }

    @Test
    void readingNegativeIndexIsRejected() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(100);

        assertThrows(IndexOutOfBoundsException.class, () -> basket.getPriceAt(-1));
    }

    @Test
    void readingIndexAtSizeIsRejected() {
        DigitalBasket basket = new DigitalBasket();
        basket.addPrice(100);

        assertThrows(IndexOutOfBoundsException.class, () -> basket.getPriceAt(1));
    }

    @Test
    void removingNegativeIndexIsRejected() {
        DigitalBasket basket = new DigitalBasket();

        assertThrows(IndexOutOfBoundsException.class, () -> basket.removePriceAt(-1));
    }

    @Test
    void removingIndexAtSizeIsRejected() {
        DigitalBasket basket = new DigitalBasket();

        assertThrows(IndexOutOfBoundsException.class, () -> basket.removePriceAt(0));
    }

    @Test
    void basketsKeepIndependentPrices() {
        DigitalBasket first = new DigitalBasket();
        DigitalBasket second = new DigitalBasket();
        first.addPrice(100);

        assertEquals(1, first.getPriceCount());
        assertEquals(0, second.getPriceCount());
        assertEquals(100L, first.getTotalRubles());
        assertEquals(0L, second.getTotalRubles());
    }

    @Test
    void totalUsesLongWhenItExceedsIntegerRange() {
        DigitalBasket basket = new DigitalBasket();
        for (int index = 0; index < 3_000; index++) {
            basket.addPrice(1_000_000);
        }

        assertEquals(3_000_000_000L, basket.getTotalRubles());
    }

    @Test
    void backingArrayListIsNotReturnedByPublicMethods() {
        boolean exposesArrayList = Arrays.stream(DigitalBasket.class.getMethods())
                .anyMatch(method -> ArrayList.class.isAssignableFrom(method.getReturnType()));

        assertFalse(exposesArrayList);
    }

    @Test
    void basketOperationsDoNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            DigitalBasket basket = new DigitalBasket();
            basket.addPrice(350);
            basket.getPriceAt(0);
            basket.getPriceCount();
            basket.getTotalRubles();
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
