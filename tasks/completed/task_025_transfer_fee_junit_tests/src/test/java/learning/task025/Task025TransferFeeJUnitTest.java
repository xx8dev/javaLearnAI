package learning.task025;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task025TransferFeeJUnitTest {
    @Test
    void hasRequiredPublicStaticMethod() throws NoSuchMethodException {
        Method calculate = TransferFeeCalculator.class.getMethod(
                "calculateFeeRubles", int.class, int.class);

        assertTrue(Modifier.isPublic(TransferFeeCalculator.class.getModifiers()));
        assertTrue(Modifier.isPublic(calculate.getModifiers()));
        assertTrue(Modifier.isStatic(calculate.getModifiers()));
        assertEquals(int.class, calculate.getReturnType());
    }

    @Test
    void calculatesTypicalFee() {
        assertEquals(375, TransferFeeCalculator.calculateFeeRubles(12_500, 3));
    }

    @Test
    void dropsFractionalRuble() {
        assertEquals(9, TransferFeeCalculator.calculateFeeRubles(99, 10));
    }

    @Test
    void zeroAmountAndZeroPercentProduceZeroFee() {
        assertEquals(0, TransferFeeCalculator.calculateFeeRubles(0, 25));
        assertEquals(0, TransferFeeCalculator.calculateFeeRubles(500, 0));
    }

    @Test
    void oneHundredPercentFeeEqualsTransferAmount() {
        assertEquals(1_000_000, TransferFeeCalculator.calculateFeeRubles(1_000_000, 100));
    }

    @Test
    void rejectsNegativeTransferAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferFeeCalculator.calculateFeeRubles(-1, 5));
    }

    @Test
    void rejectsTransferAmountAboveMaximum() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferFeeCalculator.calculateFeeRubles(1_000_001, 5));
    }

    @Test
    void rejectsNegativeFeePercent() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferFeeCalculator.calculateFeeRubles(100, -1));
    }

    @Test
    void rejectsFeePercentAboveMaximum() {
        assertThrows(IllegalArgumentException.class,
                () -> TransferFeeCalculator.calculateFeeRubles(100, 101));
    }

    @Test
    void learnerHasWrittenAtLeastFiveJunitTests() throws ClassNotFoundException {
        Class<?> learnerTestClass = Class.forName("learning.task025.TransferFeeCalculatorTest");
        long testCount = Arrays.stream(learnerTestClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .count();

        assertTrue(testCount >= 5,
                "TransferFeeCalculatorTest должен содержать не меньше пяти методов с @Test");
    }
}
