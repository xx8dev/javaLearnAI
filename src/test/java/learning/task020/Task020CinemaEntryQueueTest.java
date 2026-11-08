package learning.task020;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task020CinemaEntryQueueTest {
    @Test
    void queueHasPublicConstructorAndPublicMethodsWithRequiredTypes() throws NoSuchMethodException {
        Constructor<CinemaEntryQueue> constructor = CinemaEntryQueue.class.getConstructor();
        Method joinLine = CinemaEntryQueue.class.getMethod("joinLine", String.class);
        Method peekNextGuest = CinemaEntryQueue.class.getMethod("peekNextGuest");
        Method callNextGuest = CinemaEntryQueue.class.getMethod("callNextGuest");
        Method getGuestCount = CinemaEntryQueue.class.getMethod("getGuestCount");

        assertTrue(Modifier.isPublic(CinemaEntryQueue.class.getModifiers()));
        assertTrue(Modifier.isPublic(constructor.getModifiers()));
        assertTrue(Modifier.isPublic(joinLine.getModifiers()));
        assertTrue(Modifier.isPublic(peekNextGuest.getModifiers()));
        assertTrue(Modifier.isPublic(callNextGuest.getModifiers()));
        assertTrue(Modifier.isPublic(getGuestCount.getModifiers()));
        assertEquals(void.class, joinLine.getReturnType());
        assertEquals(String.class, peekNextGuest.getReturnType());
        assertEquals(String.class, callNextGuest.getReturnType());
        assertEquals(int.class, getGuestCount.getReturnType());
    }

    @Test
    void usesOnePrivateFinalArrayDequeOfStrings() {
        Field[] fields = CinemaEntryQueue.class.getDeclaredFields();

        assertEquals(1, fields.length);
        Field guests = fields[0];
        assertEquals(ArrayDeque.class, guests.getType());
        assertTrue(Modifier.isPrivate(guests.getModifiers()));
        assertTrue(Modifier.isFinal(guests.getModifiers()));
        assertTrue(guests.getGenericType() instanceof ParameterizedType);
        ParameterizedType genericType = (ParameterizedType) guests.getGenericType();
        assertEquals(String.class, genericType.getActualTypeArguments()[0]);
    }

    @Test
    void newQueueStartsEmpty() {
        CinemaEntryQueue queue = new CinemaEntryQueue();

        assertEquals(0, queue.getGuestCount());
    }

    @Test
    void peekOnEmptyQueueReturnsNull() {
        CinemaEntryQueue queue = new CinemaEntryQueue();

        assertNull(queue.peekNextGuest());
    }

    @Test
    void callOnEmptyQueueReturnsNull() {
        CinemaEntryQueue queue = new CinemaEntryQueue();

        assertNull(queue.callNextGuest());
        assertEquals(0, queue.getGuestCount());
    }

    @Test
    void joiningAddsOneGuestAndIncrementsCount() {
        CinemaEntryQueue queue = new CinemaEntryQueue();

        queue.joinLine("Мира");

        assertEquals(1, queue.getGuestCount());
    }

    @Test
    void peekReturnsFirstGuestWithoutRemovingThem() {
        CinemaEntryQueue queue = new CinemaEntryQueue();
        queue.joinLine("Мира");
        queue.joinLine("Лев");

        assertEquals("Мира", queue.peekNextGuest());
        assertEquals(2, queue.getGuestCount());
    }

    @Test
    void callReturnsAndRemovesFirstGuest() {
        CinemaEntryQueue queue = new CinemaEntryQueue();
        queue.joinLine("Мира");
        queue.joinLine("Лев");

        assertEquals("Мира", queue.callNextGuest());
        assertEquals(1, queue.getGuestCount());
        assertEquals("Лев", queue.peekNextGuest());
    }

    @Test
    void guestsAreCalledInFifoOrder() {
        CinemaEntryQueue queue = new CinemaEntryQueue();
        queue.joinLine("Мира");
        queue.joinLine("Лев");
        queue.joinLine("Ника");

        assertEquals("Мира", queue.callNextGuest());
        assertEquals("Лев", queue.callNextGuest());
        assertEquals("Ника", queue.callNextGuest());
        assertEquals(0, queue.getGuestCount());
    }

    @Test
    void repeatedNamesOccupySeparatePositions() {
        CinemaEntryQueue queue = new CinemaEntryQueue();
        queue.joinLine("Мира");
        queue.joinLine("Мира");

        assertEquals(2, queue.getGuestCount());
        assertEquals("Мира", queue.callNextGuest());
        assertEquals("Мира", queue.callNextGuest());
    }

    @Test
    void nullGuestNameIsRejectedWithoutChangingQueue() {
        CinemaEntryQueue queue = new CinemaEntryQueue();
        queue.joinLine("Мира");

        assertThrows(IllegalArgumentException.class, () -> queue.joinLine(null));
        assertEquals(1, queue.getGuestCount());
        assertEquals("Мира", queue.peekNextGuest());
    }

    @Test
    void emptyGuestNameIsRejectedWithoutChangingQueue() {
        CinemaEntryQueue queue = new CinemaEntryQueue();

        assertThrows(IllegalArgumentException.class, () -> queue.joinLine(""));
        assertEquals(0, queue.getGuestCount());
    }

    @Test
    void queuesKeepIndependentGuests() {
        CinemaEntryQueue first = new CinemaEntryQueue();
        CinemaEntryQueue second = new CinemaEntryQueue();
        first.joinLine("Мира");

        assertEquals(1, first.getGuestCount());
        assertEquals(0, second.getGuestCount());
        assertNull(second.peekNextGuest());
    }

    @Test
    void publicMethodsDoNotExposeArrayDeque() {
        boolean exposesQueue = Arrays.stream(CinemaEntryQueue.class.getMethods())
                .anyMatch(method -> ArrayDeque.class.isAssignableFrom(method.getReturnType()));

        assertFalse(exposesQueue);
    }

    @Test
    void queueOperationsDoNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            CinemaEntryQueue queue = new CinemaEntryQueue();
            queue.joinLine("Мира");
            queue.peekNextGuest();
            queue.callNextGuest();
            queue.getGuestCount();
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
