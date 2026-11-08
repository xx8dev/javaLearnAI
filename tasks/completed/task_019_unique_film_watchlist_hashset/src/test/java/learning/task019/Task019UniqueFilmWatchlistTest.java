package learning.task019;

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
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task019UniqueFilmWatchlistTest {
    @Test
    void watchlistHasPublicConstructorAndPublicMethodsWithRequiredTypes() throws NoSuchMethodException {
        Constructor<FilmWatchlist> constructor = FilmWatchlist.class.getConstructor();
        Method addFilmCode = FilmWatchlist.class.getMethod("addFilmCode", String.class);
        Method containsFilmCode = FilmWatchlist.class.getMethod("containsFilmCode", String.class);
        Method removeFilmCode = FilmWatchlist.class.getMethod("removeFilmCode", String.class);
        Method getFilmCount = FilmWatchlist.class.getMethod("getFilmCount");

        assertTrue(Modifier.isPublic(FilmWatchlist.class.getModifiers()));
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertTrue(Modifier.isPublic(addFilmCode.getModifiers()));
        assertTrue(Modifier.isPublic(containsFilmCode.getModifiers()));
        assertTrue(Modifier.isPublic(removeFilmCode.getModifiers()));
        assertTrue(Modifier.isPublic(getFilmCount.getModifiers()));
        assertEquals(boolean.class, addFilmCode.getReturnType());
        assertEquals(boolean.class, containsFilmCode.getReturnType());
        assertEquals(boolean.class, removeFilmCode.getReturnType());
        assertEquals(int.class, getFilmCount.getReturnType());
    }

    @Test
    void usesOnePrivateFinalHashSetOfStrings() {
        Field[] fields = FilmWatchlist.class.getDeclaredFields();

        assertEquals(1, fields.length);
        Field codes = fields[0];
        assertEquals(HashSet.class, codes.getType());
        assertTrue(Modifier.isPrivate(codes.getModifiers()));
        assertTrue(Modifier.isFinal(codes.getModifiers()));
        assertTrue(codes.getGenericType() instanceof ParameterizedType);
        ParameterizedType genericType = (ParameterizedType) codes.getGenericType();
        assertEquals(String.class, genericType.getActualTypeArguments()[0]);
    }

    @Test
    void addsNewCodeAndIncrementsCount() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertTrue(watchlist.addFilmCode("FILM-4"));
        assertEquals(1, watchlist.getFilmCount());
    }

    @Test
    void duplicateCodeIsNotAddedAgain() {
        FilmWatchlist watchlist = new FilmWatchlist();
        assertTrue(watchlist.addFilmCode("FILM-4"));

        assertFalse(watchlist.addFilmCode("FILM-4"));
        assertEquals(1, watchlist.getFilmCount());
    }

    @Test
    void differentStringObjectsWithEqualContentAreDuplicates() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertTrue(watchlist.addFilmCode(new String("FILM-4")));
        assertFalse(watchlist.addFilmCode(new String("FILM-4")));
        assertEquals(1, watchlist.getFilmCount());
    }

    @Test
    void differentCodesAreStoredSeparately() {
        FilmWatchlist watchlist = new FilmWatchlist();
        watchlist.addFilmCode("FILM-4");

        assertTrue(watchlist.addFilmCode("FILM-5"));
        assertEquals(2, watchlist.getFilmCount());
    }

    @Test
    void codesAreCaseSensitive() {
        FilmWatchlist watchlist = new FilmWatchlist();
        watchlist.addFilmCode("FILM-4");

        assertFalse(watchlist.containsFilmCode("film-4"));
        assertTrue(watchlist.addFilmCode("film-4"));
        assertEquals(2, watchlist.getFilmCount());
    }

    @Test
    void containsReturnsTrueForExistingCode() {
        FilmWatchlist watchlist = new FilmWatchlist();
        watchlist.addFilmCode("FILM-4");

        assertTrue(watchlist.containsFilmCode("FILM-4"));
    }

    @Test
    void containsReturnsFalseForUnknownCode() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertFalse(watchlist.containsFilmCode("UNKNOWN"));
    }

    @Test
    void removesExistingCodeAndDecreasesCount() {
        FilmWatchlist watchlist = new FilmWatchlist();
        watchlist.addFilmCode("FILM-4");

        assertTrue(watchlist.removeFilmCode("FILM-4"));
        assertFalse(watchlist.containsFilmCode("FILM-4"));
        assertEquals(0, watchlist.getFilmCount());
    }

    @Test
    void removedCodeCanBeAddedAgain() {
        FilmWatchlist watchlist = new FilmWatchlist();
        watchlist.addFilmCode("FILM-4");
        watchlist.removeFilmCode("FILM-4");

        assertTrue(watchlist.addFilmCode("FILM-4"));
        assertEquals(1, watchlist.getFilmCount());
    }

    @Test
    void removingUnknownCodeReturnsFalse() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertFalse(watchlist.removeFilmCode("UNKNOWN"));
    }

    @Test
    void newWatchlistStartsEmpty() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertEquals(0, watchlist.getFilmCount());
    }

    @Test
    void nullCodeIsRejectedWhenAdding() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.addFilmCode(null));
    }

    @Test
    void emptyCodeIsRejectedWhenAdding() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.addFilmCode(""));
    }

    @Test
    void nullCodeIsRejectedWhenSearching() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.containsFilmCode(null));
    }

    @Test
    void emptyCodeIsRejectedWhenSearching() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.containsFilmCode(""));
    }

    @Test
    void nullCodeIsRejectedWhenRemoving() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.removeFilmCode(null));
    }

    @Test
    void emptyCodeIsRejectedWhenRemoving() {
        FilmWatchlist watchlist = new FilmWatchlist();

        assertThrows(IllegalArgumentException.class, () -> watchlist.removeFilmCode(""));
    }

    @Test
    void watchlistsKeepIndependentCodes() {
        FilmWatchlist first = new FilmWatchlist();
        FilmWatchlist second = new FilmWatchlist();
        first.addFilmCode("FILM-4");

        assertEquals(1, first.getFilmCount());
        assertEquals(0, second.getFilmCount());
        assertFalse(second.containsFilmCode("FILM-4"));
    }

    @Test
    void publicMethodsDoNotExposeTheBackingSet() {
        boolean exposesSet = Arrays.stream(FilmWatchlist.class.getMethods())
                .anyMatch(method -> Set.class.isAssignableFrom(method.getReturnType()));

        assertFalse(exposesSet);
    }

    @Test
    void watchlistOperationsDoNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            FilmWatchlist watchlist = new FilmWatchlist();
            watchlist.addFilmCode("FILM-4");
            watchlist.containsFilmCode("FILM-4");
            watchlist.removeFilmCode("FILM-4");
            watchlist.getFilmCount();
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
