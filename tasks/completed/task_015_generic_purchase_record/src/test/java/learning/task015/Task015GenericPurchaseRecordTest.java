package learning.task015;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task015GenericPurchaseRecordTest {
    @Test
    void purchaseClassIsPublicAndHasOneTypeParameter() {
        assertTrue(Modifier.isPublic(DigitalPurchase.class.getModifiers()));
        assertEquals(1, DigitalPurchase.class.getTypeParameters().length);
        assertEquals("T", DigitalPurchase.class.getTypeParameters()[0].getName());
    }

    @Test
    void constructorAndGettersArePublicAndKeepTheGenericType() throws NoSuchMethodException {
        Constructor<?> constructor = DigitalPurchase.class.getConstructor(Object.class, int.class);
        Method itemGetter = DigitalPurchase.class.getMethod("getItem");
        Method priceGetter = DigitalPurchase.class.getMethod("getPriceRubles");

        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertEquals("T", constructor.getGenericParameterTypes()[0].getTypeName());
        assertTrue(Modifier.isPublic(itemGetter.getModifiers()));
        assertTrue(itemGetter.getGenericReturnType() instanceof TypeVariable<?>);
        assertEquals("T", itemGetter.getGenericReturnType().getTypeName());
        assertTrue(Modifier.isPublic(priceGetter.getModifiers()));
        assertEquals(int.class, priceGetter.getReturnType());
    }

    @Test
    void stringItemAndPriceAreReturned() {
        DigitalPurchase<String> purchase = new DigitalPurchase<>("Фильм", 350);

        assertEquals("Фильм", purchase.getItem());
        assertEquals(350, purchase.getPriceRubles());
    }

    @Test
    void customItemIsReturnedAsTheSameObject() {
        GameExpansion expansion = new GameExpansion("Новая карта");
        DigitalPurchase<GameExpansion> purchase = new DigitalPurchase<>(expansion, 700);

        assertSame(expansion, purchase.getItem());
        assertEquals("Новая карта", purchase.getItem().title());
    }

    @Test
    void integerWrapperCanBeUsedAsTheItemType() {
        DigitalPurchase<Integer> purchase = new DigitalPurchase<>(42, 10);

        assertEquals(Integer.valueOf(42), purchase.getItem());
    }

    @Test
    void zeroPriceIsAllowed() {
        DigitalPurchase<String> purchase = new DigitalPurchase<>("Бонус", 0);

        assertEquals(0, purchase.getPriceRubles());
    }

    @Test
    void maximumPriceIsAllowed() {
        DigitalPurchase<String> purchase = new DigitalPurchase<>("Коллекция", 1_000_000);

        assertEquals(1_000_000, purchase.getPriceRubles());
    }

    @Test
    void nullItemIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalPurchase<String>(null, 100));
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalPurchase<>("Фильм", -1));
    }

    @Test
    void priceAboveMaximumIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DigitalPurchase<>("Фильм", 1_000_001));
    }

    @Test
    void purchasesKeepTheirOwnValues() {
        DigitalPurchase<String> first = new DigitalPurchase<>("Первый фильм", 200);
        DigitalPurchase<String> second = new DigitalPurchase<>("Второй фильм", 500);

        assertEquals("Первый фильм", first.getItem());
        assertEquals(200, first.getPriceRubles());
        assertEquals("Второй фильм", second.getItem());
        assertEquals(500, second.getPriceRubles());
        assertNotSame(first, second);
    }

    @Test
    void repeatedCallsReturnTheStoredValues() {
        DigitalPurchase<String> purchase = new DigitalPurchase<>("Саундтрек", 80);

        assertEquals("Саундтрек", purchase.getItem());
        assertEquals("Саундтрек", purchase.getItem());
        assertEquals(80, purchase.getPriceRubles());
        assertEquals(80, purchase.getPriceRubles());
    }

    @Test
    void purchaseStateIsPrivateAndImmutable() {
        Field[] fields = DigitalPurchase.class.getDeclaredFields();

        assertEquals(2, fields.length);
        for (Field field : fields) {
            assertTrue(Modifier.isPrivate(field.getModifiers()));
            assertTrue(Modifier.isFinal(field.getModifiers()));
        }
    }

    @Test
    void purchaseDoesNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            DigitalPurchase<String> purchase = new DigitalPurchase<>("Фильм", 350);
            purchase.getItem();
            purchase.getPriceRubles();
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }

    private record GameExpansion(String title) {
    }
}
