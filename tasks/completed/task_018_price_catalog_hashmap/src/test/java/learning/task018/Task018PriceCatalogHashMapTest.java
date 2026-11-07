package learning.task018;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task018PriceCatalogHashMapTest {
    @Test
    void catalogHasPublicConstructorAndPublicMethodsWithRequiredTypes() throws NoSuchMethodException {
        Constructor<PriceCatalog> constructor = PriceCatalog.class.getConstructor();
        Method setPrice = PriceCatalog.class.getMethod("setPrice", String.class, int.class);
        Method getPrice = PriceCatalog.class.getMethod("getPrice", String.class);
        Method removePrice = PriceCatalog.class.getMethod("removePrice", String.class);
        Method getProductCount = PriceCatalog.class.getMethod("getProductCount");

        assertTrue(Modifier.isPublic(PriceCatalog.class.getModifiers()));
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertTrue(Modifier.isPublic(setPrice.getModifiers()));
        assertTrue(Modifier.isPublic(getPrice.getModifiers()));
        assertTrue(Modifier.isPublic(removePrice.getModifiers()));
        assertTrue(Modifier.isPublic(getProductCount.getModifiers()));
        assertEquals(void.class, setPrice.getReturnType());
        assertEquals(int.class, getPrice.getReturnType());
        assertEquals(boolean.class, removePrice.getReturnType());
        assertEquals(int.class, getProductCount.getReturnType());
    }

    @Test
    void usesOnePrivateFinalHashMapWithRequiredTypes() {
        Field[] fields = PriceCatalog.class.getDeclaredFields();

        assertEquals(1, fields.length);
        Field prices = fields[0];
        assertEquals(HashMap.class, prices.getType());
        assertTrue(Modifier.isPrivate(prices.getModifiers()));
        assertTrue(Modifier.isFinal(prices.getModifiers()));
        assertTrue(prices.getGenericType() instanceof ParameterizedType);
        ParameterizedType genericType = (ParameterizedType) prices.getGenericType();
        assertEquals(String.class, genericType.getActualTypeArguments()[0]);
        assertEquals(Integer.class, genericType.getActualTypeArguments()[1]);
    }

    @Test
    void storesAndReturnsPriceByCode() {
        PriceCatalog catalog = new PriceCatalog();

        catalog.setPrice("FILM-7", 350);

        assertEquals(350, catalog.getPrice("FILM-7"));
    }

    @Test
    void findsKeyUsingStringValueEquality() {
        PriceCatalog catalog = new PriceCatalog();
        String storedCode = new String("FILM-7");
        String searchCode = new String("FILM-7");
        catalog.setPrice(storedCode, 350);

        assertEquals(350, catalog.getPrice(searchCode));
    }

    @Test
    void settingExistingCodeUpdatesPriceWithoutAddingAnotherEntry() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("GAME-2", 700);
        catalog.setPrice("GAME-2", 750);

        assertEquals(750, catalog.getPrice("GAME-2"));
        assertEquals(1, catalog.getProductCount());
    }

    @Test
    void storesPricesForDifferentCodes() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("FILM-7", 350);
        catalog.setPrice("GAME-2", 700);

        assertEquals(2, catalog.getProductCount());
        assertEquals(350, catalog.getPrice("FILM-7"));
        assertEquals(700, catalog.getPrice("GAME-2"));
    }

    @Test
    void codesAreCaseSensitive() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("FILM-7", 350);

        assertEquals(-1, catalog.getPrice("film-7"));
    }

    @Test
    void returnsMinusOneForUnknownCode() {
        PriceCatalog catalog = new PriceCatalog();

        assertEquals(-1, catalog.getPrice("UNKNOWN"));
    }

    @Test
    void removesExistingCodeAndReturnsTrue() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("FILM-7", 350);

        assertTrue(catalog.removePrice("FILM-7"));
        assertEquals(-1, catalog.getPrice("FILM-7"));
        assertEquals(0, catalog.getProductCount());
    }

    @Test
    void removingUnknownCodeReturnsFalse() {
        PriceCatalog catalog = new PriceCatalog();

        assertFalse(catalog.removePrice("UNKNOWN"));
    }

    @Test
    void zeroPriceIsAllowedAndDifferentFromMissingCode() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("FREE-BONUS", 0);

        assertEquals(0, catalog.getPrice("FREE-BONUS"));
        assertEquals(-1, catalog.getPrice("UNKNOWN"));
    }

    @Test
    void maximumPriceIsAllowed() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("COLLECTION", 1_000_000);

        assertEquals(1_000_000, catalog.getPrice("COLLECTION"));
    }

    @Test
    void negativePriceIsRejectedWithoutChangingCatalog() {
        PriceCatalog catalog = new PriceCatalog();
        catalog.setPrice("FILM-7", 350);

        assertThrows(IllegalArgumentException.class, () -> catalog.setPrice("GAME-2", -1));
        assertEquals(1, catalog.getProductCount());
        assertEquals(-1, catalog.getPrice("GAME-2"));
    }

    @Test
    void priceAboveMaximumIsRejected() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.setPrice("FILM-7", 1_000_001));
        assertEquals(0, catalog.getProductCount());
    }

    @Test
    void nullCodeIsRejectedWhenSettingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.setPrice(null, 100));
    }

    @Test
    void emptyCodeIsRejectedWhenSettingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.setPrice("", 100));
    }

    @Test
    void nullCodeIsRejectedWhenGettingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.getPrice(null));
    }

    @Test
    void emptyCodeIsRejectedWhenGettingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.getPrice(""));
    }

    @Test
    void nullCodeIsRejectedWhenRemovingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.removePrice(null));
    }

    @Test
    void emptyCodeIsRejectedWhenRemovingPrice() {
        PriceCatalog catalog = new PriceCatalog();

        assertThrows(IllegalArgumentException.class, () -> catalog.removePrice(""));
    }

    @Test
    void catalogsKeepIndependentEntries() {
        PriceCatalog first = new PriceCatalog();
        PriceCatalog second = new PriceCatalog();
        first.setPrice("FILM-7", 350);

        assertEquals(1, first.getProductCount());
        assertEquals(0, second.getProductCount());
        assertEquals(-1, second.getPrice("FILM-7"));
    }

    @Test
    void publicMethodsDoNotExposeTheBackingMap() {
        boolean exposesMap = Arrays.stream(PriceCatalog.class.getMethods())
                .anyMatch(method -> Map.class.isAssignableFrom(method.getReturnType()));

        assertFalse(exposesMap);
    }

    @Test
    void catalogOperationsDoNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            PriceCatalog catalog = new PriceCatalog();
            catalog.setPrice("FILM-7", 350);
            catalog.getPrice("FILM-7");
            catalog.removePrice("FILM-7");
            catalog.getProductCount();
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
