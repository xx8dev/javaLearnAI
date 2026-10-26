package learning.task014;

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

class Task014StreamingCostAbstractionTest {
    @Test
    void hourlyTariffCostsZeroForZeroHours() {
        assertEquals(0, new HourlyViewingCostRule().calculateCostRubles(0));
    }

    @Test
    void hourlyTariffCostsTwelveRublesForOneHour() {
        assertEquals(12, new HourlyViewingCostRule().calculateCostRubles(1));
    }

    @Test
    void hourlyTariffCalculatesSeveralHours() {
        assertEquals(36, new HourlyViewingCostRule().calculateCostRubles(3));
    }

    @Test
    void hourlyTariffWorksAtMaximumHours() {
        assertEquals(12_000, new HourlyViewingCostRule().calculateCostRubles(1_000));
    }

    @Test
    void monthlyTariffCostsTheSameAtZeroHours() {
        assertEquals(450, new MonthlyViewingCostRule().calculateCostRubles(0));
    }

    @Test
    void monthlyTariffCostsTheSameForRegularViewing() {
        assertEquals(450, new MonthlyViewingCostRule().calculateCostRubles(100));
    }

    @Test
    void monthlyTariffCostsTheSameAtMaximumHours() {
        assertEquals(450, new MonthlyViewingCostRule().calculateCostRubles(1_000));
    }

    @Test
    void viewingCostRuleIsPublicAndAbstract() {
        assertTrue(Modifier.isPublic(ViewingCostRule.class.getModifiers()));
        assertTrue(Modifier.isAbstract(ViewingCostRule.class.getModifiers()));
    }

    @Test
    void costMethodIsPublicAndAbstract() throws NoSuchMethodException {
        Method method = ViewingCostRule.class.getMethod("calculateCostRubles", int.class);

        assertEquals(int.class, method.getReturnType());
        assertTrue(Modifier.isPublic(method.getModifiers()));
        assertTrue(Modifier.isAbstract(method.getModifiers()));
    }

    @Test
    void bothTariffsArePublicSubclasses() {
        assertTrue(Modifier.isPublic(HourlyViewingCostRule.class.getModifiers()));
        assertTrue(Modifier.isPublic(MonthlyViewingCostRule.class.getModifiers()));
        assertTrue(ViewingCostRule.class.isAssignableFrom(HourlyViewingCostRule.class));
        assertTrue(ViewingCostRule.class.isAssignableFrom(MonthlyViewingCostRule.class));
    }

    @Test
    void bothTariffsDeclareConcreteImplementations() throws NoSuchMethodException {
        Method hourlyMethod = HourlyViewingCostRule.class.getDeclaredMethod("calculateCostRubles", int.class);
        Method monthlyMethod = MonthlyViewingCostRule.class.getDeclaredMethod("calculateCostRubles", int.class);

        assertEquals(int.class, hourlyMethod.getReturnType());
        assertEquals(int.class, monthlyMethod.getReturnType());
        assertTrue(Modifier.isPublic(hourlyMethod.getModifiers()));
        assertTrue(Modifier.isPublic(monthlyMethod.getModifiers()));
        assertFalse(Modifier.isAbstract(hourlyMethod.getModifiers()));
        assertFalse(Modifier.isAbstract(monthlyMethod.getModifiers()));
    }

    @Test
    void baseReferenceUsesHourlyImplementation() {
        ViewingCostRule rule = new HourlyViewingCostRule();

        assertEquals(36, rule.calculateCostRubles(3));
    }

    @Test
    void baseReferenceUsesMonthlyImplementation() {
        ViewingCostRule rule = new MonthlyViewingCostRule();

        assertEquals(450, rule.calculateCostRubles(3));
    }

    @Test
    void repeatedCallsReturnSameCost() {
        ViewingCostRule rule = new HourlyViewingCostRule();

        assertEquals(36, rule.calculateCostRubles(3));
        assertEquals(36, rule.calculateCostRubles(3));
    }

    @Test
    void tariffsDoNotStoreInstanceState() {
        Class<?>[] ruleClasses = {ViewingCostRule.class, HourlyViewingCostRule.class, MonthlyViewingCostRule.class};
        boolean hasInstanceField = Arrays.stream(ruleClasses)
                .flatMap(ruleClass -> Arrays.stream(ruleClass.getDeclaredFields()))
                .anyMatch(field -> !Modifier.isStatic(field.getModifiers()));

        assertFalse(hasInstanceField);
    }

    @Test
    void costCalculationDoesNotPrintToConsole() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(capturedOutput));
            ViewingCostRule rule = new MonthlyViewingCostRule();
            assertEquals(450, rule.calculateCostRubles(10));
        } finally {
            System.setOut(originalOut);
        }

        assertEquals("", capturedOutput.toString(StandardCharsets.UTF_8));
    }
}
