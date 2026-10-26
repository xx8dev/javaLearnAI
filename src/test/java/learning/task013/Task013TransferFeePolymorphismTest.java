package learning.task013;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Task013TransferFeePolymorphismTest {
    @Test
    void fixedFeeReturnsThirtyRublesAtMinimumAmount() {
        assertEquals(30, new FixedTransferFee().calculateFeeRubles(100));
    }

    @Test
    void fixedFeeDoesNotDependOnTransferAmount() {
        assertEquals(30, new FixedTransferFee().calculateFeeRubles(10_000));
    }

    @Test
    void fixedFeeWorksAtMaximumAmount() {
        assertEquals(30, new FixedTransferFee().calculateFeeRubles(100_000));
    }

    @Test
    void percentageFeeReturnsFivePercentAtMinimumAmount() {
        assertEquals(5, new PercentTransferFee().calculateFeeRubles(100));
    }

    @Test
    void percentageFeeDropsFractionalRubles() {
        assertEquals(49, new PercentTransferFee().calculateFeeRubles(999));
    }

    @Test
    void percentageFeeReturnsFiftyRublesForOneThousandRubles() {
        assertEquals(50, new PercentTransferFee().calculateFeeRubles(1_000));
    }

    @Test
    void percentageFeeWorksAtMaximumAmount() {
        assertEquals(5_000, new PercentTransferFee().calculateFeeRubles(100_000));
    }

    @Test
    void baseTypeIsConcreteAndPublic() {
        assertTrue(Modifier.isPublic(TransferFeeRule.class.getModifiers()));
        assertFalse(Modifier.isAbstract(TransferFeeRule.class.getModifiers()));
    }

    @Test
    void baseRuleReturnsZeroFeeByDefault() {
        assertEquals(0, new TransferFeeRule().calculateFeeRubles(500));
    }

    @Test
    void feeMethodIsPublicAndConcrete() throws NoSuchMethodException {
        Method method = TransferFeeRule.class.getMethod("calculateFeeRubles", int.class);

        assertEquals(int.class, method.getReturnType());
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertFalse(Modifier.isAbstract(method.getModifiers()));
    }

    @Test
    void bothFeeRulesArePublicSubclasses() {
        assertTrue(Modifier.isPublic(FixedTransferFee.class.getModifiers()));
        assertTrue(Modifier.isPublic(PercentTransferFee.class.getModifiers()));
        assertTrue(TransferFeeRule.class.isAssignableFrom(FixedTransferFee.class));
        assertTrue(TransferFeeRule.class.isAssignableFrom(PercentTransferFee.class));
    }

    @Test
    void bothFeeRulesDeclareTheirOwnImplementation() throws NoSuchMethodException {
        Method fixedMethod = FixedTransferFee.class.getDeclaredMethod("calculateFeeRubles", int.class);
        Method percentMethod = PercentTransferFee.class.getDeclaredMethod("calculateFeeRubles", int.class);

        assertEquals(int.class, fixedMethod.getReturnType());
        assertEquals(int.class, percentMethod.getReturnType());
        assertTrue(Modifier.isPublic(fixedMethod.getModifiers()));
        assertTrue(Modifier.isPublic(percentMethod.getModifiers()));
        assertFalse(Modifier.isAbstract(fixedMethod.getModifiers()));
        assertFalse(Modifier.isAbstract(percentMethod.getModifiers()));
    }

    @Test
    void baseReferenceUsesFixedFeeImplementation() {
        TransferFeeRule rule = new FixedTransferFee();

        assertEquals(30, rule.calculateFeeRubles(500));
    }

    @Test
    void baseReferenceUsesPercentageFeeImplementation() {
        TransferFeeRule rule = new PercentTransferFee();

        assertEquals(25, rule.calculateFeeRubles(500));
    }

    @Test
    void repeatedCallsDoNotChangeFeeRule() {
        TransferFeeRule rule = new PercentTransferFee();

        assertEquals(49, rule.calculateFeeRubles(999));
        assertEquals(49, rule.calculateFeeRubles(999));
    }

    @Test
    void feeRuleClassesDoNotStoreInstanceState() {
        Class<?>[] ruleClasses = {TransferFeeRule.class, FixedTransferFee.class, PercentTransferFee.class};
        boolean hasInstanceField = Arrays.stream(ruleClasses)
                .flatMap(ruleClass -> Arrays.stream(ruleClass.getDeclaredFields()))
                .anyMatch(field -> !Modifier.isStatic(field.getModifiers()));

        assertFalse(hasInstanceField);
    }

    @Test
    void feeCalculationDoesNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            TransferFeeRule rule = new PercentTransferFee();
            assertEquals(25, rule.calculateFeeRubles(500));
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
